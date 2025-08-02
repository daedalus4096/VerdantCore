package com.verdantartifice.verdantcore.common.research.keys;

import com.google.common.base.Preconditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.research.ResearchManagerVC;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class StackCraftedKey extends AbstractResearchKey<StackCraftedKey> {
    public static final MapCodec<StackCraftedKey> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.CODEC.fieldOf("stack").forGetter(k -> k.stack),
            ResourceLocation.CODEC.fieldOf("registryLocation").forGetter(k -> k.registryLocation)
    ).apply(instance, StackCraftedKey::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, StackCraftedKey> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, k -> k.stack,
            ResourceLocation.STREAM_CODEC, k -> k.registryLocation,
            StackCraftedKey::new);

    private static final String PREFIX = "[#]";
    
    protected final ItemStack stack;
    protected final ResourceLocation registryLocation;
    
    public StackCraftedKey(ItemStack stack, ResourceLocation registryLocation) {
        if (stack == null || stack.isEmpty()) {
            throw new IllegalArgumentException("Item stack may not be null or empty");
        }
        this.stack = stack.copyWithCount(1);    // Preserve the stack NBT but not its count
        ResearchManagerVC.addCraftingReference(this.hashCode());
        this.registryLocation = Preconditions.checkNotNull(registryLocation);
    }
    
    public StackCraftedKey(ItemLike itemLike, ResourceLocation registryLocation) {
        this(new ItemStack(itemLike.asItem()), registryLocation);
    }

    @Override
    public ResourceLocation getRegistryLocation() {
        return this.registryLocation;
    }

    @Override
    public String toString() {
        return PREFIX + this.hashCode();
    }

    @Override
    public RequirementCategory getRequirementCategory() {
        return RequirementCategory.MUST_CRAFT;
    }

    @Override
    protected ResearchKeyType<StackCraftedKey> getType() {
        return ResearchKeyTypesVC.STACK_CRAFTED.get();
    }

    @Override
    public IconDefinition getIcon(RegistryAccess registryAccess) {
        return IconDefinition.of(this.stack.getItem());
    }

    @Override
    protected Optional<IPlayerKnowledge> getPlayerKnowledge(@Nullable Player player) {
        return ServicesVC.CAPABILITIES.knowledge(player, this.registryLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ServicesVC.ITEMS_REGISTRY.getKey(this.stack.getItem()), this.stack.getComponents());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StackCraftedKey other = (StackCraftedKey) obj;
        return ItemStack.isSameItemSameComponents(this.stack, other.stack);
    }
}
