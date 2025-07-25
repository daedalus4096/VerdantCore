package com.verdantartifice.verdantcore.common.research;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.research.keys.AbstractResearchKey;
import com.verdantartifice.verdantcore.common.research.keys.ResearchEntryKey;
import com.verdantartifice.verdantcore.common.research.keys.StackCraftedKey;
import com.verdantartifice.verdantcore.common.research.keys.TagCraftedKey;
import com.verdantartifice.verdantcore.common.research.requirements.AbstractRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.AndRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.ItemStackRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.ItemTagRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.KnowledgeRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.ResearchRequirement;
import com.verdantartifice.verdantcore.common.research.requirements.StatRequirement;
import com.verdantartifice.verdantcore.common.rewards.AbstractReward;
import com.verdantartifice.verdantcore.common.stats.Stat;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Definition of a research addendum.  An addendum is an addon to a research entry that is separately
 * unlocked with additional research if the entry itself is already unlocked.  Addenda have their own
 * text and may grant new recipes and attunements to the player.
 * 
 * @author Daedalus4096
 */
public record ResearchAddendum(ResearchEntryKey parentKey, String textTranslationKey, Optional<AbstractRequirement<?>> completionRequirementOpt,
                               List<ResourceLocation> recipes, List<AbstractResearchKey<?>> siblings, List<AbstractReward<?>> rewards) {
    public static Codec<ResearchAddendum> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                ResearchEntryKey.CODEC.fieldOf("parentKey").forGetter(ResearchAddendum::parentKey),
                Codec.STRING.fieldOf("textTranslationKey").forGetter(ResearchAddendum::textTranslationKey),
                AbstractRequirement.dispatchCodec().optionalFieldOf("completionRequirementOpt").forGetter(ResearchAddendum::completionRequirementOpt),
                ResourceLocation.CODEC.listOf().fieldOf("recipes").forGetter(ResearchAddendum::recipes),
                AbstractResearchKey.dispatchCodec().listOf().fieldOf("siblings").forGetter(ResearchAddendum::siblings),
                AbstractReward.dispatchCodec().listOf().optionalFieldOf("rewards", List.of()).forGetter(ResearchAddendum::rewards)
            ).apply(instance, ResearchAddendum::new));
    }
    
    public static StreamCodec<RegistryFriendlyByteBuf, ResearchAddendum> streamCodec() {
        return StreamCodec.composite(
                ResearchEntryKey.STREAM_CODEC, ResearchAddendum::parentKey,
                ByteBufCodecs.STRING_UTF8, ResearchAddendum::textTranslationKey,
                ByteBufCodecs.optional(AbstractRequirement.dispatchStreamCodec()), ResearchAddendum::completionRequirementOpt,
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), ResearchAddendum::recipes,
                AbstractResearchKey.dispatchStreamCodec().apply(ByteBufCodecs.list()), ResearchAddendum::siblings,
                AbstractReward.dispatchStreamCodec().apply(ByteBufCodecs.list()), ResearchAddendum::rewards,
                ResearchAddendum::new);
    }
    
    public static class Builder {
        protected final String modId;
        protected final ResearchEntry.Builder entryBuilder;
        protected final ResearchEntryKey parentKey;
        protected final int addendumIndex;
        protected final List<ResourceLocation> recipes = new ArrayList<>();
        protected final List<AbstractResearchKey<?>> siblings = new ArrayList<>();
        protected final List<AbstractRequirement<?>> requirements = new ArrayList<>();
        protected final List<AbstractReward<?>> rewards = new ArrayList<>();

        public Builder(String modId, ResearchEntry.Builder entryBuilder, ResearchEntryKey parentKey, int addendumIndex) {
            this.modId = Preconditions.checkNotNull(modId);
            this.entryBuilder = Preconditions.checkNotNull(entryBuilder);
            this.parentKey = Preconditions.checkNotNull(parentKey);
            this.addendumIndex = addendumIndex;
        }
        
        public Builder(ResearchEntry.Builder entryBuilder, ResearchEntryKey parentKey, int addendumIndex) {
            this(Constants.MOD_ID, entryBuilder, parentKey, addendumIndex);
        }
        
        public Builder recipe(String name) {
            return this.recipe(ResourceUtils.loc(name));
        }
        
        public Builder recipe(String modId, String name) {
            return this.recipe(ResourceLocation.fromNamespaceAndPath(modId, name));
        }
        
        public Builder recipe(ItemLike itemLike) {
            return this.recipe(ServicesVC.ITEMS_REGISTRY.getKey(itemLike.asItem()));
        }
        
        public Builder recipe(ResourceLocation recipe) {
            this.recipes.add(recipe);
            return this;
        }

        public Builder sibling(AbstractResearchKey<?> sibling) {
            this.siblings.add(sibling);
            return this;
        }
        
        public Builder siblingResearch(ResourceKey<ResearchEntry> siblingKey) {
            this.siblings.add(new ResearchEntryKey(siblingKey));
            return this;
        }
        
        public Builder requirement(AbstractRequirement<?> req) {
            this.requirements.add(req);
            return this;
        }
        
        public Builder requiredCraft(ItemStack stack) {
            return this.requirement(new ResearchRequirement(new StackCraftedKey(stack.copy())));
        }
        
        public Builder requiredCraft(ItemLike item) {
            return this.requiredCraft(new ItemStack(item.asItem()));
        }
        
        public Builder requiredCraft(TagKey<Item> tag) {
            return this.requirement(new ResearchRequirement(new TagCraftedKey(tag)));
        }
        
        public Builder requiredItem(ItemStack stack) {
            return this.requirement(new ItemStackRequirement(stack.copy()));
        }
        
        public Builder requiredItem(ItemLike item, int count) {
            return this.requiredItem(new ItemStack(item.asItem(), count));
        }
        
        public Builder requiredItem(ItemLike item) {
            return this.requiredItem(item, 1);
        }
        
        public Builder requiredItem(TagKey<Item> tag, int count) {
            return this.requirement(new ItemTagRequirement(tag, count));
        }
        
        public Builder requiredItem(TagKey<Item> tag) {
            return this.requiredItem(tag, 1);
        }
        
        public Builder requiredResearch(ResourceKey<ResearchEntry> entryKey) {
            return this.requirement(new ResearchRequirement(new ResearchEntryKey(entryKey)));
        }
        
        public Builder requiredKnowledge(KnowledgeType type, int levels) {
            return this.requirement(new KnowledgeRequirement(type, levels));
        }
        
        public Builder requiredStat(Stat stat, int value) {
            return this.requirement(new StatRequirement(stat, value));
        }

        public Builder reward(AbstractReward<?> reward) {
            this.rewards.add(reward);
            return this;
        }

        protected String getTextTranslationKey() {
            return String.join(".", "research", this.modId.toLowerCase(), this.parentKey.getRootKey().location().getPath().toLowerCase(), "text", "addenda", Integer.toString(this.addendumIndex));
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
            if (this.modId.isBlank()) {
                throw new IllegalStateException("Mod ID may not be empty");
            } else if (this.addendumIndex < 0) {
                throw new IllegalStateException("Addendum index must be non-negative");
            }
        }
        
        ResearchAddendum build() {
            this.validate();
            return new ResearchAddendum(this.parentKey, this.getTextTranslationKey(), this.getFinalRequirement(), this.recipes, this.siblings, this.rewards);
        }
        
        public ResearchEntry.Builder end() {
            return this.entryBuilder;
        }
    }
}
