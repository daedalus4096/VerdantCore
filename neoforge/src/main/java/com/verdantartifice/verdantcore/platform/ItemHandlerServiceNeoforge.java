package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.capabilities.ItemStackHandlerPMNeoforge;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import com.verdantartifice.verdantcore.common.util.InvWrapperPMNeoforge;
import com.verdantartifice.verdantcore.common.util.SidedInvWrapperPMNeoforge;
import com.verdantartifice.verdantcore.platform.services.IItemHandlerService;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemHandlerServiceNeoforge implements IItemHandlerService {
    @Override
    public IItemHandlerVC create(@Nullable AbstractTileVC tile) {
        return new ItemStackHandlerPMNeoforge(tile);
    }

    @Override
    public IItemHandlerVC create(int size, @Nullable AbstractTileVC tile) {
        return new ItemStackHandlerPMNeoforge(size, tile);
    }

    @Override
    public IItemHandlerVC create(NonNullList<ItemStack> stacks, @Nullable AbstractTileVC tile) {
        return new ItemStackHandlerPMNeoforge(stacks, tile);
    }

    @Override
    public IItemHandlerVC.Builder builder(NonNullList<ItemStack> stacks, @Nullable AbstractTileVC tile) {
        return ItemStackHandlerPMNeoforge.builder(stacks, tile);
    }

    @Override
    public IItemHandlerVC wrap(Container container, @Nullable Direction side) {
        if (container instanceof WorldlyContainer worldlyContainer) {
            return new SidedInvWrapperPMNeoforge(worldlyContainer, side);
        } else {
            return new InvWrapperPMNeoforge(container);
        }
    }
}
