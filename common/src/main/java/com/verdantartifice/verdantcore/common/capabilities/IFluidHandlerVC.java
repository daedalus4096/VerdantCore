package com.verdantartifice.verdantcore.common.capabilities;

import com.verdantartifice.verdantcore.common.fluids.IFluidStackVC;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

/**
 * Common interface mirroring the Forge and Neoforge fluid handler capability interfaces.
 * Provided here so that common code has a usable reference to capability functions.
 */
public interface IFluidHandlerVC {
    int getTanks();
    IFluidStackVC getFluidInTank(int tank);
    int getTankCapacity(int tank);
    boolean isFluidValid(int tank, IFluidStackVC stack);
    int fill(IFluidStackVC stack, boolean simulate);
    IFluidStackVC drain(IFluidStackVC stack, boolean simulate);
    IFluidStackVC drain(int maxDrain, boolean simulate);
    IFluidHandlerVC readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt);
    CompoundTag writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt);
}
