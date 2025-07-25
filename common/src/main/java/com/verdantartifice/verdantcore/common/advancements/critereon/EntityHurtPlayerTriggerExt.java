package com.verdantartifice.verdantcore.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DamagePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

/**
 * Advancement criterion that is trigger when the player is hurt by an entity.  Like the vanilla
 * {@link net.minecraft.advancements.critereon.EntityHurtPlayerTrigger}, except that it takes into 
 * account the player predicate as well, instead of just the damage source.
 * 
 * @author Daedalus4096
 */
public class EntityHurtPlayerTriggerExt extends SimpleCriterionTrigger<EntityHurtPlayerTriggerExt.TriggerInstance> {
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }
    
    public void trigger(ServerPlayer pPlayer, DamageSource pSource, float pAmountDealt) {
        // The LivingHurtEvent only provides the damage source and amount of damage dealt, not the damage taken or blocked values, so we
        // have to make some assumptions here.
        LootContext playerContext = EntityPredicate.createContext(pPlayer, pPlayer);
        this.trigger(pPlayer, triggerInstance -> triggerInstance.matches(pPlayer, playerContext, pSource, pAmountDealt, pAmountDealt, false));
    }
    
    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<DamagePredicate> damage) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                DamagePredicate.CODEC.optionalFieldOf("damage").forGetter(TriggerInstance::damage)
            ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> playerHurtEntity(Optional<EntityPredicate> pPlayer, Optional<DamagePredicate> pDamage) {
            return CriteriaTriggersVC.ENTITY_HURT_PLAYER_EXT.get().createCriterion(new TriggerInstance(EntityPredicate.wrap(pPlayer), pDamage));
        }

        public static Criterion<TriggerInstance> playerHurtEntity(Optional<EntityPredicate> pPlayer, DamagePredicate.Builder pDamage) {
            return CriteriaTriggersVC.ENTITY_HURT_PLAYER_EXT.get().createCriterion(new TriggerInstance(EntityPredicate.wrap(pPlayer), Optional.of(pDamage.build())));
        }

        public boolean matches(ServerPlayer pPlayer, LootContext pPlayerContext, DamageSource pDamage, float pDealt, float pTaken, boolean pBlocked) {
            if (this.damage.isPresent() && !this.damage.get().matches(pPlayer, pDamage, pDealt, pTaken, pBlocked)) {
                return false;
            } else {
                return !this.player.isPresent() || this.player.get().matches(pPlayerContext);
            }
        }

        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }
    }
}
