package com.verdantartifice.verdantcore.common.research.keys;

import com.google.common.base.Preconditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.research.ResearchManagerVC;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.common.util.StreamCodecUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class TagCraftedKey extends AbstractResearchKey<TagCraftedKey> {
    public static final MapCodec<TagCraftedKey> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TagKey.codec(Registries.ITEM).fieldOf("tagKey").forGetter(k -> k.tagKey),
            ResourceLocation.CODEC.fieldOf("registryLocation").forGetter(k -> k.registryLocation)
    ).apply(instance, TagCraftedKey::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TagCraftedKey> STREAM_CODEC = StreamCodec.composite(
            StreamCodecUtils.tagKey(Registries.ITEM), k -> k.tagKey,
            ResourceLocation.STREAM_CODEC, k -> k.registryLocation,
            TagCraftedKey::new);

    private static final String PREFIX = "[#]";
    
    protected final TagKey<Item> tagKey;
    protected final ResourceLocation registryLocation;

    public TagCraftedKey(TagKey<Item> tagKey, ResourceLocation registryLocation) {
        this.tagKey = Preconditions.checkNotNull(tagKey);
        ResearchManagerVC.addCraftingReference(this.tagKey.hashCode());
        this.registryLocation = Preconditions.checkNotNull(registryLocation);
    }

    @Override
    public ResourceLocation getRegistryLocation() {
        return this.registryLocation;
    }

    @Override
    public String toString() {
        return PREFIX + this.tagKey.hashCode();
    }

    @Override
    public RequirementCategory getRequirementCategory() {
        return RequirementCategory.MUST_CRAFT;
    }

    @Override
    protected ResearchKeyType<TagCraftedKey> getType() {
        return ResearchKeyTypesVC.TAG_CRAFTED.get();
    }

    @Override
    public IconDefinition getIcon(RegistryAccess registryAccess) {
        return IconDefinition.of(this.tagKey);
    }

    @Override
    protected Optional<IPlayerKnowledge> getPlayerKnowledge(@Nullable Player player) {
        return ServicesVC.CAPABILITIES.knowledge(player, this.registryLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tagKey);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TagCraftedKey other = (TagCraftedKey) obj;
        return Objects.equals(this.tagKey, other.tagKey);
    }
}
