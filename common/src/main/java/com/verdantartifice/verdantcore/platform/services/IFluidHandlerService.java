package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.capabilities.IFluidHandlerPM;
import com.verdantartifice.verdantcore.common.fluids.IFluidStackPM;

import java.util.function.Predicate;

public interface IFluidHandlerService {
    IFluidHandlerPM create(int capacity);
    IFluidHandlerPM create(int capacity, Predicate<IFluidStackPM> validator);
}
