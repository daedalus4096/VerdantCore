package com.verdantartifice.verdantcore.common.research;

import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Interface for a research key, which can be tested for known-ness for a player.
 * 
 * @author Daedalus4096
 */
public interface IResearchKey {
    boolean isEmpty();
    boolean isKnownBy(@Nullable Player player);
    boolean isKnownByStrict(@Nullable Player player);
}