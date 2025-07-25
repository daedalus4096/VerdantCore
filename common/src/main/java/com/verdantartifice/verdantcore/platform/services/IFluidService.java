package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.fluids.IFluidStackVC;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public interface IFluidService {
    @NotNull IFluidStackVC emptyStack();
    @NotNull IFluidStackVC makeFluidStack(@NotNull Fluid fluid, int amount);
    boolean canConvertToSource(@NotNull FlowingFluid fluid, @NotNull FluidState state, @NotNull Level level, @NotNull BlockPos pos);
}
