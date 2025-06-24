package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.FluidHandlerPMNeoforge;
import com.verdantartifice.verdantcore.common.capabilities.IFluidHandlerPM;
import com.verdantartifice.verdantcore.common.fluids.IFluidStackPM;
import com.verdantartifice.verdantcore.platform.services.IFluidHandlerService;

import java.util.function.Predicate;

public class FluidHandlerServiceNeoforge implements IFluidHandlerService {
    @Override
    public IFluidHandlerPM create(int capacity) {
        return new FluidHandlerPMNeoforge(capacity);
    }

    @Override
    public IFluidHandlerPM create(int capacity, Predicate<IFluidStackPM> validator) {
        return new FluidHandlerPMNeoforge(capacity, validator);
    }
}
