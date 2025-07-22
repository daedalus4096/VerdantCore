package com.verdantartifice.verdantcore.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.books.BookLanguage;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * Advancement criterion that is triggered when the player reaches at least the given comprehension value.
 * 
 * @author Daedalus4096
 */
public class LinguisticsComprehensionTrigger extends SimpleCriterionTrigger<LinguisticsComprehensionTrigger.TriggerInstance> {
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.codec();
    }
    
    public void trigger(ServerPlayer player, ResourceKey<BookLanguage> language, int value) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(language, value));
    }
    
    public record TriggerInstance(Optional<ContextAwarePredicate> player, ResourceKey<BookLanguage> language, int threshold) implements SimpleInstance {
        public static Codec<TriggerInstance> codec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                    ResourceKey.codec(RegistryKeysVC.BOOK_LANGUAGES).fieldOf("language").forGetter(TriggerInstance::language),
                    Codec.INT.fieldOf("threshold").forGetter(TriggerInstance::threshold)
                ).apply(instance, TriggerInstance::new));
        }
        
        public static Criterion<TriggerInstance> atLeast(ResourceKey<BookLanguage> language, int threshold) {
            return CriteriaTriggersVC.LINGUISTICS_COMPREHENSION.get().createCriterion(new TriggerInstance(Optional.empty(), language, threshold));
        }
        
        public boolean matches(ResourceKey<BookLanguage> language, int value) {
            return this.language.equals(language) && value >= this.threshold;
        }

        public Optional<ContextAwarePredicate> player() {
           return this.player;
        }
    }
}
