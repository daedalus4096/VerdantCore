package com.verdantartifice.verdantcore.common.rewards;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.Optional;

/**
 * Reward that grants experience points.
 * 
 * @author Daedalus4096
 */
public class ExperienceReward extends AbstractReward<ExperienceReward> {
    public static final MapCodec<ExperienceReward> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("points").forGetter(r -> r.points)
        ).apply(instance, ExperienceReward::new));
    
    public static final StreamCodec<ByteBuf, ExperienceReward> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, reward -> reward.points,
            ExperienceReward::new);
    
    private final int points;
    
    public ExperienceReward(int points) {
        this.points = points;
    }

    @Override
    protected RewardType<ExperienceReward> getType() {
        return RewardTypesVC.EXPERIENCE.get();
    }

    @Override
    public void grant(ServerPlayer player) {
        player.giveExperiencePoints(this.points);
    }

    @Override
    public Component getDescription(Player player) {
        Component label = Component.translatable("label.verdantcore.experience.points");
        return Component.translatable("label.verdantcore.research_table.reward", this.points, label);
    }

    @Override
    public ResourceLocation getIconLocation(Player player) {
        // FIXME Stub
        return null;
    }

    @Override
    public Optional<Component> getAmountText() {
        return Optional.of(Component.literal(Integer.toString(this.points)));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ExperienceReward that)) return false;
        return points == that.points;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(points);
    }
}
