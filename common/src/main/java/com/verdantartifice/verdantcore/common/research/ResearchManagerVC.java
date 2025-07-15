package com.verdantartifice.verdantcore.common.research;

import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.research.keys.AbstractResearchKey;
import com.verdantartifice.verdantcore.common.research.keys.ResearchEntryKey;
import com.verdantartifice.verdantcore.common.stats.StatsManager;
import com.verdantartifice.verdantcore.common.util.RegistryUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Primary access point for research-related methods.
 * 
 * @author Daedalus4096
 */
public class ResearchManagerVC {
    // Hash codes of items that must be crafted to complete one or more research stages
    private static final Set<Integer> CRAFTING_REFERENCES = new HashSet<>();
    
    // Set of unique IDs of players that need their research synced to their client
    private static final Set<UUID> SYNC_SET = ConcurrentHashMap.newKeySet();
    
    public static Set<Integer> getAllCraftingReferences() {
        return Collections.unmodifiableSet(CRAFTING_REFERENCES);
    }
    
    public static boolean addCraftingReference(int reference) {
        return CRAFTING_REFERENCES.add(Integer.valueOf(reference));
    }
    
    static void clearCraftingReferences() {
        CRAFTING_REFERENCES.clear();
    }
    
    @Nonnull
    public static Optional<ResearchEntry> getEntryForRecipe(RegistryAccess registryAccess, ResourceLocation recipeId, ResourceKey<Registry<ResearchEntry>> registryKey) {
        return RegistryUtils.stream(registryKey, registryAccess)
                .filter(entry -> entry.getAllRecipeIds().contains(recipeId))
                .findFirst();
    }
    
    public static boolean isRecipeVisible(ResourceLocation recipeId, Player player, ResourceKey<Registry<ResearchEntry>> registryKey) {
        IPlayerKnowledge know = ServicesVC.CAPABILITIES.knowledge(player).orElseThrow(() -> new IllegalStateException("No knowledge provider for player"));
        ResearchEntry entry = ResearchManagerVC.getEntryForRecipe(player.level().registryAccess(), recipeId, registryKey).orElse(null);
        if (entry == null) {
            // If the recipe has no controlling research, then assume it's not visible
            return false;
        }

        // First check to see if the current stage for the entry has the recipe listed
        int currentStageIndex = know.getResearchStage(entry.key());
        if (currentStageIndex == entry.stages().size()) {
            ResearchStage currentStage = entry.stages().get(currentStageIndex - 1);
            if (currentStage.recipes().contains(recipeId)) {
                return true;
            }
        } else if (currentStageIndex >= 0 && currentStageIndex < entry.stages().size()) {
            ResearchStage currentStage = entry.stages().get(currentStageIndex);
            if (currentStage.recipes().contains(recipeId)) {
                return true;
            }
        }
        
        // If that doesn't pan out, check to see if any unlocked addendum lists the recipe
        for (ResearchAddendum addendum : entry.addenda()) {
            if (addendum.completionRequirementOpt().isPresent() && addendum.recipes().contains(recipeId) && addendum.completionRequirementOpt().get().isMetBy(player)) {
                return true;
            }
        }
        
        // Otherwise, return false
        return false;
    }
    
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
    
    public static boolean hasPrerequisites(@Nullable Player player, @Nullable AbstractResearchKey<?> key) {
        if (player == null) {
            return false;
        }
        if (key == null) {
            return true;
        }
        if (key instanceof ResearchEntryKey entryKey) {
            Optional<Holder.Reference<ResearchEntry>> entryRefOpt = player.level().registryAccess().registryOrThrow(entryKey.getRegistryKey()).getHolder(entryKey.getRootKey());
            if (entryRefOpt.isEmpty() || entryRefOpt.get().value().parents().isEmpty()) {
                return true;
            } else {
                // Perform a strict completion check on the given entry's parent research
                return entryRefOpt.get().value().parents().stream().allMatch(k -> k.isKnownBy(player));
            }
        } else {
            return true;
        }
    }
    
