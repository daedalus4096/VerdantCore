package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ICapabilityService {
    /**
     * Attempts to get an item handler capability for the given side of the given position in the given world.
     * First searches for tiles that directly implement the capability, then attempts to wrap instances of the
     * vanilla inventory interface.
     *
     * @param level the level containing the desired tile entity
     * @param pos the position of the desired tile entity
     * @param face the side of the tile entity to be queried
     * @return an optional containing the item handler of the tile entity, or empty if no such capability could be found
     */
    Optional<IItemHandlerVC> itemHandler(@NotNull Level level, @NotNull BlockPos pos, @Nullable Direction face);
    Optional<IItemHandlerVC> itemHandler(@Nullable AbstractTileVC tile, @Nullable Direction face);
}
