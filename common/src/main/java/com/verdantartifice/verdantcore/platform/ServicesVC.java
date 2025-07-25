package com.verdantartifice.verdantcore.platform;

import com.mojang.logging.LogUtils;
import com.verdantartifice.verdantcore.platform.services.IAttributeService;
import com.verdantartifice.verdantcore.platform.services.IBlockPrototypeService;
import com.verdantartifice.verdantcore.platform.services.IBlockStateService;
import com.verdantartifice.verdantcore.platform.services.ICapabilityService;
import com.verdantartifice.verdantcore.platform.services.IConfigService;
import com.verdantartifice.verdantcore.platform.services.IEventService;
import com.verdantartifice.verdantcore.platform.services.IFluidHandlerService;
import com.verdantartifice.verdantcore.platform.services.IFluidService;
import com.verdantartifice.verdantcore.platform.services.IFluidStateService;
import com.verdantartifice.verdantcore.platform.services.IGuiGraphicsService;
import com.verdantartifice.verdantcore.platform.services.IIngredientService;
import com.verdantartifice.verdantcore.platform.services.IInputService;
import com.verdantartifice.verdantcore.platform.services.IItemAbilityService;
import com.verdantartifice.verdantcore.platform.services.IItemHandlerService;
import com.verdantartifice.verdantcore.platform.services.IItemService;
import com.verdantartifice.verdantcore.platform.services.IItemStackService;
import com.verdantartifice.verdantcore.platform.services.ILevelService;
import com.verdantartifice.verdantcore.platform.services.IMenuService;
import com.verdantartifice.verdantcore.platform.services.IModelResourceLocationService;
import com.verdantartifice.verdantcore.platform.services.INetworkService;
import com.verdantartifice.verdantcore.platform.services.IPartEntityService;
import com.verdantartifice.verdantcore.platform.services.IParticleService;
import com.verdantartifice.verdantcore.platform.services.IPlatformService;
import com.verdantartifice.verdantcore.platform.services.IPlayerService;
import com.verdantartifice.verdantcore.platform.services.IRecipeService;
import com.verdantartifice.verdantcore.platform.services.IShearableService;
import com.verdantartifice.verdantcore.platform.services.ITagService;
import com.verdantartifice.verdantcore.platform.services.ITestService;
import com.verdantartifice.verdantcore.platform.services.registries.IArgumentTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IArmorMaterialRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IBlockEntityTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IBlockRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.ICreativeModeTabRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.ICriterionTriggerRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IDataComponentTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IEnchantmentEntityEffectTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IEnchantmentLocationBasedEffectTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IEntityTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IItemRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.ILootItemConditionTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IMemoryModuleTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IMenuTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IMobEffectRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IParticleTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IProjectMaterialTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IRecipeSerializerRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IRecipeTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IRequirementTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IResearchKeyTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IResearchTopicTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IRewardTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.ISensorTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.ISoundEventRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IStructurePieceTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IStructureTypeRegistryService;
import com.verdantartifice.verdantcore.platform.services.registries.IWeightFunctionTypeRegistryService;
import org.slf4j.Logger;

import java.util.ServiceLoader;

/**
 * Definition point for cross-platform services. These are used to allow common code to call
 * into platform-specific (e.g. Forge) code using Java services.
 *
 * @author Daedalus4096
 */
public class ServicesVC {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final IPlatformService PLATFORM = load(IPlatformService.class);
    public static final IInputService INPUT = load(IInputService.class);
    public static final IConfigService CONFIG = load(IConfigService.class);
    public static final ICapabilityService CAPABILITIES = load(ICapabilityService.class);
    public static final IItemAbilityService ITEM_ABILITIES = load(IItemAbilityService.class);
    public static final IEventService EVENTS = load(IEventService.class);
    public static final IItemHandlerService ITEM_HANDLERS = load(IItemHandlerService.class);
    public static final IPlayerService PLAYER = load(IPlayerService.class);
    public static final ILevelService LEVEL = load(ILevelService.class);
    public static final IMenuService MENU = load(IMenuService.class);
    public static final IShearableService SHEARABLE = load(IShearableService.class);
    public static final IPartEntityService PART_ENTITIES = load(IPartEntityService.class);
    public static final IItemService ITEMS = load(IItemService.class);
    public static final IParticleService PARTICLES = load(IParticleService.class);
    public static final IGuiGraphicsService GUI_GRAPHICS = load(IGuiGraphicsService.class);
    public static final IIngredientService INGREDIENTS = load(IIngredientService.class);
    public static final IBlockStateService BLOCK_STATES = load(IBlockStateService.class);
    public static final IFluidStateService FLUID_STATES = load(IFluidStateService.class);
    public static final IItemStackService ITEM_STACKS = load(IItemStackService.class);
    public static final IAttributeService ATTRIBUTES = load(IAttributeService.class);
    public static final IRecipeService RECIPES = load(IRecipeService.class);
    public static final INetworkService NETWORK = load(INetworkService.class);
    public static final ITestService TEST = load(ITestService.class);
    public static final IModelResourceLocationService MODEL_RESOURCE_LOCATIONS = load(IModelResourceLocationService.class);
    public static final ITagService TAGS = load(ITagService.class);
    public static final IFluidService FLUIDS = load(IFluidService.class);
    public static final IFluidHandlerService FLUID_HANDLERS = load(IFluidHandlerService.class);
    public static final IBlockPrototypeService BLOCK_PROTOTYPES = load(IBlockPrototypeService.class);