    public static boolean isResearchStarted(@Nullable Player player, @Nonnull ResourceKey<Registry<ResearchEntry>> registryKey, @Nonnull ResourceKey<ResearchEntry> rawKey) {
        return isResearchStarted(player, new ResearchEntryKey(registryKey, rawKey));
    }
    
    public static boolean isResearchStarted(@Nullable Player player, @Nullable AbstractResearchKey<?> key) {
        if (player == null || key == null) {
            return false;
        }
        return ServicesVC.CAPABILITIES.knowledge(player).map(k -> k.isResearchKnown(key)).orElse(false);
    }
    
    public static boolean isResearchComplete(@Nullable Player player, @Nonnull ResourceKey<Registry<ResearchEntry>> registryKey, @Nonnull ResourceKey<ResearchEntry> rawKey) {
        return isResearchComplete(player, new ResearchEntryKey(registryKey, rawKey));
    }
    
    public static boolean isResearchComplete(@Nullable Player player, @Nullable AbstractResearchKey<?> key) {
        if (player == null || key == null) {
            return false;
        }
        RegistryAccess registryAccess = player.level().registryAccess();
        return ServicesVC.CAPABILITIES.knowledge(player).map(k -> k.isResearchComplete(registryAccess, key)).orElse(false);
    }
    
    public static boolean completeResearch(@Nullable Player player, @Nonnull ResourceKey<Registry<ResearchEntry>> registryKey, @Nonnull ResourceKey<ResearchEntry> rawKey) {
        return completeResearch(player, new ResearchEntryKey(registryKey, rawKey));
    }
    
    public static boolean completeResearch(@Nullable Player player, @Nullable AbstractResearchKey<?> key) {
        // Complete the given research and sync it to the player's client
        return completeResearch(player, key, true);
    }
    
    public static boolean completeResearch(@Nullable Player player, @Nullable AbstractResearchKey<?> key, boolean sync) {
        // Complete the given research, optionally syncing it to the player's client
        return completeResearch(player, key, sync, true, true);
    }
    
    public static boolean completeResearch(@Nullable Player player, @Nullable AbstractResearchKey<?> key, boolean sync, boolean showNewFlags, boolean showPopups) {
        // Repeatedly progress the given research until it is completed, optionally syncing it to the player's client
        boolean retVal = false;
        while (progressResearch(player, key, sync, showNewFlags, showPopups)) {
            retVal = true;
        }
        return retVal;
    }
    
    public static void forceGrantWithAllParents(@Nullable Player player, @Nonnull ResourceKey<Registry<ResearchEntry>> registryKey, @Nonnull ResourceKey<ResearchEntry> rawKey) {
        forceGrantWithAllParents(player, new ResearchEntryKey(registryKey, rawKey));
    }
    
    public static void forceGrantWithAllParents(@Nullable Player player, @Nullable ResearchEntryKey key) {
        if (player != null && key != null) {
            RegistryAccess registryAccess = player.level().registryAccess();
            ServicesVC.CAPABILITIES.knowledge(player).ifPresent(knowledge -> {
                if (!knowledge.isResearchComplete(registryAccess, key)) {
                    ResearchEntry entry = RegistryUtils.getEntry(key.getRegistryKey(), key.getRootKey(), registryAccess);
                    if (entry != null) {
                        // Recursively force-grant all of this entry's parent entries, even if not all of them are required
                        entry.parents().forEach(parentKey -> forceGrantWithAllParents(player, parentKey));

                        for (ResearchStage stage : entry.stages()) {
                            // Force complete any requirements for any of the entry's stages
                            stage.completionRequirementOpt().ifPresent(req -> req.forceComplete(player));
                        }
                    }
                    
                    // Once all prerequisites are out of the way, complete this entry itself
                    completeResearch(player, key, true, true, false);
                    
                    // Mark as updated any research entry that has a stage which requires completion of this entry
                    registryAccess.registryOrThrow(key.getRegistryKey()).forEach(searchEntry -> {
                        for (ResearchStage searchStage : searchEntry.stages()) {
                            if (searchStage.completionRequirementOpt().isPresent() && searchStage.completionRequirementOpt().get().contains(key)) {
                                knowledge.addResearchFlag(searchEntry.key(), IPlayerKnowledge.ResearchFlag.UPDATED);
                                knowledge.removeResearchFlag(searchEntry.key(), IPlayerKnowledge.ResearchFlag.READ);
                                break;
                            }
                        }
                    });
                }
            });
        }
    }
    
