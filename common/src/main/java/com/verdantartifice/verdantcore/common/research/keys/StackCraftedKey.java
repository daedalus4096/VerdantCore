package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.serialization.MapCodec;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.research.ResearchManagerVC;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class StackCraftedKey extends AbstractResearchKey<StackCraftedKey> {
    public static final MapCodec<StackCraftedKey> CODEC = ItemStack.CODEC.fieldOf("stack").xmap(StackCraftedKey::new, key -> key.stack);
    public static final StreamCodec<RegistryFriendlyByteBuf, StackCraftedKey> STREAM_CODEC = ItemStack.STREAM_CODEC.map(StackCraftedKey::new, key -> key.stack);
    
    private static final String PREFIX = "[#]";
    
    protected final ItemStack stack;
    
    public StackCraftedKey(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            throw new IllegalArgumentException("Item stack may not be null or empty");
        }
        this.stack = stack.copyWithCount(1);    // Preserve the stack NBT but not its count
        ResearchManagerVC.addCraftingReference(this.hashCode());
    }
    
    public StackCraftedKey(ItemLike itemLike) {
        this(new ItemStack(itemLike.asItem()));
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
