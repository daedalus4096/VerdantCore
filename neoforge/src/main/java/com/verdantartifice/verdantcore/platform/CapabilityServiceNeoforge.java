package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.CapabilitiesNeoforge;
import com.verdantartifice.verdantcore.common.capabilities.IEntitySwappers;
import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerPM;
import com.verdantartifice.verdantcore.common.capabilities.IManaStorage;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerArcaneRecipeBook;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerAttunements;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerCompanions;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerCooldowns;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerLinguistics;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerStats;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerWard;
import com.verdantartifice.verdantcore.common.capabilities.ItemStackHandlerPMNeoforge;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTilePM;
import com.verdantartifice.verdantcore.platform.services.ICapabilityService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CapabilityServiceNeoforge implements ICapabilityService {
    @Override
    public Optional<IPlayerKnowledge> knowledge(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.KNOWLEDGE));
    }

    @Override
    public Optional<IPlayerCooldowns> cooldowns(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.COOLDOWNS));
    }

    @Override
    public Optional<IPlayerStats> stats(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.STATS));
    }

    @Override
    public Optional<IPlayerAttunements> attunements(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.ATTUNEMENTS));
    }

    @Override
    public Optional<IPlayerCompanions> companions(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.COMPANIONS));
    }

    @Override
    public Optional<IPlayerWard> ward(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.WARD));
    }

    @Override
    public Optional<IPlayerLinguistics> linguistics(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.LINGUISTICS));
    }

    @Override
    public Optional<IPlayerArcaneRecipeBook> arcaneRecipeBook(Player player) {
        return player == null ? Optional.empty() : Optional.of(player.getData(CapabilitiesNeoforge.ARCANE_RECIPE_BOOK));
    }

    @Override
    public Optional<IEntitySwappers> swappers(Entity entity) {
        return entity == null ? Optional.empty() : Optional.of(entity.getData(CapabilitiesNeoforge.ENTITY_SWAPPERS));
    }

    @Override
    public Optional<IItemHandlerPM> itemHandler(@NotNull Level level, @NotNull BlockPos pos, @Nullable Direction face) {
        IItemHandler neoforgeHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, face);
        if (neoforgeHandler instanceof IItemHandlerPM castHandler) {
            // If the tile entity directly provides an appropriate item handler capability, return that
            return Optional.of(castHandler);
        } else if  (neoforgeHandler != null) {
            // If the tile entity provides an item handler capability in need of wrapping, do so
            return Optional.of(new ItemStackHandlerPMNeoforge(neoforgeHandler, null));
        } else if (level.getBlockEntity(pos) instanceof Container container) {
            // If the tile entity does not provide an item handler but does have an inventory, return a wrapper around that
            return Optional.ofNullable(ServicesVC.ITEM_HANDLERS.wrap(container, face));
        } else {
            // If the tile entity does not have an inventory at all, return null
            return Optional.empty();
        }
    }

    @Override
    public Optional<IItemHandlerPM> itemHandler(AbstractTilePM tile, Direction face) {
        if (tile == null || tile.getLevel() == null) {
            return Optional.empty();
        } else {
            return this.itemHandler(tile.getLevel(), tile.getBlockPos(), face);
        }
    }

    @Override
    public Optional<IManaStorage<?>> manaStorage(@NotNull Level level, @NotNull BlockPos pos, @Nullable Direction face) {
        IManaStorage<?> cap = level.getCapability(CapabilitiesNeoforge.MANA_STORAGE, pos, null);
        return Optional.ofNullable(cap);
    }

    @Override
    public Optional<IManaStorage<?>> manaStorage(@Nullable AbstractTilePM tile, @Nullable Direction face) {
        if (tile == null || tile.getLevel() == null) {
            return Optional.empty();
        } else {
            return this.manaStorage(tile.getLevel(), tile.getBlockPos(), face);
        }
    }
}
