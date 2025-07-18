package com.verdantartifice.verdantcore.common.capabilities;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Common interface mirroring the Forge and Neoforge item handler capability interfaces.
 * Provided here so that common code has a usable reference to capability functions.
 */
public interface IItemHandlerVC {
    int getSlots();

    ItemStack getStackInSlot(int slot);

    ItemStack insertItem(int slot, ItemStack stack, boolean simulate);

    ItemStack extractItem(int slot, int amount, boolean simulate);

    int getSlotLimit(int slot);

    boolean isItemValid(int slot, ItemStack stack);

    void setStackInSlot(int slot, ItemStack stack);

    Container asContainer();

    interface Builder {
        Builder slotLimitFunction(Function<Integer, Integer> limitFunction);
        Builder itemValidFunction(BiPredicate<Integer, ItemStack> itemValidFunction);
        Builder contentsChangedFunction(Consumer<Integer> contentsChangedFunction);
        IItemHandlerVC build();
    }
}