    public static void forceGrantParentsOnly(@Nullable Player player, @Nullable ResearchEntryKey key) {
        if (player != null && key != null) {
            RegistryAccess registryAccess = player.level().registryAccess();
            ServicesVC.CAPABILITIES.knowledge(player).ifPresent(knowledge -> {
                if (!knowledge.isResearchComplete(registryAccess, key)) {
                    ResearchEntry entry = RegistryUtils.getEntry(key.getRegistryKey(), key.getRootKey(), registryAccess);
                    if (entry != null) {
                        // Recursively force-grant all of this entry's parent entries, even if not all of them are required
                        entry.parents().forEach(parentKey -> forceGrantWithAllParents(player, parentKey));

                        for (ResearchStage stage : entry.stages()) {
                            // Force complete any requirements for any of the entry's stages
                            stage.completionRequirementOpt().ifPresent(req -> req.forceComplete(player));
                        }
                    }
                }
            });
        }
    }
    
    public static void forceGrantAll(@Nullable Player player, @Nonnull ResourceKey<Registry<ResearchEntry>> registryKey) {
        if (player != null) {
            player.level().registryAccess().registryOrThrow(registryKey).forEach(entry -> forceGrantWithAllParents(player, entry.key()));
        }
    }
    
    public static void forceRevokeWithAllChildren(@Nullable Player player, @Nonnull ResourceKey<Registry<ResearchEntry>> registryKey, @Nonnull ResourceKey<ResearchEntry> rawKey) {
        forceRevokeWithAllChildren(player, new ResearchEntryKey(registryKey, rawKey));
    }
    
    public static void forceRevokeWithAllChildren(@Nullable Player player, @Nullable ResearchEntryKey key) {
        if (player != null && key != null) {
            RegistryAccess registryAccess = player.level().registryAccess();
            ServicesVC.CAPABILITIES.knowledge(player).ifPresent(knowledge -> {
                if (knowledge.isResearchComplete(registryAccess, key)) {
                    // Revoke all child research of this entry
                    registryAccess.registryOrThrow(key.getRegistryKey()).forEach(entry -> {
                        if (entry.parents().contains(key)) {
                            forceRevokeWithAllChildren(player, entry.key());
                        }
                    });
                    
                    // Once all children are revoked, revoke this entry itself
                    revokeResearch(player, key);
                }
            });
        }
    }
    
    public static boolean revokeResearch(@Nullable Player player, @Nullable ResearchEntryKey key) {
        // Revoke the given research and sync it to the player's client
        return revokeResearch(player, key, true);
    }
    
