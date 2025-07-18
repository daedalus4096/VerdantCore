package com.verdantartifice.verdantcore.client.util;

import com.verdantartifice.verdantcore.client.gui.GrimoireScreen;
import com.verdantartifice.verdantcore.client.gui.StaticBookViewScreen;
import com.verdantartifice.verdantcore.client.gui.recipe_book.ArcaneRecipeUpdateListener;
import com.verdantartifice.verdantcore.common.books.BookType;
import com.verdantartifice.verdantcore.common.books.BookView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Collection of utility methods for accessing client-side data.
 * 
 * @author Daedalus4096
 */
public class ClientUtils {
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
    
    /**
     * Places a ghost recipe into the screen of the given IDed menu.
     * 
     * ONLY CALL THIS METHOD AFTER CHECKING YOUR CURRENT FMLENVIRONMENT DIST.
     * 
     * @param containerId the ID of the menu whose screen to update
     * @param recipeId the ID of the recipe to be placed
     */
    public static void handlePlaceGhostRecipe(int containerId, ResourceLocation recipeId) {
        // TODO Find a better home for this method
        Minecraft mc = Minecraft.getInstance();
        AbstractContainerMenu menu = mc.player.containerMenu;
        if (menu.containerId == containerId) {
            mc.level.getRecipeManager().byKey(recipeId).ifPresent(recipe -> {
                if (mc.screen instanceof ArcaneRecipeUpdateListener listener) {
                    listener.getRecipeBookComponent().setupGhostRecipe(recipe, menu.slots);
                }
            });
        }
    }

    /**
     * Opens the grimoire GUI on the client.
     * 
     * ONLY CALL THIS METHOD AFTER CHECKING YOUR CURRENT FMLENVIRONMENT DIST.
     */
    public static void openGrimoireScreen() {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new GrimoireScreen());
    }
    
    /**
     * Opens the static book GUI on the client for the given book ID.
     * 
     * ONLY CALL THIS METHOD AFTER CHECKING YOUR CURRENT FMLENVIRONMENT DIST.
     * 
     * @param bookId the ID of the static book whose resources to load
     */
    public static void openStaticBookScreen(BookView view, BookType bookType) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new StaticBookViewScreen(view, bookType));
    }
}
