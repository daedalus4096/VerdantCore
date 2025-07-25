package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.capabilities.ItemStackHandlerVCForge;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import com.verdantartifice.verdantcore.common.util.InvWrapperPMForge;
import com.verdantartifice.verdantcore.common.util.SidedInvWrapperPMForge;
import com.verdantartifice.verdantcore.platform.services.IItemHandlerService;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemHandlerServiceForge implements IItemHandlerService {
    @Override
    public IItemHandlerVC create(@Nullable AbstractTileVC tile) {
        return new ItemStackHandlerVCForge(tile);
    }

    @Override
    public IItemHandlerVC create(int size, @Nullable AbstractTileVC tile) {
        return new ItemStackHandlerVCForge(size, tile);
    }

    @Override
    public IItemHandlerVC create(NonNullList<ItemStack> stacks, @Nullable AbstractTileVC tile) {
        return new ItemStackHandlerVCForge(stacks, tile);
    }

    @Override
    public IItemHandlerVC.Builder builder(NonNullList<ItemStack> stacks, @Nullable AbstractTileVC tile) {
        return ItemStackHandlerVCForge.builder(stacks, tile);
    }

    @Override
    public IItemHandlerVC wrap(Container container, @Nullable Direction side) {
        if (container instanceof WorldlyContainer worldlyContainer) {
            return new SidedInvWrapperPMForge(worldlyContainer, side);
        } else {
            return new InvWrapperPMForge(container);
        }
    }
}
