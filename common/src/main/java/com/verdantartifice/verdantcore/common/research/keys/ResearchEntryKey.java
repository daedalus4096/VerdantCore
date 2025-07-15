package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.research.ResearchEntry;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ResearchEntryKey extends AbstractResearchKey<ResearchEntryKey> {
    public static final MapCodec<ResearchEntryKey> CODEC = Codec.mapPair(ResourceLocation.CODEC.fieldOf("registry"), ResourceLocation.CODEC.fieldOf("entry"))
            .xmap(
                    pair -> {
                        ResourceKey<Registry<ResearchEntry>> registryKey = ResourceKey.createRegistryKey(pair.getFirst());
                        return new ResearchEntryKey(registryKey, ResourceKey.create(registryKey, pair.getSecond()));
                    },
                    key -> new Pair<>(key.getRegistryKey().location(), key.getRootKey().location())
            );

    public static final StreamCodec<ByteBuf, ResearchEntryKey> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, key -> key.getRegistryKey().location(),
            ResourceLocation.STREAM_CODEC, key -> key.getRootKey().location(),
            (loc1, loc2) -> {
                ResourceKey<Registry<ResearchEntry>> registryKey = ResourceKey.createRegistryKey(loc1);
                return new ResearchEntryKey(registryKey, ResourceKey.create(registryKey, loc2));
            }
    );

    private static final ResourceLocation ICON_UNKNOWN = ResourceUtils.loc("textures/research/research_unknown.png");

    protected final ResourceKey<Registry<ResearchEntry>> registryKey;
    protected final ResourceKey<ResearchEntry> rootKey;
    
    public ResearchEntryKey(ResourceKey<Registry<ResearchEntry>> registryKey, ResourceKey<ResearchEntry> rootKey) {
        this.registryKey = registryKey;
        this.rootKey = rootKey;
    }

    public ResourceKey<Registry<ResearchEntry>> getRegistryKey() {
        return this.registryKey;
    }
    
    public ResourceKey<ResearchEntry> getRootKey() {
        return this.rootKey;
    }

    @Override
    public String toString() {
        return this.rootKey.location().toString();
    }

    @Override
    public RequirementCategory getRequirementCategory() {
        return RequirementCategory.RESEARCH;
    }

    @Override
    protected ResearchKeyType<ResearchEntryKey> getType() {
        return ResearchKeyTypesVC.RESEARCH_ENTRY.get();
    }

    @Override
    public IconDefinition getIcon(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(this.registryKey).getHolder(this.rootKey).flatMap(ref -> ref.value().iconOpt()).orElse(IconDefinition.of(ICON_UNKNOWN));
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
        ResearchEntryKey other = (ResearchEntryKey) obj;
        return Objects.equals(rootKey, other.rootKey);
    }
    
    public static ResearchEntryKey fromNetwork(RegistryFriendlyByteBuf buf) {
        return (ResearchEntryKey)AbstractResearchKey.fromNetwork(buf);
    }
}
