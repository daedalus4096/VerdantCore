package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.books.BookDefinition;
import com.verdantartifice.verdantcore.common.books.BookLanguage;
import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerLinguistics;
import com.verdantartifice.verdantcore.common.research.ResearchEntry;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ICapabilityService {
    Optional<IPlayerKnowledge> knowledge(@Nullable Player player, @Nullable ResourceLocation registryLocation);
    Optional<IPlayerLinguistics> linguistics(@Nullable Player player, @Nullable ResourceLocation registryLocation);

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
