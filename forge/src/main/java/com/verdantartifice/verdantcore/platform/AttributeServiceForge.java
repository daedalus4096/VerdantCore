package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IAttributeService;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.ForgeMod;

public class AttributeServiceForge implements IAttributeService {
    @Override
    public Holder<Attribute> swimSpeed() {
        return ForgeMod.SWIM_SPEED.getHolder().get();
    }
}
