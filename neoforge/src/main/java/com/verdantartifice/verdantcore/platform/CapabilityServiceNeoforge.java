package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerLinguistics;
import com.verdantartifice.verdantcore.common.capabilities.ItemStackHandlerVCNeoforge;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileVC;
import com.verdantartifice.verdantcore.platform.services.ICapabilityService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class CapabilityServiceNeoforge implements ICapabilityService {
    private static final Map<ResourceLocation, Supplier<AttachmentType<IPlayerKnowledge>>> KNOWLEDGE_TOKENS = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, Supplier<AttachmentType<IPlayerLinguistics>>> LINGUISTICS_TOKENS = new ConcurrentHashMap<>();

    public static void registerKnowledgeToken(@NotNull ResourceLocation registryLoc, @NotNull Supplier<AttachmentType<IPlayerKnowledge>> capability) {
        KNOWLEDGE_TOKENS.put(registryLoc, capability);
    }

    public static void registerLinguisticsToken(@NotNull ResourceLocation registryLoc, @NotNull Supplier<AttachmentType<IPlayerLinguistics>> capability) {
        LINGUISTICS_TOKENS.put(registryLoc, capability);
    }

    @NotNull
    private Optional<Supplier<AttachmentType<IPlayerKnowledge>>> lookupKnowledgeToken(@NotNull ResourceLocation registryLocation) {
        return Optional.ofNullable(KNOWLEDGE_TOKENS.get(registryLocation));
    }

    @Override
    public Optional<IPlayerKnowledge> knowledge(@Nullable Player player, @Nullable ResourceLocation registryLocation) {
        return player == null || registryLocation == null ?
                Optional.empty() :
                lookupKnowledgeToken(registryLocation).map(player::getData);
    }

    @NotNull
    private Optional<Supplier<AttachmentType<IPlayerLinguistics>>> lookupLinguisticsToken(@NotNull ResourceLocation registryLoc) {
        return Optional.ofNullable(LINGUISTICS_TOKENS.get(registryLoc));
    }

    @Override
    public Optional<IPlayerLinguistics> linguistics(@Nullable Player player, @Nullable ResourceLocation registryLocation) {
        return player == null || registryLocation == null ?
                Optional.empty() :
                lookupLinguisticsToken(registryLocation).map(player::getData);
    }

    @Override
    public Optional<IItemHandlerVC> itemHandler(@NotNull Level level, @NotNull BlockPos pos, @Nullable Direction face) {
        IItemHandler neoforgeHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, face);
        if (neoforgeHandler instanceof IItemHandlerVC castHandler) {
            // If the tile entity directly provides an appropriate item handler capability, return that
            return Optional.of(castHandler);
        } else if  (neoforgeHandler != null) {
            // If the tile entity provides an item handler capability in need of wrapping, do so
            return Optional.of(new ItemStackHandlerVCNeoforge(neoforgeHandler, null));
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
