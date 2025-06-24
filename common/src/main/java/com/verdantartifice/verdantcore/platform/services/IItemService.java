package com.verdantartifice.verdantcore.platform.services;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public interface IItemService {
    boolean hasCraftingRemainingItem(ItemStack stack);
    ItemStack getCraftingRemainingItem(ItemStack stack);
    InteractionResult onItemUseFirst(Item item, ItemStack stack, UseOnContext context);
}
