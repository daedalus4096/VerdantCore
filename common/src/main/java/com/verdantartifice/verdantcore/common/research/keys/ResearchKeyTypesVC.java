package com.verdantartifice.verdantcore.common.research.keys;

import com.mojang.serialization.MapCodec;
import com.verdantartifice.verdantcore.common.registries.IRegistryItem;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ResearchKeyTypesVC {
    public static void init() {
        // Pass the service initialization through this class so it gets class loaded and fields registered
        ServicesVC.RESEARCH_KEY_TYPES_REGISTRY.init();
    }

    public static final IRegistryItem<ResearchKeyType<?>, ResearchKeyType<ResearchDisciplineKey>> RESEARCH_DISCIPLINE = register("research_discipline", ResearchDisciplineKey.CODEC, ResearchDisciplineKey.STREAM_CODEC);
    public static final IRegistryItem<ResearchKeyType<?>, ResearchKeyType<ResearchEntryKey>> RESEARCH_ENTRY = register("research_entry", ResearchEntryKey.CODEC, ResearchEntryKey.STREAM_CODEC);
    public static final IRegistryItem<ResearchKeyType<?>, ResearchKeyType<ResearchStageKey>> RESEARCH_STAGE = register("research_stage", ResearchStageKey.CODEC, ResearchStageKey.STREAM_CODEC);
    public static final IRegistryItem<ResearchKeyType<?>, ResearchKeyType<StackCraftedKey>> STACK_CRAFTED = register("stack_crafted", StackCraftedKey.CODEC, StackCraftedKey.STREAM_CODEC);
    public static final IRegistryItem<ResearchKeyType<?>, ResearchKeyType<TagCraftedKey>> TAG_CRAFTED = register("tag_crafted", TagCraftedKey.CODEC, TagCraftedKey.STREAM_CODEC);

    protected static <T extends AbstractResearchKey<T>> IRegistryItem<ResearchKeyType<?>, ResearchKeyType<T>> register(String id, MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return ServicesVC.RESEARCH_KEY_TYPES_REGISTRY.register(id, () -> new ResearchKeyType<T>(ResourceUtils.loc(id), codec, streamCodec));
    }
}
