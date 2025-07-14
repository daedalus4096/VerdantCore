package com.verdantartifice.verdantcore.common.registries;

import com.verdantartifice.verdantcore.common.research.keys.ResearchKeyType;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementType;
import com.verdantartifice.verdantcore.common.research.topics.ResearchTopicType;
import com.verdantartifice.verdantcore.common.rewards.RewardType;
import com.verdantartifice.verdantcore.common.theorycrafting.materials.ProjectMaterialType;
import com.verdantartifice.verdantcore.common.theorycrafting.weights.WeightFunctionType;
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
    public static final ResourceKey<Registry<RewardType<?>>> REWARD_TYPES = key("reward_types");
    public static final ResourceKey<Registry<ResearchTopicType<?>>> RESEARCH_TOPIC_TYPES = key("research_topic_types");
    public static final ResourceKey<Registry<ProjectMaterialType<?>>> PROJECT_MATERIAL_TYPES = key("project_material_types");
    public static final ResourceKey<Registry<WeightFunctionType<?>>> PROJECT_WEIGHT_FUNCTION_TYPES = key("project_weight_function_types");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ResourceUtils.loc(name));
    }
}
