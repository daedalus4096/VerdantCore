package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IAttributeService;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.NeoForgeMod;

public class AttributeServiceNeoforge implements IAttributeService {
    @Override
    public Holder<Attribute> swimSpeed() {
        return NeoForgeMod.SWIM_SPEED;
    }
}