    // Registry services
    public static final IBlockRegistryService BLOCKS_REGISTRY = load(IBlockRegistryService.class);
    public static final IItemRegistryService ITEMS_REGISTRY = load(IItemRegistryService.class);
    public static final ICreativeModeTabRegistryService CREATIVE_MODE_TABS_REGISTRY = load(ICreativeModeTabRegistryService.class);
    public static final IArmorMaterialRegistryService ARMOR_MATERIALS_REGISTRY = load(IArmorMaterialRegistryService.class);
    public static final IDataComponentTypeRegistryService DATA_COMPONENT_TYPES_REGISTRY = load(IDataComponentTypeRegistryService.class);
    public static final IEntityTypeRegistryService ENTITY_TYPES_REGISTRY = load(IEntityTypeRegistryService.class);
    public static final IBlockEntityTypeRegistryService BLOCK_ENTITY_TYPES_REGISTRY = load(IBlockEntityTypeRegistryService.class);
    public static final IMenuTypeRegistryService MENU_TYPES_REGISTRY = load(IMenuTypeRegistryService.class);
    public static final IMobEffectRegistryService MOB_EFFECTS_REGISTRY = load(IMobEffectRegistryService.class);
    public static final IRecipeTypeRegistryService RECIPE_TYPES_REGISTRY = load(IRecipeTypeRegistryService.class);
    public static final IRecipeSerializerRegistryService RECIPE_SERIALIZERS_REGISTRY = load(IRecipeSerializerRegistryService.class);
    public static final ISoundEventRegistryService SOUND_EVENTS_REGISTRY = load(ISoundEventRegistryService.class);
    public static final IStructurePieceTypeRegistryService STRUCTURE_PIECE_TYPES_REGISTRY = load(IStructurePieceTypeRegistryService.class);
    public static final IStructureTypeRegistryService STRUCTURE_TYPES_REGISTRY = load(IStructureTypeRegistryService.class);
    public static final IParticleTypeRegistryService PARTICLE_TYPES_REGISTRY = load(IParticleTypeRegistryService.class);
    public static final IArgumentTypeRegistryService ARGUMENT_TYPES_REGISTRY = load(IArgumentTypeRegistryService.class);
    public static final ISensorTypeRegistryService SENSOR_TYPES_REGISTRY = load(ISensorTypeRegistryService.class);
    public static final IMemoryModuleTypeRegistryService MEMORY_MODULE_TYPES_REGISTRY = load(IMemoryModuleTypeRegistryService.class);
    public static final ICriterionTriggerRegistryService CRITERION_TRIGGERS_REGISTRY = load(ICriterionTriggerRegistryService.class);
    public static final IEnchantmentEntityEffectTypeRegistryService ENCHANTMENT_ENTITY_EFFECTS_REGISTRY = load(IEnchantmentEntityEffectTypeRegistryService.class);
    public static final IEnchantmentLocationBasedEffectTypeRegistryService ENCHANTMENT_LOCATION_BASED_EFFECTS_REGISTRY = load(IEnchantmentLocationBasedEffectTypeRegistryService.class);
    public static final ILootItemConditionTypeRegistryService LOOT_ITEM_CONDITION_TYPES_REGISTRY = load(ILootItemConditionTypeRegistryService.class);
    public static final IResearchKeyTypeRegistryService RESEARCH_KEY_TYPES_REGISTRY = load(IResearchKeyTypeRegistryService.class);
    public static final IRequirementTypeRegistryService REQUIREMENT_TYPES_REGISTRY = load(IRequirementTypeRegistryService.class);
    public static final IRewardTypeRegistryService REWARD_TYPES_REGISTRY = load(IRewardTypeRegistryService.class);
    public static final IResearchTopicTypeRegistryService RESEARCH_TOPIC_TYPES_REGISTRY = load(IResearchTopicTypeRegistryService.class);
    public static final IProjectMaterialTypeRegistryService PROJECT_MATERIAL_TYPES_REGISTRY = load(IProjectMaterialTypeRegistryService.class);
    public static final IWeightFunctionTypeRegistryService WEIGHT_FUNCTION_TYPES_REGISTRY = load(IWeightFunctionTypeRegistryService.class);

    // This code is used to load a service for the current environment. Your implementation of the service must be defined
    // manually by including a text file in META-INF/services named with the fully qualified class name of the service.
    // Inside the file you should write the fully qualified class name of the implementation to load for the platform.
    private static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}