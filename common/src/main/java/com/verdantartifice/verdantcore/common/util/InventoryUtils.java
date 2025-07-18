package com.verdantartifice.verdantcore.common.util;

import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Collection of utility methods pertaining to a player inventories.
 * 
 * @author Daedalus4096
 */
public class InventoryUtils {
    /**
     * Determine if the given player is carrying the given item in their main inventory.  Does not
     * consider equipped items or nested inventories (e.g. backpacks).  If the given stack has a count
     * higher than one, this method will search for any combination of itemstacks in the player's
     * inventory that total the given count, rather than requiring a single stack of that size.
     * 
     * @param player the player whose inventory to search
     * @param stack the itemstack being searched for
     * @param matchNBT whether the test should consider the stacks' NBT data
     * @return true if the player is carrying at least that many of the given item, false otherwise
     */
    public static boolean isPlayerCarrying(@Nullable Player player, @Nullable ItemStack stack, boolean matchNBT) {
        if (player == null) {
            return false;
        }
        if (stack == null || stack.isEmpty()) {
            return true;
        }
        int count = stack.getCount();
        for (ItemStack searchStack : player.getInventory().items) {
            // Determine if the stack items, and optionally NBT, match
            boolean areEqual = matchNBT ?
                    ItemStack.matches(stack, searchStack) :
                    ItemStack.isSameItem(stack, searchStack);
            if (areEqual) {
                count -= searchStack.getCount();
                if (count <= 0) {
                    // Once a sufficient number of the given item are found, return true
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Determine if the given player is carrying the given item in their main inventory.  Does not
     * consider equipped items or nested inventories (e.g. backpacks).  If the given stack has a count
     * higher than one, this method will search for any combination of itemstacks in the player's
     * inventory that total the given count, rather than requiring a single stack of that size.  By
     * default, does not attempt to match NBT data on itemstacks.
     * 
     * @param player the player whose inventory to search
     * @param stack the itemstack being searched for
     * @return true if the player is carrying at least that many of the given item, false otherwise
     */
    public static boolean isPlayerCarrying(@Nullable Player player, @Nullable ItemStack stack) {
        return isPlayerCarrying(player, stack, false);
    }
    
    /**
     * Determine if the given player is carrying a stack of the given item in their main inventory.  Does
     * not consider equipped items or nested inventories (e.g. backpacks).
     * 
     * @param player the player whose inventory to search
     * @param item the item being searched for
     * @return true if the player is carrying at least one of the given item, false otherwise
     */
    public static boolean isPlayerCarrying(@Nullable Player player, @Nonnull Item item) {
        return isPlayerCarrying(player, new ItemStack(item));
    }
    
    /**
     * Determine if the given player is carrying items of the given tag in their main inventory.  Does
     * not consider equipped items or nested inventories (e.g. backpacks).  If the given amount is
     * greater than one, this method will search for any combination of itemstacks in the player's
     * inventory that total the given count, rather than requiring a single stack of that size.  The
     * found items need not be the same, so long as all of them belong to the tag.  Does not attempt
     * to match NBT data, as that cannot be conveyed by a tag.
     * 
     * @param player the player whose inventory to search
     * @param tag the tag containing the items to be searched for
     * @param amount the minimum number of items the player must be carrying for success
     * @return true if the player is carrying at least that many of the given tag's items, false otherwise
     */
    public static boolean isPlayerCarrying(@Nullable Player player, @Nullable TagKey<Item> tag, int amount) {
        if (player == null) {
            return false;
        }
        if (tag == null) {
            return true;
        }
        for (ItemStack searchStack : player.getInventory().items) {
            // Only the items need match, not the NBT data
            if (!searchStack.isEmpty() && searchStack.is(tag)) {
                amount -= searchStack.getCount();
                if (amount <= 0) {
                    // Once a sufficient number of the given item are found, return true
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Attempts to remove the given quantity of the given item from the player's main inventory.  Does
     * not consider equipped items or nested inventories (e.g. backpacks).  If the given stack has a
     * count higher than one, this method will search for any combination of itemstacks in the player's
     * inventory that total the given count, rather than requiring a single stack of that size.  Should
     * the item being removed have a container (e.g. an empty bucket from a milk bucket), the container
     * will be refunded to the player.
     * 
     * @param player the player whose inventory to search
     * @param stack the item and quantity to be removed
     * @param matchNBT whether the test should consider the stacks' NBT data
     * @return true if the given item was removed in the given quantity, false otherwise
     */
    public static boolean consumeItem(@Nullable Player player, @Nullable ItemStack stack, boolean matchNBT) {
        if (player == null) {
            return false;
        }
        if (stack == null || stack.isEmpty()) {
            return true;
        }
        if (!isPlayerCarrying(player, stack)) {
            // If the player is not carrying enough of the given item, return false immediately
            return false;
        }
        int count = stack.getCount();
        for (int index = 0; index < player.getInventory().items.size(); index++) {
            ItemStack searchStack = player.getInventory().items.get(index);
            // Determine if the stack items, and optionally NBT, match
            boolean areEqual = matchNBT ?
                    ItemStack.matches(stack, searchStack) :
                    ItemStack.isSameItem(stack, searchStack);
            if (areEqual) {
                if (searchStack.getCount() > count) {
                    searchStack.shrink(count);
                    count = 0;
                    if (player instanceof ServerPlayer) {
                        ((ServerPlayer)player).connection.send(new ClientboundContainerSetSlotPacket(ClientboundContainerSetSlotPacket.PLAYER_INVENTORY, 0, index, searchStack));
                    }
                } else {
                    count -= searchStack.getCount();
                    player.getInventory().items.set(index, ItemStack.EMPTY);
                    if (player instanceof ServerPlayer) {
                        ((ServerPlayer)player).connection.send(new ClientboundContainerSetSlotPacket(ClientboundContainerSetSlotPacket.PLAYER_INVENTORY, 0, index, ItemStack.EMPTY));
                    }
                }
                if (count <= 0) {
                    // Once a sufficient number of the given item are removed, refund container(s) and return true
                    addRefundItem(stack, stack.getCount(), player);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Attempts to remove the given quantity of the given item from the player's main inventory.  Does
     * not consider equipped items or nested inventories (e.g. backpacks).  If the given stack has a
     * count higher than one, this method will search for any combination of itemstacks in the player's
     * inventory that total the given count, rather than requiring a single stack of that size.  By
     * default, does not attempt to match NBT data on itemstacks.  Should the item being removed have a
     * container (e.g. an empty bucket from a milk bucket), the container will be refunded to the player.
     * 
     * @param player the player whose inventory to search
     * @param stack the item and quantity to be removed
     * @return true if the given item was removed in the given quantity, false otherwise
     */
    public static boolean consumeItem(@Nullable Player player, @Nullable ItemStack stack) {
        return consumeItem(player, stack, false);
    }
    
    /**
     * Attempts to remove the given quantity of items in the given tag from the player's main inventory.
     * Does not consider equipped items or nested inventories (e.g. backpacks).  If the given amount is
     * greater than one, this method will search for any combination of itemstacks in the player's
     * inventory that total the given count, rather than requiring a single stack of that size.  The found
     * items need not be the same, so long as all of them belong to the tag.  Does not attempt to match
     * NBT data, as that cannot be conveyed by a tag.  Should the items being removed have a container
     * (e.g. an empty bucket from a milk bucket), the container will be refunded to the player.
     * 
     * @param player the player whose inventory to search
     * @param tag the tag containing the items to be removed
     * @param amount the quantity of items to be removed
     * @return true if the given tag's items were removed in the given quantity, false otherwise
     */
    public static boolean consumeItem(@Nullable Player player, @Nullable TagKey<Item> tag, int amount) {
        if (player == null) {
            return false;
        }
        if (tag == null || amount <= 0) {
            return true;
        }
        if (!isPlayerCarrying(player, tag, amount)) {
            // If the player is not carrying enough of the given items, return false immediately
            return false;
        }
        for (int index = 0; index < player.getInventory().items.size(); index++) {
            ItemStack searchStack = player.getInventory().items.get(index);
            // Only the items need match, not the NBT data
            if (!searchStack.isEmpty() && searchStack.is(tag)) {
                if (searchStack.getCount() > amount) {
                    addRefundItem(searchStack, amount, player);
                    searchStack.shrink(amount);
                    amount = 0;
                    if (player instanceof ServerPlayer) {
                        ((ServerPlayer)player).connection.send(new ClientboundContainerSetSlotPacket(ClientboundContainerSetSlotPacket.PLAYER_INVENTORY, 0, index, searchStack));
                    }
                } else {
                    addRefundItem(searchStack, searchStack.getCount(), player);
                    amount -= searchStack.getCount();
                    player.getInventory().items.set(index, ItemStack.EMPTY);
                    if (player instanceof ServerPlayer) {
                        ((ServerPlayer)player).connection.send(new ClientboundContainerSetSlotPacket(ClientboundContainerSetSlotPacket.PLAYER_INVENTORY, 0, index, ItemStack.EMPTY));
                    }
                }
                if (amount <= 0) {
                    // Once a sufficient number of the given items are removed, return true
                    return true;
                }
            }
        }
        return false;
    }
    
    private static void addRefundItem(ItemStack stack, int refundCount, Player player) {
        ItemStack refundStack = ItemStack.EMPTY;
        if (ServicesVC.ITEMS.hasCraftingRemainingItem(stack)) {
            refundStack = ServicesVC.ITEMS.getCraftingRemainingItem(stack).copyWithCount(refundCount);
        } else if (stack.is(Items.POTION)) {
            // Potions don't use the standard container mechanism, so test for them directly
            refundStack = new ItemStack(Items.GLASS_BOTTLE, refundCount);
        }
        
        if (!refundStack.isEmpty() && !player.addItem(refundStack)) {
            ItemEntity entity = player.drop(refundStack, false);
            if (entity != null) {
                entity.setNoPickUpDelay();
                entity.setTarget(player.getUUID());
            }
        }
    }
    
    /**
     * Finds all instances of items matching the given item stack in the given player's inventory, optionally
     * testing whether those item stacks match the NBT data of the given stack.  Does not consider equipped
     * items or nested inventories (e.g. backpacks).
     * 
     * @param player the player whose inventory to search
     * @param toFind the item stack being searched for
     * @param matchNBT whether the test should consider the stacks' NBT data
     * @return a list of item stacks matching the given stack
     */
    @Nonnull
    public static NonNullList<ItemStack> find(@Nullable Player player, @Nullable ItemStack toFind, boolean matchNBT) {
        NonNullList<ItemStack> retVal = NonNullList.create();
        if (player != null && toFind != null && !toFind.isEmpty()) {
            for (ItemStack searchStack : player.getInventory().items) {
                // Determine if the stack items, and optionally NBT, match
                boolean areEqual = matchNBT ?
                        ItemStack.matches(toFind, searchStack) :
                        ItemStack.isSameItem(toFind, searchStack);
                if (areEqual) {
                    retVal.add(searchStack);
                }
            }
        }
        return retVal;
    }
    
    /**
     * Finds all instances of items matching the given item stack in the given player's inventory, optionally
     * testing whether those item stacks match the NBT data of the given stack.  Does not consider equipped
     * items or nested inventories (e.g. backpacks).  Does not attempt to match NBT data on item stacks.
     * 
     * @param player the player whose inventory to search
     * @param toFind the item stack being searched for
     * @return a list of item stacks matching the given stack
     */
    @Nonnull
    public static NonNullList<ItemStack> find(@Nullable Player player, @Nullable ItemStack toFind) {
        return find(player, toFind, false);
    }

    /**
     * Finds all instances of items matching the given named tag in the given player's inventory.  Does not
     * consider equipped items or nested inventories (e.g. backpacks).  The found items need not be the same, 
     * so long as all of them belong to the tag.  Does not attempt to match NBT data, as that cannot be 
     * conveyed by a tag.
     * 
     * @param player the player whose inventory to search
     * @param tag the tag containing the items to be searched for
     * @return a list of item stacks matching the given tag
     */
    @Nonnull
    public static NonNullList<ItemStack> find(@Nullable Player player, @Nullable TagKey<Item> tag) {
        NonNullList<ItemStack> retVal = NonNullList.create();
        if (player != null && tag != null) {
            for (ItemStack searchStack : player.getInventory().items) {
                // Only the items need match, not the NBT data
                if (!searchStack.isEmpty() && searchStack.is(tag)) {
                    retVal.add(searchStack);
                }
            }
        }
        return retVal;
    }
}
