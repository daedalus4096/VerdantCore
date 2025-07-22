package com.verdantartifice.verdantcore.common.research.topics;

import com.mojang.serialization.MapCodec;
import com.verdantartifice.verdantcore.common.registries.IRegistryItem;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ResearchTopicTypesVC {
    public static void init() {
        // Pass the service initialization through this class so it gets class loaded and fields registered
        ServicesVC.RESEARCH_TOPIC_TYPES_REGISTRY.init();
    }

    public static final IRegistryItem<ResearchTopicType<?>, ResearchTopicType<DisciplineResearchTopic>> DISCIPLINE = register("discipline", DisciplineResearchTopic.CODEC, DisciplineResearchTopic.STREAM_CODEC);
    public static final IRegistryItem<ResearchTopicType<?>, ResearchTopicType<EnchantmentResearchTopic>> ENCHANTMENT = register("enchantment", EnchantmentResearchTopic.CODEC, EnchantmentResearchTopic.STREAM_CODEC);
    public static final IRegistryItem<ResearchTopicType<?>, ResearchTopicType<EntryResearchTopic>> RESEARCH_ENTRY = register("research_entry", EntryResearchTopic.CODEC, EntryResearchTopic.STREAM_CODEC);
    public static final IRegistryItem<ResearchTopicType<?>, ResearchTopicType<OtherResearchTopic>> OTHER = register("other", OtherResearchTopic.CODEC, OtherResearchTopic.STREAM_CODEC);

    protected static <T extends AbstractResearchTopic<T>> IRegistryItem<ResearchTopicType<?>, ResearchTopicType<T>> register(String id, MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return ServicesVC.RESEARCH_TOPIC_TYPES_REGISTRY.register(id, () -> new ResearchTopicType<T>(ResourceUtils.loc(id), codec, streamCodec));
    }
}
