package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.capabilities.ItemStackHandlerPMForge;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import com.verdantartifice.verdantcore.platform.services.ICapabilityService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaInventoryCodeHooks;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CapabilityServiceForge implements ICapabilityService {
    @Override
    public Optional<IItemHandlerVC> itemHandler(@NotNull Level level, @NotNull BlockPos pos, @Nullable Direction face) {
        Optional<Pair<IItemHandler, Object>> optional = VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), face);
        Pair<IItemHandler, Object> pair = optional.orElse(null);
        if (pair != null && pair.getLeft() != null) {
            // If the tile entity directly provides an item handler capability, return that
            if (pair.getLeft() instanceof IItemHandlerVC castHandler) {
                return Optional.of(castHandler);
            } else {
                return Optional.of(new ItemStackHandlerPMForge(pair.getLeft(), null));
            }
        } else if (level.getBlockEntity(pos) instanceof Container container) {
            // If the tile entity does not provide an item handler but does have an inventory, return a wrapper around that
            return Optional.ofNullable(ServicesVC.ITEM_HANDLERS.wrap(container, face));
        } else {
            // If the tile entity does not have an inventory at all, return null
            return Optional.empty();
        }
    }

    @Override
    public Optional<IItemHandlerVC> itemHandler(AbstractTileVC tile, Direction face) {
        if (tile == null || tile.getLevel() == null) {
            return Optional.empty();
        } else {
            return this.itemHandler(tile.getLevel(), tile.getBlockPos(), face);
        }
    }
}
