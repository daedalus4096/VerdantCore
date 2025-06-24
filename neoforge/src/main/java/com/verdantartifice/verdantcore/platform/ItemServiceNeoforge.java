package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IItemService;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class ItemServiceNeoforge implements IItemService {
    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.hasCraftingRemainingItem();
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.getCraftingRemainingItem();
    }

    @Override
    public InteractionResult onItemUseFirst(Item item, ItemStack stack, UseOnContext context) {
        return item.onItemUseFirst(stack, context);
    }
}
