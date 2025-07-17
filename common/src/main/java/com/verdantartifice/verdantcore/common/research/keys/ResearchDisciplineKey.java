package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.research.ResearchDiscipline;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ResearchDisciplineKey extends AbstractResearchKey<ResearchDisciplineKey> {
    public static final MapCodec<ResearchDisciplineKey> CODEC = Codec.mapPair(ResourceLocation.CODEC.fieldOf("registry"), ResourceLocation.CODEC.fieldOf("discipline"))
            .xmap(
                    pair -> {
                        ResourceKey<Registry<ResearchDiscipline>> registryKey = ResourceKey.createRegistryKey(pair.getFirst());
                        return new ResearchDisciplineKey(registryKey, ResourceKey.create(registryKey, pair.getSecond()));
                    },
                    key -> new Pair<>(key.getRegistryKey().location(), key.getRootKey().location())
            );

    public static final StreamCodec<ByteBuf, ResearchDisciplineKey> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, key -> key.getRegistryKey().location(),
            ResourceLocation.STREAM_CODEC, key -> key.getRootKey().location(),
            (loc1, loc2) -> {
                ResourceKey<Registry<ResearchDiscipline>> registryKey = ResourceKey.createRegistryKey(loc1);
                return new ResearchDisciplineKey(registryKey, ResourceKey.create(registryKey, loc2));
            }
    );

    private static final ResourceLocation ICON_UNKNOWN = ResourceUtils.loc("textures/research/research_unknown.png");

    protected final ResourceKey<Registry<ResearchDiscipline>> registryKey;
    protected final ResourceKey<ResearchDiscipline> rootKey;
    
    public ResearchDisciplineKey(ResourceKey<Registry<ResearchDiscipline>> registryKey, ResourceKey<ResearchDiscipline> rootKey) {
        this.registryKey = registryKey;
        this.rootKey = rootKey;
    }

    public ResourceKey<Registry<ResearchDiscipline>> getRegistryKey() {
        return this.registryKey;
    }
    
    public ResourceKey<ResearchDiscipline> getRootKey() {
        return this.rootKey;
    }

    @Override
    public String toString() {
        return this.rootKey.location().toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rootKey.registry(), this.rootKey.location());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResearchDisciplineKey other = (ResearchDisciplineKey) obj;
        return Objects.equals(rootKey, other.rootKey);
    }

    @Override
    public RequirementCategory getRequirementCategory() {
        return RequirementCategory.RESEARCH;
    }

    @Override
    protected ResearchKeyType<ResearchDisciplineKey> getType() {
        return ResearchKeyTypesVC.RESEARCH_DISCIPLINE.get();
    }

    @Override
    public IconDefinition getIcon(RegistryAccess registryAccess) {
        return IconDefinition.of(registryAccess.registryOrThrow(this.registryKey).getHolder(this.rootKey).map(ref -> ref.value().iconLocation()).orElse(ICON_UNKNOWN));
    }

    @Override
    public boolean isKnownBy(@Nullable Player player, @NotNull IPlayerKnowledge knowledgeCapability) {
        if (player == null) {
            return false;
        }
        RegistryAccess registryAccess = player.level().registryAccess();
        Holder.Reference<ResearchDiscipline> discipline = registryAccess.registryOrThrow(this.registryKey).getHolderOrThrow(this.rootKey);

        // If the discipline does have an unlock requirement, then the discipline is only known if that requirement is met
        // If the discipline has no unlock requirement, then it's known to the player
        return discipline.value().unlockRequirementOpt().map(req -> req.isMetBy(player)).orElse(true);
    }
    
    @Nonnull
    public static ResearchDisciplineKey fromNetwork(RegistryFriendlyByteBuf buf) {
        return (ResearchDisciplineKey)AbstractResearchKey.fromNetwork(buf);
    }
}
