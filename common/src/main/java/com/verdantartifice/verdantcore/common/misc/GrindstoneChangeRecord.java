package com.verdantartifice.verdantcore.common.misc;

import net.minecraft.world.item.ItemStack;

public record GrindstoneChangeRecord(ItemStack top, ItemStack bottom, ItemStack output, int xp, boolean canceled) {
}
