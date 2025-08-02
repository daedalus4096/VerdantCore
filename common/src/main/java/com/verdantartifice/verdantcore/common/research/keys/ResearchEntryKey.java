package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.registries.RegistryEncodedResourceKey;
import com.verdantartifice.verdantcore.common.research.ResearchEntry;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ResearchEntryKey extends AbstractResearchKey<ResearchEntryKey> {
    public static final MapCodec<ResearchEntryKey> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryEncodedResourceKey.<ResearchEntry>codec().fieldOf("key").forGetter(rek -> rek.key)
        ).apply(instance, ResearchEntryKey::new));

    public static final StreamCodec<ByteBuf, ResearchEntryKey> STREAM_CODEC = StreamCodec.composite(
            RegistryEncodedResourceKey.streamCodec(), rek -> rek.key,
            ResearchEntryKey::new
        );

    private static final ResourceLocation ICON_UNKNOWN = ResourceUtils.loc("textures/research/research_unknown.png");

    protected final RegistryEncodedResourceKey<ResearchEntry> key;

    public ResearchEntryKey(RegistryEncodedResourceKey<ResearchEntry> key) {
        this.key = key;
    }
    
    public ResearchEntryKey(ResourceKey<ResearchEntry> rootKey) {
        this(RegistryEncodedResourceKey.fromResourceKey(rootKey));
    }

    @Override
    public ResourceLocation getRegistryLocation() {
        return this.getRegistryKey().location();
    }

    public ResourceKey<Registry<ResearchEntry>> getRegistryKey() {
        return this.key.registryKey();
    }
    
    public ResourceKey<ResearchEntry> getRootKey() {
        return this.key.toResourceKey();
    }

    @Override
    public String toString() {
        return this.getRootKey().location().toString();
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
        return registryAccess.registryOrThrow(this.getRegistryKey()).getHolder(this.getRootKey()).flatMap(ref -> ref.value().iconOpt()).orElse(IconDefinition.of(ICON_UNKNOWN));
    }

    @Override
    protected Optional<IPlayerKnowledge> getPlayerKnowledge(@Nullable Player player) {
        return ServicesVC.CAPABILITIES.knowledge(player, this.getRegistryKey().location());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResearchEntryKey that)) return false;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    public static ResearchEntryKey fromNetwork(RegistryFriendlyByteBuf buf) {
        return (ResearchEntryKey)AbstractResearchKey.fromNetwork(buf);
    }
}
