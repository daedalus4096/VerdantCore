package com.verdantartifice.verdantcore.common.stats;

import com.verdantartifice.verdantcore.common.crafting.IHasExpertise;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.research.ResearchDiscipline;
import com.verdantartifice.verdantcore.common.research.ResearchTier;
import com.verdantartifice.verdantcore.common.research.keys.ResearchDisciplineKey;
import com.verdantartifice.verdantcore.common.util.RegistryUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * Wrapper around {@link StatsManager} specifically for dealing with expertise stats.
 * 
 * @author Daedalus4096
 */
public class ExpertiseManager {
    private static final Set<ResourceKey<ResearchDiscipline>> EXPERTISELESS_REGISTRY = new HashSet<>();
    private static final Map<ResourceKey<ResearchDiscipline>, Function<ResearchTier, Integer>> EXPERTISE_OVERRIDE_REGISTRY = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static Optional<Stat> getStat(RegistryAccess registryAccess, ResearchDisciplineKey disciplineKey) {
        ResearchDiscipline discipline = RegistryUtils.getEntry(RegistryKeysVC.RESEARCH_DISCIPLINES, disciplineKey.getRootKey(), registryAccess);
        return discipline == null ? Optional.empty() : discipline.expertiseStat();
    }

    public static void registerExpertiselessDiscipline(ResearchDisciplineKey disciplineKey) {
        EXPERTISELESS_REGISTRY.add(disciplineKey.getRootKey());
    }

    public static void registerExpertiseOverrideDiscipline(ResearchDisciplineKey disciplineKey, Function<ResearchTier, Integer> overrideFunction) {
        EXPERTISE_OVERRIDE_REGISTRY.put(disciplineKey.getRootKey(), overrideFunction);
    }
    
    public static Optional<Integer> getThreshold(Level level, ResearchDisciplineKey disciplineKey, ResearchTier tier) {
        ResourceKey<ResearchDiscipline> rawKey = disciplineKey.getRootKey();
        if (EXPERTISELESS_REGISTRY.contains(rawKey)) {
            // These disciplines don't track expertise
            return Optional.empty();
        } else if (EXPERTISE_OVERRIDE_REGISTRY.containsKey(rawKey)) {
            // Some disciplines calculate expertise thresholds arbitrarily, out of necessity
            return Optional.of(EXPERTISE_OVERRIDE_REGISTRY.get(rawKey).apply(tier));
        } else {
            // All other disciplines calculate thresholds based solely on traditional and/or specialized crafting
            return Optional.of(getThresholdByDisciplineRecipes(level.registryAccess(), level.getRecipeManager(), disciplineKey, tier));
        }
    }
    
    protected static int getThresholdByDisciplineRecipes(RegistryAccess registryAccess, RecipeManager recipeManager, ResearchDisciplineKey discKey, ResearchTier tier) {
        Set<ResourceLocation> foundGroups = new HashSet<>();
        MutableInt retVal = new MutableInt(0);
        for (RecipeHolder<?> recipeHolder : recipeManager.getRecipes()) {
            if (recipeHolder.value() instanceof IHasExpertise expRecipe) {
                // Only consider recipes with a discipline that matches the given one
                expRecipe.getResearchDiscipline(registryAccess, recipeHolder.id()).filter(recipeDisc -> recipeDisc.equals(discKey)).ifPresent(recipeDisc -> {
                    // Only consider recipes with a research tier lower than the given one
                    expRecipe.getResearchTier(registryAccess).filter(recipeTier -> recipeTier.compareTo(tier) < 0).ifPresent(recipeTier -> {
                        expRecipe.getExpertiseGroup().ifPresentOrElse(groupId -> {
                            // If the recipe is part of an expertise group, only take its values into account if that group has not already been processed
                            if (!foundGroups.contains(groupId)) {
                                int reward = expRecipe.getExpertiseReward(registryAccess) + expRecipe.getBonusExpertiseReward(registryAccess);
                                retVal.add(reward);
                                foundGroups.add(groupId);
                            }
                        }, () -> {
                            // If the recipe is not part of an expertise group, then always contribute its values to the threshold
                            int reward = expRecipe.getExpertiseReward(registryAccess) + expRecipe.getBonusExpertiseReward(registryAccess);
                            retVal.add(reward);
                        });
                    });
                });
            }
        }
        LOGGER.debug("Final expertise threshold value for {} tier {}: {}", discKey.getRootKey().location(), tier.getSerializedName(), retVal.intValue());
        return retVal.intValue();
    }
    
