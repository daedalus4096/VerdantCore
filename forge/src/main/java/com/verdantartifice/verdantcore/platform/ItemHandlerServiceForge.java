package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerPM;
import com.verdantartifice.verdantcore.common.capabilities.ItemStackHandlerPMForge;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTilePM;
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
    public IItemHandlerPM create(@Nullable AbstractTilePM tile) {
        return new ItemStackHandlerPMForge(tile);
    }

    @Override
    public IItemHandlerPM create(int size, @Nullable AbstractTilePM tile) {
        return new ItemStackHandlerPMForge(size, tile);
    }

    @Override
    public IItemHandlerPM create(NonNullList<ItemStack> stacks, @Nullable AbstractTilePM tile) {
        return new ItemStackHandlerPMForge(stacks, tile);
    }

    @Override
    public IItemHandlerPM.Builder builder(NonNullList<ItemStack> stacks, @Nullable AbstractTilePM tile) {
        return ItemStackHandlerPMForge.builder(stacks, tile);
    }

    @Override
    public IItemHandlerPM wrap(Container container, @Nullable Direction side) {
        if (container instanceof WorldlyContainer worldlyContainer) {
            return new SidedInvWrapperPMForge(worldlyContainer, side);
        } else {
            return new InvWrapperPMForge(container);
        }
    }
}
