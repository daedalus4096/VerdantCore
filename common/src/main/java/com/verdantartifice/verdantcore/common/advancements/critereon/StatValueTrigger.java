package com.verdantartifice.verdantcore.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.stats.Stat;
import com.verdantartifice.verdantcore.common.stats.StatsManager;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * Advancement criterion that is triggered when the player reaches at least the given stat value.
 * 
 * @author Daedalus4096
 */
public class StatValueTrigger extends SimpleCriterionTrigger<StatValueTrigger.TriggerInstance> {
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.codec();
    }
    
    public void trigger(ServerPlayer player, Stat stat, int value) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(stat, value));
    }
    
    public record TriggerInstance(Optional<ContextAwarePredicate> player, Stat stat, int threshold) implements SimpleInstance {
        public static Codec<TriggerInstance> codec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                    ResourceLocation.CODEC.fieldOf("stat").xmap(loc -> StatsManager.getStat(loc), stat -> stat.key()).forGetter(TriggerInstance::stat),
                    Codec.INT.fieldOf("threshold").forGetter(TriggerInstance::threshold)
                ).apply(instance, TriggerInstance::new));
        }
        
        public static Criterion<TriggerInstance> atLeast(Stat stat, int threshold) {
            return CriteriaTriggersVC.STAT_VALUE.get().createCriterion(new TriggerInstance(Optional.empty(), stat, threshold));
        }
        
        public boolean matches(Stat stat, int value) {
            return this.stat.equals(stat) && value >= this.threshold;
        }

        public Optional<ContextAwarePredicate> player() {
           return this.player;
        }
    }
}
