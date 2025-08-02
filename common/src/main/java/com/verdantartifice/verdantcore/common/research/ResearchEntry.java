package com.verdantartifice.verdantcore.common.research;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.misc.IconDefinition;
import com.verdantartifice.verdantcore.common.research.keys.ResearchDisciplineKey;
import com.verdantartifice.verdantcore.common.research.keys.ResearchEntryKey;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import com.verdantartifice.verdantcore.common.util.StreamCodecUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Definition of a research entry, the primary component of the research system.  A research entry
 * represents a single node in the mod research tree and a single named entry in the grimoire.  A
 * research entry is made up of one or more stages, which are progressed through in sequence using
 * the grimoire.  It may have one or more parent research entries, which are required to unlock 
 * access to the entry.  It may also have zero or more addenda to be unlocked after the entry is
 * completed.
 * 
 * @author Daedalus4096
 */
public record ResearchEntry(ResearchEntryKey key, Optional<ResearchDisciplineKey> disciplineKeyOpt, Optional<ResearchTier> tierOpt,
                            Optional<String> nameKeyOpt, Optional<IconDefinition> iconOpt, List<ResearchEntryKey> parents, Flags flags,
                            List<ResearchDisciplineKey> finales, List<ResearchStage> stages, List<ResearchAddendum> addenda) {
    public static final Codec<ResearchEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResearchEntryKey.CODEC.fieldOf("key").forGetter(ResearchEntry::key),
                ResearchDisciplineKey.CODEC.codec().optionalFieldOf("disciplineKey").forGetter(ResearchEntry::disciplineKeyOpt),
                ResearchTier.CODEC.optionalFieldOf("tier").forGetter(ResearchEntry::tierOpt),
                Codec.STRING.optionalFieldOf("nameKey").forGetter(ResearchEntry::nameKeyOpt),
                IconDefinition.CODEC.optionalFieldOf("icon").forGetter(ResearchEntry::iconOpt),
                ResearchEntryKey.CODEC.codec().listOf().fieldOf("parents").forGetter(ResearchEntry::parents),
                Flags.CODEC.fieldOf("flags").forGetter(ResearchEntry::flags),
                ResearchDisciplineKey.CODEC.codec().listOf().fieldOf("finales").forGetter(ResearchEntry::finales),
                ResearchStage.codec().listOf().fieldOf("stages").forGetter(ResearchEntry::stages),
                ResearchAddendum.codec().listOf().fieldOf("addenda").forGetter(ResearchEntry::addenda)
            ).apply(instance, ResearchEntry::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ResearchEntry> STREAM_CODEC = StreamCodecUtils.composite(
                ResearchEntryKey.STREAM_CODEC, ResearchEntry::key,
                ByteBufCodecs.optional(ResearchDisciplineKey.STREAM_CODEC), ResearchEntry::disciplineKeyOpt,
                ByteBufCodecs.optional(ResearchTier.STREAM_CODEC), ResearchEntry::tierOpt,
                ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), ResearchEntry::nameKeyOpt,
                ByteBufCodecs.optional(IconDefinition.STREAM_CODEC), ResearchEntry::iconOpt,
                ResearchEntryKey.STREAM_CODEC.apply(ByteBufCodecs.list()), ResearchEntry::parents,
                Flags.STREAM_CODEC, ResearchEntry::flags,
                ResearchDisciplineKey.STREAM_CODEC.apply(ByteBufCodecs.list()), ResearchEntry::finales,
                ResearchStage.streamCodec().apply(ByteBufCodecs.list()), ResearchEntry::stages,
                ResearchAddendum.streamCodec().apply(ByteBufCodecs.list()), ResearchEntry::addenda,
                ResearchEntry::new);

    public static Builder builder(String modId, ResourceKey<ResearchEntry> key) {
        return new Builder(modId, key);
    }
    
    public String getBaseTranslationKey() {
        return String.join(".", "research", this.key.getRootKey().location().getNamespace(), this.key.getRootKey().location().getPath());
    }
    
    public String getNameTranslationKey() {
        return this.nameKeyOpt().orElseGet(() -> String.join(".", this.getBaseTranslationKey(), "title"));
    }
    
    public Optional<String> getHintTranslationKey() {
        if (this.flags().hasHint()) {
            return Optional.of(String.join(".", this.getBaseTranslationKey(), "hint"));
        } else {
            return Optional.empty();
        }
    }
    
    public boolean isForDiscipline(ResearchDisciplineKey discipline) {
        return this.disciplineKeyOpt.isPresent() && this.disciplineKeyOpt.get().equals(discipline);
    }
    
    /**
     * Get whether this research entry is a finale for the given discipline key.
     * 
     * @param discipline the discipline to be tested
     * @return whether this research is a finale for the given discipline key
     */
    public boolean isFinaleFor(ResearchDisciplineKey discipline) {
        return this.finales.contains(discipline);
    }
    
    /**
     * Get whether this research entry is a finale for the given discipline key.
     * 
     * @param discipline the discipline to be tested
     * @return whether this research is a finale for the given discipline key
     */
    public boolean isFinaleFor(ResourceKey<ResearchDiscipline> discipline) {
        return this.isFinaleFor(new ResearchDisciplineKey(discipline));
    }
    
    public boolean isNew(@NotNull IPlayerKnowledge knowledgeCapability) {
        return knowledgeCapability.hasResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.NEW);
    }
    
    public boolean isUpdated(@NotNull IPlayerKnowledge knowledgeCapability) {
        return knowledgeCapability.hasResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.UPDATED);
    }

    public boolean isHighlighted(@NotNull IPlayerKnowledge knowledgeCapability) {
        return knowledgeCapability.hasResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.HIGHLIGHT);
    }

    public boolean isUnread(@NotNull Player player, @NotNull IPlayerKnowledge knowledgeCapability) {
        return !knowledgeCapability.hasResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.READ) && this.isAvailable(player, knowledgeCapability) &&
                (!this.flags().hidden() || knowledgeCapability.getResearchStage(this.key()) >= 0);
    }

    public void markRead(@NotNull IPlayerKnowledge knowledgeCapability) {
        knowledgeCapability.addResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.READ);
        knowledgeCapability.removeResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.NEW);
        knowledgeCapability.removeResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.UPDATED);
        knowledgeCapability.removeResearchFlag(this.key(), IPlayerKnowledge.ResearchFlag.HIGHLIGHT);
    }

    /**
     * Returns whether this entry should be considered read when being imported from knowledge data prior to the version
     * containing that flag option.
     *
     * @param player the player whose data is to be queried
     * @param knowledgeCapability the knowledge capability of the player whose data is to be queried
     * @return true whether this entry should be considered read, false otherwise
     */
    public boolean isReadByDefault(@NotNull Player player, @NotNull IPlayerKnowledge knowledgeCapability) {
        return !this.isAvailable(player, knowledgeCapability) ||
                (!this.isNew(knowledgeCapability) && !this.isUpdated(knowledgeCapability) && !this.isUnknown(player, knowledgeCapability));
    }

    public boolean isComplete(@NotNull IPlayerKnowledge knowledgeCapability, @NotNull RegistryAccess registryAccess) {
        return knowledgeCapability.getResearchStatus(registryAccess, this.key()) == IPlayerKnowledge.ResearchStatus.COMPLETE;
    }
    
    public boolean isInProgress(@NotNull IPlayerKnowledge knowledgeCapability, @NotNull RegistryAccess registryAccess) {
        return knowledgeCapability.getResearchStatus(registryAccess, this.key()) == IPlayerKnowledge.ResearchStatus.IN_PROGRESS;
    }
    
    public boolean isAvailable(@NotNull Player player, @NotNull IPlayerKnowledge knowledgeCapability) {
        return this.parents.isEmpty() || this.parents.stream().allMatch(key -> key.isKnownBy(player));
    }
    
    public boolean isUpcoming(@NotNull Player player, @NotNull IPlayerKnowledge knowledgeCapability, @NotNull Optional<TagKey<ResearchEntry>> opaqueTagOpt) {
        RegistryAccess registryAccess = player.level().registryAccess();
        Registry<ResearchEntry> registry = registryAccess.registryOrThrow(this.key.getRegistryKey());
        return this.parents.stream().map(k -> registry.getHolder(k.getRootKey())).noneMatch(opt -> {
            return opt.isPresent() && ((opaqueTagOpt.isPresent() && opt.get().is(opaqueTagOpt.get()) && !opt.get().value().key().isKnownBy(player)) || !opt.get().value().isAvailable(player, knowledgeCapability));
        });
    }

    public boolean isVisible(@NotNull Player player, @NotNull IPlayerKnowledge knowledgeCapability, @NotNull Optional<TagKey<ResearchEntry>> opaqueTagOpt) {
        return this.isAvailable(player, knowledgeCapability) || this.isUpcoming(player, knowledgeCapability, opaqueTagOpt);
    }

    public boolean isUnknown(@NotNull Player player, @NotNull IPlayerKnowledge knowledgeCapability) {
        return knowledgeCapability.getResearchStatus(player.level().registryAccess(), this.key()) == IPlayerKnowledge.ResearchStatus.UNKNOWN;
    }
    
    @NotNull
    public Set<ResourceLocation> getAllRecipeIds() {
        return Stream.concat(this.stages.stream().flatMap(stage -> stage.recipes().stream()), this.addenda.stream().flatMap(addendum -> addendum.recipes().stream())).collect(Collectors.toSet());
    }
    
    @NotNull
    public Set<ResourceLocation> getKnownRecipeIds(@NotNull Player player, @NotNull IPlayerKnowledge knowledge, @NotNull RegistryAccess registryAccess) {
        Set<ResourceLocation> retVal = new HashSet<>();
        if (this.stages().isEmpty()) {
            // If this research entry has no stages, then it can't have any recipes, so just abort
            return retVal;
        }
        
        ResearchStage currentStage = null;
        int currentStageNum = knowledge.getResearchStage(this.key);
        if (currentStageNum >= 0) {
            currentStage = this.stages().get(Math.min(currentStageNum, this.stages().size() - 1));
        }
        boolean entryComplete = (currentStageNum >= this.stages().size());
        
        if (currentStage != null) {
            retVal.addAll(currentStage.recipes());
        }
        if (entryComplete) {
            for (ResearchAddendum addendum : this.addenda()) {
                addendum.completionRequirementOpt().ifPresent(req -> {
                    if (req.isMetBy(player)) {
                        retVal.addAll(addendum.recipes());
                    }
                });
            }
            registryAccess.registryOrThrow(this.key().getRegistryKey()).forEach(searchEntry -> {
                if (!searchEntry.addenda().isEmpty() && knowledge.isResearchComplete(registryAccess, searchEntry.key())) {
                    for (ResearchAddendum addendum : searchEntry.addenda()) {
                        addendum.completionRequirementOpt().ifPresent(req -> {
                            if (req.contains(this.key) && req.isMetBy(player)) {
                                retVal.addAll(addendum.recipes());
                            }
                        });
                    }
                }
            });
        }
        
        return retVal;
    }
    
    public record Flags(boolean hidden, boolean hasHint, boolean internal, boolean finaleExempt) {
        public static final Flags EMPTY = new Flags(false, false, false, false);
        
        public static final Codec<Flags> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("hidden", false).forGetter(Flags::hidden),
                Codec.BOOL.optionalFieldOf("hasHint", false).forGetter(Flags::hasHint),
                Codec.BOOL.optionalFieldOf("internal", false).forGetter(Flags::internal),
                Codec.BOOL.optionalFieldOf("finaleExempt", false).forGetter(Flags::finaleExempt)
            ).apply(instance, Flags::new));
        
        public static final StreamCodec<ByteBuf, Flags> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                Flags::hidden,
                ByteBufCodecs.BOOL,
                Flags::hasHint,
                ByteBufCodecs.BOOL,
                Flags::internal,
                ByteBufCodecs.BOOL,
                Flags::finaleExempt,
                Flags::new);
        
        public static Builder builder() {
            return new Builder();
        }
        
        public static class Builder {
            protected boolean hidden = false;
            protected boolean hasHint = false;
            protected boolean internal = false;
            protected boolean finaleExempt = false;
            
            public Builder hidden() {
                this.hidden = true;
                return this;
            }
            
            public Builder hasHint() {
                this.hasHint = true;
                return this;
            }
            
            public Builder internal() {
                this.internal = true;
                return this;
            }
            
            public Builder finaleExempt() {
                this.finaleExempt = true;
                return this;
            }
            
            public Flags build() {
                return new Flags(this.hidden, this.hasHint, this.internal, this.finaleExempt);
            }
        }
    }
    
    public static class Builder {
        protected final String modId;
        protected final ResearchEntryKey key;
        protected Optional<ResearchDisciplineKey> disciplineKeyOpt = Optional.empty();
        protected Optional<ResearchTier> tierOpt = Optional.empty();
        protected Optional<String> nameKeyOpt = Optional.empty();
        protected Optional<IconDefinition> iconOpt = Optional.empty();
        protected final List<ResearchEntryKey> parents = new ArrayList<>();
        protected Flags.Builder flagsBuilder = Flags.builder();
        protected final List<ResearchDisciplineKey> finales = new ArrayList<>();
        protected final List<ResearchStage.Builder> stageBuilders = new ArrayList<>();
        protected final List<ResearchAddendum.Builder> addendumBuilders = new ArrayList<>();
        
        public Builder(String modId, ResearchEntryKey key) {
            this.modId = Preconditions.checkNotNull(modId);
            this.key = Preconditions.checkNotNull(key);
        }
        
        public Builder(String modId, ResourceKey<ResearchEntry> rawKey) {
            this(modId, new ResearchEntryKey(rawKey));
        }
        
        public Builder discipline(ResourceKey<ResearchDiscipline> discKey) {
            this.disciplineKeyOpt = Optional.of(new ResearchDisciplineKey(discKey));
            return this;
        }
        
        public Builder tier(ResearchTier tier) {
            this.tierOpt = Optional.ofNullable(tier);
            return this;
        }

        public Builder nameKey(String key) {
            this.nameKeyOpt = Optional.ofNullable(key);
            return this;
        }
        
        public Builder icon(ItemLike item) {
            this.iconOpt = Optional.of(IconDefinition.of(item));
            return this;
        }
        
        public Builder icon(ResourceLocation loc) {
            this.iconOpt = Optional.of(IconDefinition.of(loc));
            return this;
        }
        
        public Builder icon(String path) {
            return this.icon(ResourceUtils.loc(path));
        }
        
        public Builder parent(ResearchEntryKey key) {
            this.parents.add(key);
            return this;
        }
        
        public Builder parent(ResourceKey<ResearchEntry> rawKey) {
            return this.parent(new ResearchEntryKey(rawKey));
        }
        
        public Builder flags(Flags.Builder flagsBuilder) {
            this.flagsBuilder = flagsBuilder;
            return this;
        }
        
        public Builder finale(ResourceKey<ResearchDiscipline> discKey) {
            this.finales.add(new ResearchDisciplineKey(discKey));
            return this;
        }
        
        public ResearchStage.Builder stage() {
            ResearchStage.Builder retVal = new ResearchStage.Builder(this.modId, this, this.key, this.stageBuilders.size() + 1);
            this.stageBuilders.add(retVal);
            return retVal;
        }
        
        public ResearchAddendum.Builder addendum() {
            ResearchAddendum.Builder retVal = new ResearchAddendum.Builder(this.modId, this, this.key, this.addendumBuilders.size() + 1);
            this.addendumBuilders.add(retVal);
            return retVal;
        }
        
        private void validate() {
            if (this.modId.isBlank()) {
                throw new IllegalStateException("No mod ID specified for entry");
            } else if (!this.flagsBuilder.internal && this.disciplineKeyOpt.isEmpty()) {
                throw new IllegalStateException("No discipline specified for non-internal entry");
            } else if (!this.flagsBuilder.internal && this.stageBuilders.isEmpty()) {
                throw new IllegalStateException("Non-internal entries must have at least one stage");
            }
        }
        
        public ResearchEntry build() {
            this.validate();
            return new ResearchEntry(this.key, this.disciplineKeyOpt, this.tierOpt, this.nameKeyOpt, this.iconOpt, this.parents, this.flagsBuilder.build(), this.finales,
                    this.stageBuilders.stream().map(ResearchStage.Builder::build).toList(), this.addendumBuilders.stream().map(ResearchAddendum.Builder::build).toList());
        }
    }
}
