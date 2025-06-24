package com.verdantartifice.verdantcore.platform.services.registries;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public interface IEnchantmentEntityEffectTypeRegistryService extends IRegistryService<MapCodec<? extends EnchantmentEntityEffect>> {
}
