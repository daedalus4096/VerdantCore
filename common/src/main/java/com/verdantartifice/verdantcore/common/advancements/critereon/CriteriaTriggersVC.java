package com.verdantartifice.verdantcore.common.advancements.critereon;

import com.verdantartifice.verdantcore.common.registries.IRegistryItem;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.advancements.CriterionTrigger;

import java.util.function.Supplier;

public class CriteriaTriggersVC {
    public static void init() {
        // Pass the service initialization through this class so it gets class loaded and fields registered
        ServicesVC.CRITERION_TRIGGERS_REGISTRY.init();
    }

    public static final IRegistryItem<CriterionTrigger<?>, ResearchCompletedTrigger> RESEARCH_COMPLETED = register("research_completed", ResearchCompletedTrigger::new);
    public static final IRegistryItem<CriterionTrigger<?>, StatValueTrigger> STAT_VALUE = register("stat_value", StatValueTrigger::new);
    public static final IRegistryItem<CriterionTrigger<?>, LinguisticsComprehensionTrigger> LINGUISTICS_COMPREHENSION = register("linguistics_comprehension", LinguisticsComprehensionTrigger::new);
    public static final IRegistryItem<CriterionTrigger<?>, EntityHurtPlayerTriggerExt> ENTITY_HURT_PLAYER_EXT = register("entity_hurt_player_ext", EntityHurtPlayerTriggerExt::new);

    private static <T extends CriterionTrigger<?>> IRegistryItem<CriterionTrigger<?>, T> register(String name, Supplier<T> supplier) {
        return ServicesVC.CRITERION_TRIGGERS_REGISTRY.register(name, supplier);
    }
}
