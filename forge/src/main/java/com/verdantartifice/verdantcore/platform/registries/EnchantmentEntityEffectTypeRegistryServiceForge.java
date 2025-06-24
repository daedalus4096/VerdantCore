package com.verdantartifice.verdantcore.platform.registries;

import com.mojang.serialization.MapCodec;
import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IEnchantmentEntityEffectTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EnchantmentEntityEffectTypeRegistryServiceForge extends AbstractBuiltInRegistryServiceForge<MapCodec<? extends EnchantmentEntityEffect>> implements IEnchantmentEntityEffectTypeRegistryService {
    private static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>>> getDeferredRegisterSupplier() {
        return () -> EFFECTS;
    }

    @Override
    protected Registry<MapCodec<? extends EnchantmentEntityEffect>> getRegistry() {
        return BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE;
    }
}
