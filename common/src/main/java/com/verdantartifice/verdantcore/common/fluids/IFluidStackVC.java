package com.verdantartifice.verdantcore.common.fluids;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;

/**
 * Common interface for a loader-specific fluid stack.  Needed so that loader-agnostic code can work with fluids.
 *
 * @author Daedalus4096
 */
public interface IFluidStackVC {
    Fluid getFluid();
    int getAmount();
    boolean isEmpty();
    boolean is(Fluid fluid);
    Component getHoverName();
}
