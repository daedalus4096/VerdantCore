package com.verdantartifice.verdantcore.common.capabilities;

import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import com.verdantartifice.verdantcore.common.util.RecipeContainerWrapper;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Extension of the default IItemHandler implementation which updates its owning tile's client
 * side when the contents of the capability change.
 * 
 * @author Daedalus4096
 */
public class ItemStackHandlerVCNeoforge extends ItemStackHandler implements IItemHandlerVC {
    protected final AbstractTileVC tile;
    protected final Optional<Function<Integer, Integer>> limitFuncOverride;
    protected final Optional<BiPredicate<Integer, ItemStack>> validityFuncOverride;
    protected final Optional<Consumer<Integer>> contentsChangedFuncOverride;

    public ItemStackHandlerVCNeoforge(AbstractTileVC tile) {
        super();
        this.tile = tile;
        this.limitFuncOverride = Optional.empty();
        this.validityFuncOverride = Optional.empty();
        this.contentsChangedFuncOverride = Optional.empty();
    }

    public ItemStackHandlerVCNeoforge(int size, AbstractTileVC tile) {
        super(size);
        this.tile = tile;
        this.limitFuncOverride = Optional.empty();
        this.validityFuncOverride = Optional.empty();
        this.contentsChangedFuncOverride = Optional.empty();
    }

    public ItemStackHandlerVCNeoforge(NonNullList<ItemStack> stacks, AbstractTileVC tile) {
        super(stacks);
        this.tile = tile;
        this.limitFuncOverride = Optional.empty();
        this.validityFuncOverride = Optional.empty();
        this.contentsChangedFuncOverride = Optional.empty();
    }

    public ItemStackHandlerVCNeoforge(IItemHandler original, @Nullable AbstractTileVC tile) {
        super(Util.make(NonNullList.createWithCapacity(original.getSlots()), newList -> {
            for (int i = 0; i < original.getSlots(); i++) {
                newList.add(original.getStackInSlot(i));
            }
        }));
        this.tile = tile;
        this.limitFuncOverride = Optional.empty();
        this.validityFuncOverride = Optional.empty();
        this.contentsChangedFuncOverride = Optional.empty();
    }

    protected ItemStackHandlerVCNeoforge(NonNullList<ItemStack> stacks, AbstractTileVC tile, Optional<Function<Integer, Integer>> limit,
                                         Optional<BiPredicate<Integer, ItemStack>> validity, Optional<Consumer<Integer>> contentsChanged) {
        super(stacks);
        this.tile = tile;
        this.limitFuncOverride = limit;
        this.validityFuncOverride = validity;
        this.contentsChangedFuncOverride = contentsChanged;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.limitFuncOverride.map(f -> f.apply(slot)).orElseGet(() -> super.getSlotLimit(slot));
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.validityFuncOverride.map(f -> f.test(slot, stack)).orElseGet(() -> super.isItemValid(slot, stack));
    }

    @Override
    public Container asContainer() {
        return new RecipeContainerWrapper(this);
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (this.tile != null) {
            this.tile.syncTile(true);
            this.tile.setChanged();
        }
        this.contentsChangedFuncOverride.ifPresent(c -> c.accept(slot));
    }

    public static Builder builder(NonNullList<ItemStack> stacks, AbstractTileVC tile) {
        return new Builder(stacks, tile);
    }

    public static class Builder implements IItemHandlerVC.Builder {
        private final NonNullList<ItemStack> stacks;
        private final AbstractTileVC tile;
        private Optional<Function<Integer, Integer>> limitFuncOverride = Optional.empty();
        private Optional<BiPredicate<Integer, ItemStack>> validityFuncOverride = Optional.empty();
        private Optional<Consumer<Integer>> contentsChangedFuncOverride = Optional.empty();

        public Builder(NonNullList<ItemStack> stacks, AbstractTileVC tile) {
            this.stacks = stacks;
            this.tile = tile;
        }

        @Override
        public IItemHandlerVC.Builder slotLimitFunction(Function<Integer, Integer> limitFunction) {
            this.limitFuncOverride = Optional.of(limitFunction);
            return this;
        }

        @Override
        public IItemHandlerVC.Builder itemValidFunction(BiPredicate<Integer, ItemStack> itemValidFunction) {
            this.validityFuncOverride = Optional.of(itemValidFunction);
            return this;
        }

        @Override
        public IItemHandlerVC.Builder contentsChangedFunction(Consumer<Integer> contentsChangedFunction) {
            this.contentsChangedFuncOverride = Optional.of(contentsChangedFunction);
            return this;
        }

        @Override
        public IItemHandlerVC build() {
            return new ItemStackHandlerVCNeoforge(this.stacks, this.tile, this.limitFuncOverride,
                    this.validityFuncOverride, this.contentsChangedFuncOverride);
        }
    }
}
