package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface IItemHandlerService {
    IItemHandlerVC create(@Nullable AbstractTileVC tile);
    IItemHandlerVC create(int size, @Nullable AbstractTileVC tile);
    IItemHandlerVC create(NonNullList<ItemStack> stacks, @Nullable AbstractTileVC tile);
    IItemHandlerVC.Builder builder(NonNullList<ItemStack> stacks, @Nullable AbstractTileVC tile);

    IItemHandlerVC wrap(Container container, @Nullable Direction side);
}
