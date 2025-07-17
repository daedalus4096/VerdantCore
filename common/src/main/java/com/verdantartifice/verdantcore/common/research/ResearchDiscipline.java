package com.verdantartifice.verdantcore.common.research;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.capabilities.IPlayerKnowledge;
import com.verdantartifice.verdantcore.common.network.PacketHandler;
import com.verdantartifice.verdantcore.common.network.packets.data.SyncResearchFlagsPacket;
import com.verdantartifice.verdantcore.common.research.keys.ResearchDisciplineKey;
import com.verdantartifice.verdantcore.common.research.keys.ResearchEntryKey;
import com.verdantartifice.verdantcore.common.research.requirements.AbstractRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.AndRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.ResearchRequirement;
import com.verdantartifice.verdantcore.common.stats.Stat;
import com.verdantartifice.verdantcore.common.stats.StatsManager;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Definition of a research discipline.  A discipline represents a collection of research entries of
 * similar type (e.g. alchemy).  They are unlocked, yielding access to their entries, by completing
 * other research entries.
 * 
 * @author Daedalus4096
 */
public record ResearchDiscipline(ResearchDisciplineKey key, Optional<AbstractRequirement<?>> unlockRequirementOpt, ResourceLocation iconLocation, Optional<Stat> craftingStat, 
        Optional<Stat> expertiseStat, Optional<Integer> indexSortOrder) {
    public static final Codec<ResearchDiscipline> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResearchDisciplineKey.CODEC.fieldOf("key").forGetter(ResearchDiscipline::key),
                AbstractRequirement.dispatchCodec().optionalFieldOf("unlockRequirementOpt").forGetter(ResearchDiscipline::unlockRequirementOpt),
                ResourceLocation.CODEC.fieldOf("iconLocation").forGetter(ResearchDiscipline::iconLocation),
                ResourceLocation.CODEC.optionalFieldOf("craftingStat").xmap(locOpt -> locOpt.map(StatsManager::getStat), statOpt -> statOpt.map(Stat::key)).forGetter(ResearchDiscipline::craftingStat),
                ResourceLocation.CODEC.optionalFieldOf("expertiseStat").xmap(locOpt -> locOpt.map(StatsManager::getStat), statOpt -> statOpt.map(Stat::key)).forGetter(ResearchDiscipline::expertiseStat),
                Codec.INT.optionalFieldOf("indexSortOrder").forGetter(ResearchDiscipline::indexSortOrder)
            ).apply(instance, ResearchDiscipline::new));

    @Nonnull
    public String getNameTranslationKey() {
        return String.join(".", "research_discipline", Constants.MOD_ID, this.key.getRootKey().location().getPath());
    }
    
    public Stream<ResearchEntry> getEntryStream(RegistryAccess registryAccess, ResourceKey<Registry<ResearchEntry>> registryKey) {
        return registryAccess.registryOrThrow(registryKey).stream().filter(e -> e.isForDiscipline(this.key));
    }

    public boolean isUnlocked(Player player, ResourceKey<Registry<ResearchEntry>> registryKey) {
        return this.unlockRequirementOpt().map(req -> req.isMetBy(player)).orElse(true);
    }

    public boolean isHighlighted(@Nonnull IPlayerKnowledge knowledgeCapability, @Nonnull RegistryAccess registryAccess, ResourceKey<Registry<ResearchEntry>> registryKey) {
        return this.getEntryStream(registryAccess, registryKey).anyMatch(entry -> entry.isHighlighted(knowledgeCapability));
    }

    public boolean isUnread(@Nonnull IPlayerKnowledge knowledgeCapability, @Nonnull RegistryAccess registryAccess, ResourceKey<Registry<ResearchEntry>> registryKey) {
        return this.getEntryStream(registryAccess, registryKey).anyMatch(entry -> entry.isUnread(knowledgeCapability, registryAccess));
    }

    public int getUnreadEntryCount(@Nonnull IPlayerKnowledge knowledgeCapability, @Nonnull RegistryAccess registryAccess, ResourceKey<Registry<ResearchEntry>> registryKey) {
        return this.getEntryStream(registryAccess, registryKey).mapToInt(e -> e.isUnread(knowledgeCapability, registryAccess) ? 1 : 0).sum();
    }

    public void markAllAsRead(Player player, @Nonnull IPlayerKnowledge knowledgeCapability, @Nonnull RegistryAccess registryAccess, ResourceKey<Registry<ResearchEntry>> registryKey) {
        this.getEntryStream(player.registryAccess(), registryKey).filter(e -> e.isUnread(knowledgeCapability, registryAccess)).forEach(e -> {
            e.markRead(knowledgeCapability);
            if (player.level().isClientSide()) {
                PacketHandler.sendToServer(new SyncResearchFlagsPacket(player, e.key()));
            }
        });
    }
    
    /**
     * Get the list of all research entries, from any discipline, which serve as a finale to this discipline.
     * 
     * @return finale research entries for this discipline
     */
    @Nonnull
    public List<ResearchEntry> getFinaleEntries(RegistryAccess registryAccess, ResourceKey<Registry<ResearchEntry>> registryKey) {
        return registryAccess.registryOrThrow(registryKey).stream().filter(e -> e.isFinaleFor(this.key)).toList();
    }
    
    public static Builder builder(ResourceKey<Registry<ResearchDiscipline>> registryKey, ResourceKey<ResearchDiscipline> key) {
        return new Builder(new ResearchDisciplineKey(registryKey, key));
    }

    public static class Builder {
        protected final ResearchDisciplineKey key;
        protected final List<AbstractRequirement<?>> requirements = new ArrayList<>();
        protected ResourceLocation iconLocation = null;
        protected Optional<Stat> craftingStat = Optional.empty();
        protected Optional<Stat> expertiseStat = Optional.empty();
        protected Optional<Integer> indexSortOrder = Optional.empty();
        
        public Builder(ResearchDisciplineKey key) {
            this.key = Preconditions.checkNotNull(key);
        }
        
        public Builder unlock(AbstractRequirement<?> requirement) {
            this.requirements.add(requirement);
            return this;
        }
        
        public Builder unlock(ResourceKey<Registry<ResearchEntry>> registryKey, ResourceKey<ResearchEntry> requiredResearchEntry) {
            return this.unlock(new ResearchRequirement(new ResearchEntryKey(registryKey, requiredResearchEntry)));
        }
        
        public Builder icon(ResourceLocation iconLocation) {
            this.iconLocation = iconLocation;
            return this;
        }
        
        public Builder craftingStat(Stat stat) {
            this.craftingStat = Optional.ofNullable(stat);
            return this;
        }
        
        public Builder expertiseStat(Stat stat) {
            this.expertiseStat = Optional.ofNullable(stat);
            return this;
        }
        
        public Builder indexSortOrder(int order) {
            this.indexSortOrder = Optional.of(order);
            return this;
        }
        
        protected Optional<AbstractRequirement<?>> getFinalRequirement() {
            if (this.requirements.isEmpty()) {
                return Optional.empty();
            } else if (this.requirements.size() == 1) {
                return Optional.of(this.requirements.getFirst());
            } else {
                return Optional.of(new AndRequirement(this.requirements));
            }
        }
        
        private void validate() {
            if (this.iconLocation == null) {
                throw new IllegalStateException("Research discipline must have an icon");
            }
        }
        
        public ResearchDiscipline build() {
            this.validate();
            return new ResearchDiscipline(this.key, this.getFinalRequirement(), this.iconLocation, this.craftingStat, this.expertiseStat, this.indexSortOrder);
        }
    }
}
