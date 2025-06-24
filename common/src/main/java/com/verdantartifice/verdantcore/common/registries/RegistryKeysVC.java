package com.verdantartifice.verdantcore.common.registries;

import com.verdantartifice.verdantcore.common.rewards.RewardType;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Home for resource keys for custom mod registries.
 * 
 * @author Daedalus4096
 */
public class RegistryKeysVC {
    public static final ResourceKey<Registry<RequirementType<?>>> REQUIREMENT_TYPES = key("requirement_types");
    public static final ResourceKey<Registry<ResearchKeyType<?>>> RESEARCH_KEY_TYPES = key("research_key_types");
    public static final ResourceKey<Registry<ResearchDiscipline>> RESEARCH_DISCIPLINES = key("research_disciplines");
    public static final ResourceKey<Registry<ResearchEntry>> RESEARCH_ENTRIES = key("research_entries");
    public static final ResourceKey<Registry<RewardType<?>>> REWARD_TYPES = key("reward_types");
    public static final ResourceKey<Registry<ResearchTopicType<?>>> RESEARCH_TOPIC_TYPES = key("research_topic_types");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ResourceUtils.loc(name));
    }
}
