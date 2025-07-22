package com.verdantartifice.verdantcore.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.research.ResearchEntry;
import com.verdantartifice.verdantcore.common.research.keys.AbstractResearchKey;
import com.verdantartifice.verdantcore.common.research.keys.ResearchEntryKey;
import com.verdantartifice.verdantcore.common.research.keys.StackCraftedKey;
import com.verdantartifice.verdantcore.common.research.keys.TagCraftedKey;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

/**
 * Advancement criterion that is triggered when the player completes a given research key.
 * 
 * @author Daedalus4096
 */
public class ResearchCompletedTrigger extends SimpleCriterionTrigger<ResearchCompletedTrigger.TriggerInstance> {
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.codec();
    }
    
    public void trigger(ServerPlayer player, AbstractResearchKey<?> completedKey) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(completedKey));
    }
    
    public record TriggerInstance(Optional<ContextAwarePredicate> player, AbstractResearchKey<?> researchKey) implements SimpleInstance {
        public static Codec<TriggerInstance> codec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                    AbstractResearchKey.dispatchCodec().fieldOf("researchKey").forGetter(TriggerInstance::researchKey)
                ).apply(instance, TriggerInstance::new));
        }
        
        public static Criterion<TriggerInstance> researchEntry(ResourceKey<Registry<ResearchEntry>> registryKey, ResourceKey<ResearchEntry> rawKey) {
            return CriteriaTriggersVC.RESEARCH_COMPLETED.get().createCriterion(new TriggerInstance(Optional.empty(), new ResearchEntryKey(registryKey, rawKey)));
        }
        
        public static Criterion<TriggerInstance> stackCrafted(ItemLike itemLike) {
            return CriteriaTriggersVC.RESEARCH_COMPLETED.get().createCriterion(new TriggerInstance(Optional.empty(), new StackCraftedKey(itemLike)));
        }
        
        public static Criterion<TriggerInstance> stackCrafted(ItemStack stack) {
            return CriteriaTriggersVC.RESEARCH_COMPLETED.get().createCriterion(new TriggerInstance(Optional.empty(), new StackCraftedKey(stack)));
        }
        
        public static Criterion<TriggerInstance> tagCrafted(TagKey<Item> tagKey) {
            return CriteriaTriggersVC.RESEARCH_COMPLETED.get().createCriterion(new TriggerInstance(Optional.empty(), new TagCraftedKey(tagKey)));
        }
        
        public boolean matches(AbstractResearchKey<?> completedKey) {
            return this.researchKey.equals(completedKey);
        }

        public Optional<ContextAwarePredicate> player() {
           return this.player;
        }
    }
}