    public static Optional<Integer> getValue(@Nullable Player player, ResearchDisciplineKey disciplineKey) {
        return player == null ? Optional.empty() : getStat(player.level().registryAccess(), disciplineKey).map(stat -> StatsManager.getValue(player, stat));
    }
    
    public static Optional<Integer> getValue(@Nullable Player player, @Nonnull ResourceKey<ResearchDiscipline> rawKey) {
        return getValue(player, new ResearchDisciplineKey(rawKey));
    }
    
    public static void incrementValue(@Nullable Player player, ResearchDisciplineKey disciplineKey) {
        if (player != null) {
            getStat(player.level().registryAccess(), disciplineKey).ifPresent(stat -> StatsManager.incrementValue(player, stat));
        }
    }
    
    public static void incrementValue(@Nullable Player player, ResearchDisciplineKey disciplineKey, int delta) {
        if (player != null) {
            getStat(player.level().registryAccess(), disciplineKey).ifPresent(stat -> StatsManager.incrementValue(player, stat, delta));
        }
    }
    
    public static void setValue(@Nullable Player player, ResearchDisciplineKey disciplineKey, int value) {
        if (player != null) {
            getStat(player.level().registryAccess(), disciplineKey).ifPresent(stat -> StatsManager.setValue(player, stat, value));
        }
    }
    
    public static void setValueIfMax(@Nullable Player player, ResearchDisciplineKey disciplineKey, int newVal) {
        if (player != null) {
            getStat(player.level().registryAccess(), disciplineKey).ifPresent(stat -> StatsManager.setValueIfMax(player, stat, newVal));
        }
    }
    
    /**
     * Award expertise to the given player for crafting the given expertise-granting recipe at an arcane workbench or
     * other crafting station.
     * 
     * @param player the player to receive the expertise
     * @param recipeHolder a holder for the expertise-granting recipe
     */
    public static void awardExpertise(@Nullable Player player, @Nullable RecipeHolder<?> recipeHolder) {
        if (player != null && recipeHolder != null && recipeHolder.value() instanceof IHasExpertise expRecipe) {
            expRecipe.getResearchDiscipline(player.level().registryAccess(), recipeHolder.id()).ifPresent(discKey -> {
                // Award base expertise for this recipe to the player's discipline score
                incrementValue(player, discKey, expRecipe.getExpertiseReward(player.level().registryAccess()));
                
                // Award bonus expertise for this recipe to the player's discipline score if eligible, then mark it as having been crafted
                if (isBonusEligible(player, recipeHolder)) {
                    incrementValue(player, discKey, expRecipe.getBonusExpertiseReward(player.level().registryAccess()));
                    markCrafted(player, recipeHolder);
                }
            });
        }
    }
    
    public static boolean isBonusEligible(Player player, RecipeHolder<?> recipeHolder) {
        if (player != null && recipeHolder != null && recipeHolder.value() instanceof IHasExpertise expRecipe) {
            return ServicesVC.CAPABILITIES.stats(player).map(stats ->
                    !stats.isRecipeCrafted(recipeHolder.id()) &&
                    (expRecipe.getExpertiseGroup().isEmpty() || !stats.isRecipeGroupCrafted(expRecipe.getExpertiseGroup().get()))
            ).orElse(false);
        }
        return false;
    }
    
    protected static void markCrafted(Player player, RecipeHolder<?> recipeHolder) {
        if (player != null && recipeHolder != null && recipeHolder.value() instanceof IHasExpertise expRecipe) {
            ServicesVC.CAPABILITIES.stats(player).ifPresent(stats -> {
                stats.setRecipeCrafted(recipeHolder.id());
                expRecipe.getExpertiseGroup().ifPresent(stats::setRecipeGroupCrafted);
            });
        }
    }
}
