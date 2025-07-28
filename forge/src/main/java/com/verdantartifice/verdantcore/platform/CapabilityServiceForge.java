package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerLinguistics;
import com.verdantartifice.verdantcore.common.capabilities.ItemStackHandlerVCForge;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import com.verdantartifice.verdantcore.platform.services.ICapabilityService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaInventoryCodeHooks;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CapabilityServiceForge implements ICapabilityService {
    private static final Map<ResourceLocation, Capability<IPlayerKnowledge>> KNOWLEDGE_TOKENS = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, Capability<IPlayerLinguistics>> LINGUISTICS_TOKENS = new ConcurrentHashMap<>();

    public static void registerKnowledgeToken(@NotNull ResourceLocation registryLocation, @NotNull Capability<IPlayerKnowledge> capability) {
        KNOWLEDGE_TOKENS.put(registryLocation, capability);
    }

    public static void registerLinguisticsToken(@NotNull ResourceLocation registryLocation, @NotNull Capability<IPlayerLinguistics> capability) {
        LINGUISTICS_TOKENS.put(registryLocation, capability);
    }

    @NotNull
    private Optional<Capability<IPlayerKnowledge>> lookupKnowledgeToken(@NotNull ResourceLocation registryLocation) {
        return Optional.ofNullable(KNOWLEDGE_TOKENS.get(registryLocation));
    }

    @Override
    public Optional<IPlayerKnowledge> knowledge(@Nullable Player player, @Nullable ResourceLocation registryLocation) {
        return player == null || registryLocation == null ?
                Optional.empty() :
                lookupKnowledgeToken(registryLocation).flatMap(token -> player.getCapability(token).resolve());
    }

    @NotNull
    private Optional<Capability<IPlayerLinguistics>> lookupLinguisticsToken(@NotNull ResourceLocation registryLocation) {
        return Optional.ofNullable(LINGUISTICS_TOKENS.get(registryLocation));
    }

    @Override
    public Optional<IPlayerLinguistics> linguistics(@Nullable Player player, @Nullable ResourceLocation registryLocation) {
        return player == null || registryLocation == null ?
                Optional.empty() :
                lookupLinguisticsToken(registryLocation).flatMap(token -> player.getCapability(token).resolve());
    }

    @Override
    public Optional<IItemHandlerVC> itemHandler(@NotNull Level level, @NotNull BlockPos pos, @Nullable Direction face) {
        Optional<Pair<IItemHandler, Object>> optional = VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), face);
        Pair<IItemHandler, Object> pair = optional.orElse(null);
        if (pair != null && pair.getLeft() != null) {
            // If the tile entity directly provides an item handler capability, return that
            if (pair.getLeft() instanceof IItemHandlerVC castHandler) {
                return Optional.of(castHandler);
            } else {
                return Optional.of(new ItemStackHandlerVCForge(pair.getLeft(), null));
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
