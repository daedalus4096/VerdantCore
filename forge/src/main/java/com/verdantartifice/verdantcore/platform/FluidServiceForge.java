package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.fluids.FluidStackPMForge;
import com.verdantartifice.verdantcore.common.fluids.IFluidStackVC;
import com.verdantartifice.verdantcore.platform.services.IFluidService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public class FluidServiceForge implements IFluidService {
    @Override
    public @NotNull IFluidStackVC emptyStack() {
        return FluidStackPMForge.EMPTY;
    }

    @Override
    public @NotNull IFluidStackVC makeFluidStack(@NotNull Fluid fluid, int amount) {
        return new FluidStackPMForge(fluid, amount);
    }

    @Override
    public boolean canConvertToSource(@NotNull FlowingFluid fluid, @NotNull FluidState state, @NotNull Level level, @NotNull BlockPos pos) {
        return fluid.canConvertToSource(state, level, pos);
    }
}
