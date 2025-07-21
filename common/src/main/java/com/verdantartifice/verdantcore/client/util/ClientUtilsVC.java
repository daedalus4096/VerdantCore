package com.verdantartifice.verdantcore.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Collection of utility methods for accessing client-side data.
 * 
 * @author Daedalus4096
 */
public class ClientUtilsVC {
    /**
     * Gets the current client-side player.
     * 
     * ONLY CALL THIS METHOD AFTER CHECKING YOUR CURRENT FMLENVIRONMENT DIST.
     * 
     * @return the current client-side player
     */
    @Nullable
    public static Player getCurrentPlayer() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player;
    }

    /**
     * Gets the current client-side level.
     * 
     * ONLY CALL THIS METHOD AFTER CHECKING YOUR CURRENT FMLENVIRONMENT DIST.
     * 
     * @return the current client-side level
     */
    @Nullable
    public static Level getCurrentLevel() {
        Minecraft mc = Minecraft.getInstance();
        return mc.level;
    }
    
    /**
     * Gets the stats counter for the current client-side player.
     * 
     * ONLY CALL THIS METHOD AFTER CHECKING YOUR CURRENT FMLENVIRONMENT DIST.
     * 
     * @return the stats counter for the current player
     */
    @Nullable
    public static StatsCounter getStatsCounter() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player.getStats();
    }
    
    /**
     * Gets whether the player has the shift key held down.
     * 
     * ONLY CALL THIS METHOD AFTER CHECKING YOUR CURRENT FMLENVIRONMENT DIST.
     * 
     * @return whether the shift key is down
     */
    public static boolean hasShiftDown() {
        return Screen.hasShiftDown();
    }
}
