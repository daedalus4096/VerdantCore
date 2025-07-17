package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.serialization.Codec;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementCategory;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class representing an atom in the research hierarchy.
 * 
 * @author Daedalus4096
 */
public abstract class AbstractResearchKey<T extends AbstractResearchKey<T>> {
    public static Codec<AbstractResearchKey<?>> dispatchCodec() {
        return ServicesVC.RESEARCH_KEY_TYPES_REGISTRY.codec().dispatch("key_type", AbstractResearchKey::getType, ResearchKeyType::codec);
    }
    
    public static StreamCodec<RegistryFriendlyByteBuf, AbstractResearchKey<?>> dispatchStreamCodec() {
        return ServicesVC.RESEARCH_KEY_TYPES_REGISTRY.registryFriendlyStreamCodec().dispatch(AbstractResearchKey::getType, ResearchKeyType::streamCodec);
    }
    
    @Override
    public abstract String toString();
    
    @Override
    public abstract int hashCode();
    
    @Override
    public abstract boolean equals(Object obj);
    
    /**
     * Returns the category of requirement to be used when this key is part of a {@link com.verdantartifice.verdantcore.common.research.requirements.ResearchRequirement}.
     * 
     * @return this key's corresponding requirement category
     */
    public abstract RequirementCategory getRequirementCategory();

    protected abstract ResearchKeyType<T> getType();
    
    public abstract IconDefinition getIcon(RegistryAccess registryAccess);
    
    public boolean isKnownBy(@Nullable Player player, @NotNull IPlayerKnowledge knowledgeCapability) {
        if (player == null) {
            return false;
        }
        return knowledgeCapability.isResearchComplete(player.level().registryAccess(), this);
    }
    
    public static AbstractResearchKey<?> fromNetwork(RegistryFriendlyByteBuf buf) {
        return AbstractResearchKey.dispatchStreamCodec().decode(buf);
    }
    
    public void toNetwork(RegistryFriendlyByteBuf buf) {
        AbstractResearchKey.dispatchStreamCodec().encode(buf, this);
    }
}
