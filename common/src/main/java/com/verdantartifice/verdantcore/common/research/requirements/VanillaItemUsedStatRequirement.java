package com.verdantartifice.verdantcore.common.research.requirements;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.client.util.ClientUtilsVC;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.stream.Stream;

/**
 * Requirement that the player has accumulated at least the given amount of the given Minecraft statistic.
 * 
 * @author Daedalus4096
 */
public class VanillaItemUsedStatRequirement extends AbstractRequirement<VanillaItemUsedStatRequirement> implements IVanillaStatRequirement {
    public static final MapCodec<VanillaItemUsedStatRequirement> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("item").xmap(loc -> ServicesVC.ITEMS_REGISTRY.get(loc), item -> ServicesVC.ITEMS_REGISTRY.getKey(item)).forGetter(req -> req.stat.getValue()),
            Codec.INT.fieldOf("threshold").forGetter(VanillaItemUsedStatRequirement::getThreshold)
        ).apply(instance, VanillaItemUsedStatRequirement::new));
    public static final StreamCodec<ByteBuf, VanillaItemUsedStatRequirement> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC.map(loc -> ServicesVC.ITEMS_REGISTRY.get(loc), item -> ServicesVC.ITEMS_REGISTRY.getKey(item)),
            req -> req.stat.getValue(),
            ByteBufCodecs.VAR_INT,
            VanillaItemUsedStatRequirement::getThreshold,
            VanillaItemUsedStatRequirement::new);
    
    protected final Stat<Item> stat;
    protected final int threshold;
    
    public VanillaItemUsedStatRequirement(Item item, int threshold) {
        this(Stats.ITEM_USED.get(Preconditions.checkNotNull(item)), threshold);
    }

    protected VanillaItemUsedStatRequirement(Stat<Item> stat, int threshold) {
        this.stat = stat;
        this.threshold = threshold;
    }
    
    @Override
    public Stat<?> getStat() {
        return this.stat;
    }

    @Override
    public ResourceLocation getStatTypeLoc() {
        return BuiltInRegistries.STAT_TYPE.getKey(this.stat.getType());
    }
    
    @Override
    public ResourceLocation getStatValueLoc() {
        return ServicesVC.ITEMS_REGISTRY.getKey(this.stat.getValue());
    }

    @Override
    public int getThreshold() {
        return this.threshold;
    }
    
    @Override
    public Component getStatDescription() {
        Component baseLabel = this.stat.getType().getDisplayName();
        Component itemLabel = this.stat.getValue().getDescription();
        return Component.translatable("tooltip.verdantcore.stat_description.vanilla", baseLabel, itemLabel);
    }

    @Override
    public IconDefinition getIconDefinition() {
        return IconDefinition.of(this.stat.getValue());
    }

    public int getCurrentValue(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            return serverPlayer.getStats().getValue(this.stat);
        } else if (ServicesVC.PLATFORM.isClientDist()) {
            return ClientUtilsVC.getStatsCounter().getValue(this.stat);
        } else {
            throw new IllegalStateException("Player is neither server nor client side!");
        }
    }

    @Override
    public boolean isMetBy(Player player) {
        return this.getCurrentValue(player) >= this.threshold;
    }

    @Override
    public void consumeComponents(Player player) {
        // No action needed; statistics are never consumed
    }

    @Override
    public boolean forceComplete(Player player) {
        if (player instanceof ServerPlayer serverPlayer && this.threshold > this.getCurrentValue(serverPlayer)) {
            serverPlayer.getStats().setValue(serverPlayer, this.stat, this.threshold);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RequirementCategory getCategory() {
        return RequirementCategory.STAT;
    }

    @Override
    public Stream<AbstractRequirement<?>> streamByCategory(RequirementCategory category) {
        return category == this.getCategory() ? Stream.of(this) : Stream.empty();
    }

    @Override
    protected RequirementType<VanillaItemUsedStatRequirement> getType() {
        return RequirementsVC.VANILLA_ITEM_USED_STAT.get();
    }
}
