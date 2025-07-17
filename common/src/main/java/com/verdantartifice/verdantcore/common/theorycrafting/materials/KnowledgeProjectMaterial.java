package com.verdantartifice.verdantcore.common.theorycrafting.materials;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.research.KnowledgeType;
import com.verdantartifice.verdantcore.common.research.ResearchManagerVC;
import com.verdantartifice.verdantcore.common.research.requirements.AbstractRequirement;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Definition of a project material that requires one or more observations, which may or may not be consumed as part
 * of the research project.
 * 
 * @author Daedalus4096
 */
public class KnowledgeProjectMaterial extends AbstractProjectMaterial<KnowledgeProjectMaterial> {
    public static MapCodec<KnowledgeProjectMaterial> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                KnowledgeType.CODEC.fieldOf("knowledgeType").forGetter(KnowledgeProjectMaterial::getKnowledgeType),
                ExtraCodecs.POSITIVE_INT.fieldOf("count").forGetter(KnowledgeProjectMaterial::getCount),
                Codec.BOOL.fieldOf("consumed").forGetter(KnowledgeProjectMaterial::isConsumed),
                Codec.DOUBLE.fieldOf("weight").forGetter(KnowledgeProjectMaterial::getWeight),
                Codec.DOUBLE.fieldOf("bonusReward").forGetter(KnowledgeProjectMaterial::getBonusReward),
                AbstractRequirement.dispatchCodec().optionalFieldOf("requirement").forGetter(KnowledgeProjectMaterial::getRequirement)
            ).apply(instance, KnowledgeProjectMaterial::new));
    }
    
    public static StreamCodec<RegistryFriendlyByteBuf, KnowledgeProjectMaterial> streamCodec() {
        return StreamCodec.composite(
                KnowledgeType.STREAM_CODEC, KnowledgeProjectMaterial::getKnowledgeType,
                ByteBufCodecs.VAR_INT, KnowledgeProjectMaterial::getCount,
                ByteBufCodecs.BOOL, KnowledgeProjectMaterial::isConsumed,
                ByteBufCodecs.DOUBLE, KnowledgeProjectMaterial::getWeight,
                ByteBufCodecs.DOUBLE, KnowledgeProjectMaterial::getBonusReward,
                ByteBufCodecs.optional(AbstractRequirement.dispatchStreamCodec()), KnowledgeProjectMaterial::getRequirement,
                KnowledgeProjectMaterial::new);
    }

    protected final KnowledgeType knowledgeType;
    protected final int count;
    protected final boolean consumed;
    
    protected KnowledgeProjectMaterial(KnowledgeType knowledgeType, int count, boolean consumed, double weight, double bonusReward, Optional<AbstractRequirement<?>> requirement) {
        super(weight, bonusReward, requirement);
        this.knowledgeType = knowledgeType;
        this.count = count;
        this.consumed = consumed;
    }

    @Override
    protected ProjectMaterialType<KnowledgeProjectMaterial> getType() {
        return ProjectMaterialTypesVC.KNOWLEDGE.get();
    }

    @Override
    public boolean isSatisfied(Player player, Set<Block> surroundings) {
        IPlayerKnowledge knowledge = ServicesVC.CAPABILITIES.knowledge(player).orElse(null);
        return (knowledge != null && knowledge.getKnowledge(this.knowledgeType) >= this.count);
    }

    @Override
    public boolean consume(Player player) {
        // Deduct observation level(s) from the player's knowledge pool
        return ResearchManagerVC.addKnowledge(player, this.knowledgeType, -1 * this.count * this.knowledgeType.getProgression());
    }

    public KnowledgeType getKnowledgeType() {
        return this.knowledgeType;
    }
    
    public int getCount() {
        return this.count;
    }
    
    @Override
    public boolean isConsumed() {
        return this.consumed;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KnowledgeProjectMaterial that)) return false;
        if (!super.equals(o)) return false;
        return count == that.count && consumed == that.consumed && Objects.equals(knowledgeType, that.knowledgeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), knowledgeType, count, consumed);
    }

    public static Builder builder(KnowledgeType knowledgeType, int count) {
        return new Builder(knowledgeType, count);
    }
    
    public static class Builder extends AbstractProjectMaterial.Builder<KnowledgeProjectMaterial, Builder> {
        protected final KnowledgeType knowledgeType;
        protected final int count;
        protected boolean consumed = false;
        
        protected Builder(KnowledgeType knowledgeType, int count) {
            this.knowledgeType = knowledgeType;
            this.count = count;
        }
        
        public Builder consumed() {
            this.consumed = true;
            return this;
        }
        
        @Override
        protected void validate() {
            super.validate();
            if (this.count <= 0) {
                throw new IllegalStateException("Material count must be positive");
            }
        }

        @Override
        public KnowledgeProjectMaterial build() {
            this.validate();
            return new KnowledgeProjectMaterial(this.knowledgeType, this.count, this.consumed, this.weight, this.bonusReward, this.getFinalRequirement());
        }
    }
}
