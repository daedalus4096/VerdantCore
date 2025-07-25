package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.research.ResearchEntry;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ResearchStageKey extends AbstractResearchKey<ResearchStageKey> {
    public static final MapCodec<ResearchStageKey> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceKey.codec(RegistryKeysVC.RESEARCH_ENTRIES).fieldOf("rootKey").forGetter(ResearchStageKey::getRootKey), 
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("stage").forGetter(ResearchStageKey::getStage)
        ).apply(instance, ResearchStageKey::new));
    public static final StreamCodec<ByteBuf, ResearchStageKey> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(RegistryKeysVC.RESEARCH_ENTRIES),
            ResearchStageKey::getRootKey,
            ByteBufCodecs.VAR_INT,
            ResearchStageKey::getStage,
            ResearchStageKey::new);
    
    private static final ResourceLocation ICON_UNKNOWN = ResourceUtils.loc("textures/research/research_unknown.png");

    protected final ResourceKey<ResearchEntry> rootKey;
    protected final int stage;
    protected final ResearchEntryKey strippedKey;
    
    public ResearchStageKey(ResourceKey<ResearchEntry> rootKey, int stage) {
        this.rootKey = rootKey;
        this.stage = stage;
        this.strippedKey = new ResearchEntryKey(rootKey);
    }
    
    public ResourceKey<ResearchEntry> getRootKey() {
        return this.rootKey;
    }
    
    public int getStage() {
        return this.stage;
    }
    
    @Override
    public String toString() {
        return this.rootKey.location().toString() + "@" + this.stage;
    }

    @Override
    public RequirementCategory getRequirementCategory() {
        return RequirementCategory.RESEARCH;
    }

    @Override
    protected ResearchKeyType<ResearchStageKey> getType() {
        return ResearchKeyTypesVC.RESEARCH_STAGE.get();
    }

    @Override
    public IconDefinition getIcon(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(RegistryKeysVC.RESEARCH_ENTRIES).getHolder(this.rootKey).flatMap(ref -> ref.value().iconOpt()).orElse(IconDefinition.of(ICON_UNKNOWN));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rootKey.registry(), this.rootKey.location(), this.stage);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResearchStageKey other = (ResearchStageKey) obj;
        return Objects.equals(rootKey, other.rootKey) && stage == other.stage;
    }

    @Override
    public boolean isKnownBy(@Nullable Player player, @NotNull IPlayerKnowledge knowledge) {
        if (player == null) {
            return false;
        }

        // Rather than testing if the research entry is complete (because it probably won't be if you're using
        // this key), test if the research stage is at least as high as the one specified in this key.
        int currentStage = knowledge.getResearchStage(this.strippedKey) + 1;
        return currentStage >= this.getStage();
    }
}