    public static boolean revokeResearch(@Nullable Player player, @Nullable ResearchEntryKey key, boolean sync) {
        // Remove the given research from the player's known list and optionally sync to the player's client
        if (player == null || key == null) {
            return false;
        }
        
        IPlayerKnowledge knowledge = ServicesVC.CAPABILITIES.knowledge(player).orElse(null);
        if (knowledge == null) {
            return false;
        }
        
        // Remove all recipes that are unlocked by the given research from the player's arcane recipe book
        if (player instanceof ServerPlayer serverPlayer) {
            ResearchEntry entry = RegistryUtils.getEntry(key.getRegistryKey(), key.getRootKey(), player.level().registryAccess());
            if (entry != null) {
                RecipeManager recipeManager = serverPlayer.level().getRecipeManager();
                Set<RecipeHolder<?>> recipesToRemove = entry.getAllRecipeIds().stream().map(r -> recipeManager.byKey(r).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
                ArcaneRecipeBookManager.removeRecipes(recipesToRemove, serverPlayer);
                serverPlayer.resetRecipes(recipesToRemove);
            }
        }

        knowledge.removeResearch(key);
        if (sync) {
            scheduleSync(player);
        }
        return true;
    }
    
    public static boolean progressResearch(@Nullable Player player, @Nonnull ResourceKey<Registry<ResearchEntry>> registryKey, @Nonnull ResourceKey<ResearchEntry> rawKey) {
        return progressResearch(player, new ResearchEntryKey(registryKey, rawKey));
    }
    
    public static boolean progressResearch(@Nullable Player player, @Nullable ResearchEntryKey key) {
        // Progress the given research to its next stage and sync to the player's client
        return progressResearch(player, key, true);
    }
    
    public static boolean progressResearch(@Nullable Player player, @Nullable ResearchEntryKey key, boolean sync) {
        // Progress the given research to its next stage and sync to the player's client
        return progressResearch(player, key, sync, true, true);
    }
    
    public static boolean progressResearch(@Nullable Player player, @Nullable AbstractResearchKey<?> key, boolean sync, boolean showNewFlags, boolean showPopups) {
        // Progress the given research to its next stage and optionally sync to the player's client
        if (player == null || key == null) {
            return false;
        }
        RegistryAccess registryAccess = player.level().registryAccess();

        IPlayerKnowledge knowledge = ServicesVC.CAPABILITIES.knowledge(player).orElse(null);
        if (knowledge == null) {
            return false;
        }
        if (knowledge.isResearchComplete(registryAccess, key)) {
            // If the research is already complete, trigger advancement criteria if on server side, then abort
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggersPM.RESEARCH_COMPLETED.get().trigger(serverPlayer, key);
            }
            return false;
        } else if (!hasPrerequisites(player, key)) {
            // If the player doesn't have the prerequisites, just abort
            return false;
        }
        
        // If the research is not started yet, start it
        boolean added = false;
        if (!knowledge.isResearchKnown(key)) {
            knowledge.addResearch(key);
            added = true;
        }
        
        ResearchEntry entry = key instanceof ResearchEntryKey entryKey ?
                RegistryUtils.getEntry(entryKey.getRegistryKey(), entryKey.getRootKey(), registryAccess) :
                null;
        boolean entryComplete = true;   // Default to true for non-entry research (e.g. scan triggers)
        if (entry != null && !entry.stages().isEmpty()) {
            // Get the current stage number of the research entry
            ResearchStage currentStage = null;
            int currentStageNum = knowledge.getResearchStage(key);
            
            // Increment the current stage, unless the research was just added then skip this step (because that already 
            // incremented the stage from -1 to 0)
            if (!added) {
                currentStageNum++;
            }
            if (currentStageNum == (entry.stages().size() - 1) && !entry.stages().get(currentStageNum).hasPrerequisites()) {
                // If we've advanced to the final stage of the entry and it has no further prereqs (which it shouldn't), then
                // advance one more to be considered complete
                currentStageNum++;
            }
            currentStageNum = Math.min(currentStageNum, entry.stages().size());
            if (currentStageNum >= 0) {
                currentStage = entry.stages().get(Math.min(currentStageNum, entry.stages().size() - 1));
            }
            knowledge.setResearchStage(key, currentStageNum);
            
            // Determine whether the entry has been completed
            entryComplete = (currentStageNum >= entry.stages().size());
            
            if (currentStage != null) {
                if (player instanceof ServerPlayer serverPlayer) {
                    // Process any rewards from the newly-reached stage
                    currentStage.rewards().forEach(r -> r.grant(serverPlayer));

                    // Add any unlocked recipes from the current stage to the player's arcane recipe book
                    RecipeManager recipeManager = serverPlayer.level().getRecipeManager();
                    Set<RecipeHolder<?>> recipesToUnlock = currentStage.recipes().stream().map(r -> recipeManager.byKey(r).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
                    ArcaneRecipeBookManager.addRecipes(recipesToUnlock, serverPlayer);
                    serverPlayer.awardRecipes(recipesToUnlock);
                }
                
                // Grant any sibling research from the current stage
                for (AbstractResearchKey<?> sibling : currentStage.siblings()) {
                    completeResearch(player, sibling, sync);
                }
                
                // Open any research to be revealed by the current stage
                for (ResearchEntryKey revelation : currentStage.revelations()) {
                    if (!knowledge.isResearchKnown(revelation)) {
                        knowledge.addResearch(revelation);
                        if (showPopups) {
                            knowledge.addResearchFlag(revelation, IPlayerKnowledge.ResearchFlag.POPUP);
                        }
                        knowledge.addResearchFlag(revelation, IPlayerKnowledge.ResearchFlag.NEW);
                        knowledge.removeResearchFlag(revelation, IPlayerKnowledge.ResearchFlag.READ);
                    }
                }

                // Flag any research entries to be highlighted as such
                for (ResearchEntryKey highlight : currentStage.highlights()) {
                    knowledge.addResearchFlag(highlight, IPlayerKnowledge.ResearchFlag.HIGHLIGHT);
                }
            }
            
            if (entryComplete && !entry.addenda().isEmpty() && player instanceof ServerPlayer serverPlayer) {
                RecipeManager recipeManager = serverPlayer.level().getRecipeManager();
                for (ResearchAddendum addendum : entry.addenda()) {
                    if (addendum.completionRequirementOpt().isEmpty() || addendum.completionRequirementOpt().get().isMetBy(player)) {
                        // Add any unlocked recipes from this entry's addenda to the player's arcane recipe book
                        Set<RecipeHolder<?>> recipesToUnlock = addendum.recipes().stream().map(r -> recipeManager.byKey(r).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
                        ArcaneRecipeBookManager.addRecipes(recipesToUnlock, serverPlayer);
                        serverPlayer.awardRecipes(recipesToUnlock);
                        
                        // Grant any sibling research from this entry's addenda
                        for (AbstractResearchKey<?> sibling : addendum.siblings()) {
                            completeResearch(player, sibling, sync);
                        }
                    }
                }
            }

            // Give the player experience for advancing their research
            if (!added) {
                player.giveExperiencePoints(5);
            }
        }
        
        if (entryComplete) {
            if (sync) {
                // If the entry has been completed and we're syncing, add the appropriate flags
                if (showPopups) {
                    knowledge.addResearchFlag(key, IPlayerKnowledge.ResearchFlag.POPUP);
                }
                if (showNewFlags) {
                    knowledge.addResearchFlag(key, IPlayerKnowledge.ResearchFlag.NEW);
                    knowledge.removeResearchFlag(key, IPlayerKnowledge.ResearchFlag.READ);
                }
            }
            
            // Reveal any addenda that depended on this research
            if (entry != null) {
                registryAccess.registryOrThrow(entry.key().getRegistryKey()).forEach(searchEntry -> {
                    if (!searchEntry.addenda().isEmpty() && knowledge.isResearchComplete(registryAccess, searchEntry.key())) {
                        for (ResearchAddendum addendum : searchEntry.addenda()) {
                            addendum.completionRequirementOpt().filter(req -> req.contains(key) && req.isMetBy(player)).ifPresent(req -> {
                                // Announce completion of the addendum
                                Component nameComp = Component.translatable(searchEntry.getNameTranslationKey());
                                player.sendSystemMessage(Component.translatable("event.verdantcore.add_addendum", nameComp));
                                knowledge.addResearchFlag(searchEntry.key(), IPlayerKnowledge.ResearchFlag.UPDATED);
                                knowledge.removeResearchFlag(searchEntry.key(), IPlayerKnowledge.ResearchFlag.READ);

                                if (player instanceof ServerPlayer serverPlayer) {
                                    // Process addendum rewards
                                    addendum.rewards().forEach(r -> r.grant(serverPlayer));

                                    // Add any unlocked recipes to the player's arcane recipe book
                                    RecipeManager recipeManager = serverPlayer.level().getRecipeManager();
                                    Set<RecipeHolder<?>> recipesToUnlock = addendum.recipes().stream().map(r -> recipeManager.byKey(r).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
                                    ArcaneRecipeBookManager.addRecipes(recipesToUnlock, serverPlayer);
                                    serverPlayer.awardRecipes(recipesToUnlock);
                                }

                                // Grant any unlocked sibling research
                                for (AbstractResearchKey<?> sibling : addendum.siblings()) {
                                    completeResearch(player, sibling, sync);
                                }
                            });
                        }
                    }
                });
            }

            // If completing this entry unlocked a new discipline, show a toast to the player
            if (entry != null && player instanceof ServerPlayer serverPlayer) {
                entry.disciplineKeyOpt().ifPresent(disciplineKey -> {
                    RegistryUtils.stream(disciplineKey.getRegistryKey(), registryAccess)
                            .filter(d -> d.indexSortOrder().isPresent())
                            .sorted(Comparator.comparingInt(a -> a.indexSortOrder().get()))
                            .filter(d -> d.unlockRequirementOpt().map(req -> req.satisfiedBy(entry.key())).orElse(false))
                            .forEach(d -> PacketHandler.sendToPlayer(new UnlockDisciplinePacket(d), serverPlayer));
                });
            }
            
            // If completing this entry finished its discipline, reveal any appropriate finale research
            if (entry != null) {
                entry.disciplineKeyOpt().ifPresent(disciplineKey -> {
                    ResearchDiscipline discipline = registryAccess.registryOrThrow(disciplineKey.getRegistryKey()).get(disciplineKey.getRootKey());
                    if (discipline != null) {
                        for (ResearchEntry finaleEntry : discipline.getFinaleEntries(registryAccess, entry.key().getRegistryKey())) {
                            ResearchEntryKey finaleKey = finaleEntry.key();
                            if (!knowledge.isResearchKnown(finaleKey)) {
                                boolean shouldUnlock = finaleEntry.finales().stream().map(k -> registryAccess.registryOrThrow(k.getRegistryKey()).get(k.getRootKey()))
                                        .filter(Objects::nonNull).flatMap(d -> d.getEntryStream(registryAccess, entry.key().getRegistryKey())).filter(e -> e.finales().isEmpty() && !e.flags().finaleExempt()).allMatch(e -> e.isComplete(player));
                                if (shouldUnlock) {
                                    knowledge.addResearch(finaleKey);
                                    if (showPopups) {
                                        knowledge.addResearchFlag(finaleKey, IPlayerKnowledge.ResearchFlag.POPUP);
                                    }
                                    knowledge.addResearchFlag(finaleKey, IPlayerKnowledge.ResearchFlag.NEW);
                                    knowledge.removeResearchFlag(finaleKey, IPlayerKnowledge.ResearchFlag.READ);
                                }
                            }
                        }
                    }
                });
            }
            
            // Trigger any relevant advancements
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggersPM.RESEARCH_COMPLETED.get().trigger(serverPlayer, key);
            }
        }
        
        // If syncing, queue it up for next tick
        if (sync) {
            scheduleSync(player);
        }

        return true;
    }
    
    public static boolean addKnowledge(Player player, KnowledgeType type, int points) {
        return addKnowledge(player, type, points, true);
    }
    
    public static boolean addKnowledge(Player player, KnowledgeType type, int points, boolean scheduleSync) {
        // Add the given number of knowledge points to the player and sync to their client
        IPlayerKnowledge knowledge = ServicesVC.CAPABILITIES.knowledge(player).orElse(null);
        if (knowledge == null) {
            return false;
        }
        int levelsBefore = knowledge.getKnowledge(type);
        boolean success = knowledge.addKnowledge(type, points);
        if (!success) {
            return false;
        }
        if (points > 0) {
            int levelsAfter = knowledge.getKnowledge(type);
            int delta = levelsAfter - levelsBefore;
            if (type == KnowledgeType.OBSERVATION) {
                StatsManager.incrementValue(player, StatsPM.OBSERVATIONS_MADE, delta);
            } else if (type == KnowledgeType.THEORY) {
                StatsManager.incrementValue(player, StatsPM.THEORIES_FORMED, delta);
            }
            for (int index = 0; index < delta; index++) {
                // TODO send knowledge gain packet to player to show client effects for each level gained
            }
        }
        if (scheduleSync) {
            scheduleSync(player);
        }
        return true;
    }
}
