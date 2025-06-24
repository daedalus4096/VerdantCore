package com.verdantartifice.verdantcore.platform.services;

import net.minecraft.world.entity.Entity;

public interface IPartEntityService {
    boolean isPartEntity(Entity entity);
    Entity getParent(Entity partEntity);
}
