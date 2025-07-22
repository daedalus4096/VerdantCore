package com.verdantartifice.verdantcore.common.stats;

import com.verdantartifice.verdantcore.common.advancements.critereon.CriteriaTriggersVC;
import com.verdantartifice.verdantcore.common.crafting.IHasExpertise;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.research.ResearchDiscipline;
import com.verdantartifice.verdantcore.common.util.RegistryUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Primary access point for statistics-related methods.  Also stores the sorted list of stat definitions
 * in a static registry.
 * 
 * @author Daedalus4096
 */
public class StatsManager {
    protected static final Map<ResourceLocation, Stat> REGISTRY = new HashMap<>();
    protected static final List<Stat> DISPLAY_STATS = new ArrayList<>();
    
    // Set of unique IDs of players that need their research synced to their client
    private static final Set<UUID> SYNC_SET = ConcurrentHashMap.newKeySet();
    
    public static boolean isSyncScheduled(@Nullable Player player) {
        if (player == null) {
            return false;
        } else {
            return SYNC_SET.remove(player.getUUID());
        }
    }
    
    public static void scheduleSync(@Nullable Player player) {
        if (player != null) {
            SYNC_SET.add(player.getUUID());
        }
    }
    
    public static Set<ResourceLocation> getStatLocations() {
        return Collections.unmodifiableSet(REGISTRY.keySet());
    }
    
    public static List<Stat> getDisplayStats() {
        return Collections.unmodifiableList(DISPLAY_STATS);
    }
    
    public static boolean registerStat(@Nullable Stat stat) {
        if (stat != null) {
            REGISTRY.put(stat.key(), stat);
            if (stat.internal()) {
                return true;    // Don't register internal stats in the display list
            } else {
                return DISPLAY_STATS.add(stat);
            }
        } else {
            return false;
        }
    }
    
    public static Stat getStat(@Nullable ResourceLocation loc) {
        return REGISTRY.get(loc);
    }
    
    public static int getValue(@Nullable Player player, @Nullable Stat stat) {
        // Get the value from the player capability
        return ServicesVC.CAPABILITIES.stats(player).map(stats -> stats.getValue(stat)).orElse(0);
    }
    
    @Nonnull
    public static Component getFormattedValue(@Nullable Player player, @Nullable Stat stat) {
        return Component.literal(stat.formatter().format(getValue(player, stat)));
    }
    
    public static void incrementValue(@Nullable Player player, @Nullable Stat stat) {
        incrementValue(player, stat, 1);
    }
    
    public static void incrementValue(@Nullable Player player, @Nullable Stat stat, int delta) {
        if (delta != 0) {
            setValue(player, stat, delta + getValue(player, stat));
        }
    }
    
    public static void setValue(@Nullable Player player, @Nullable Stat stat, int value) {
        if (player instanceof ServerPlayer spe) {
            ServicesVC.CAPABILITIES.stats(spe).ifPresent(stats -> {
                // Set the new value into the player capability
                stats.setValue(stat, value);
                scheduleSync(spe);
                CriteriaTriggersVC.STAT_VALUE.get().trigger(spe, stat, value);
            });
        }
    }
    
    public static void setValueIfMax(@Nullable Player player, @Nullable Stat stat, int newVal) {
        if (newVal > getValue(player, stat)) {
            setValue(player, stat, newVal);
        }
    }
    
    public static void incrementCraftCount(@Nullable Player player, @Nullable RecipeHolder<?> recipeHolder, int amount) {
        if (recipeHolder != null && recipeHolder.value() instanceof IHasExpertise expRecipe) {
            expRecipe.getResearchDiscipline(player.level().registryAccess(), recipeHolder.id()).ifPresent(discKey -> {
                ResearchDiscipline disc = RegistryUtils.getEntry(discKey.getRegistryKey(), discKey.getRootKey(), player.level().registryAccess());
                if (disc != null) {
                    disc.craftingStat().ifPresent(stat -> StatsManager.incrementValue(player, stat, amount));
                }
            });
        }
    }
}
