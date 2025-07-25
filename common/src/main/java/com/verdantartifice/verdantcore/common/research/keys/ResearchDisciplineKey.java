package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.registries.RegistryEncodedResourceKey;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ResearchDisciplineKey extends AbstractResearchKey<ResearchDisciplineKey> {
    public static final MapCodec<ResearchDisciplineKey> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryEncodedResourceKey.<ResearchDiscipline>codec().fieldOf("key").forGetter(rek -> rek.key)
        ).apply(instance, ResearchDisciplineKey::new));

    public static final StreamCodec<ByteBuf, ResearchDisciplineKey> STREAM_CODEC = StreamCodec.composite(
            RegistryEncodedResourceKey.streamCodec(), rek -> rek.key,
            ResearchDisciplineKey::new
        );

    private static final ResourceLocation ICON_UNKNOWN = ResourceUtils.loc("textures/research/research_unknown.png");

    protected final RegistryEncodedResourceKey<ResearchDiscipline> key;

    public ResearchDisciplineKey(RegistryEncodedResourceKey<ResearchDiscipline> key) {
        this.key = key;
    }

    public ResearchDisciplineKey(ResourceKey<ResearchDiscipline> rootKey) {
        this(RegistryEncodedResourceKey.fromResourceKey(rootKey));
    }

    public ResourceKey<Registry<ResearchDiscipline>> getRegistryKey() {
        return this.key.registryKey();
    }
    
    public ResourceKey<ResearchDiscipline> getRootKey() {
        return this.key.toResourceKey();
    }

    @Override
    public String toString() {
        return this.getRootKey().location().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResearchDisciplineKey that)) return false;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
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
        return IconDefinition.of(registryAccess.registryOrThrow(this.getRegistryKey()).getHolder(this.getRootKey()).map(ref -> ref.value().iconLocation()).orElse(ICON_UNKNOWN));
    }

    @Override
    public boolean isKnownBy(@Nullable Player player, @NotNull IPlayerKnowledge knowledgeCapability) {
        if (player == null) {
            return false;
        }
        RegistryAccess registryAccess = player.level().registryAccess();
        Holder.Reference<ResearchDiscipline> discipline = registryAccess.registryOrThrow(this.getRegistryKey()).getHolderOrThrow(this.getRootKey());

        // If the discipline does have an unlock requirement, then the discipline is only known if that requirement is met
        // If the discipline has no unlock requirement, then it's known to the player
        return discipline.value().unlockRequirementOpt().map(req -> req.isMetBy(player)).orElse(true);
    }
    
    @Nonnull
    public static ResearchDisciplineKey fromNetwork(RegistryFriendlyByteBuf buf) {
        return (ResearchDisciplineKey)AbstractResearchKey.fromNetwork(buf);
    }
}
