package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.capabilities.IFluidHandlerVC;
import com.verdantartifice.verdantcore.common.fluids.IFluidStackVC;

import java.util.function.Predicate;

public interface IFluidHandlerService {
    IFluidHandlerVC create(int capacity);
    IFluidHandlerVC create(int capacity, Predicate<IFluidStackVC> validator);
}
