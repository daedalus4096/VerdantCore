package com.verdantartifice.verdantcore.client.books;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public record BookSprites(ResourceLocation background, WidgetSprites forward, WidgetSprites backward) {
    public ResourceLocation getPageButton(boolean forward, boolean highlighted) {
        return forward ? this.getForward(highlighted) : this.getBackward(highlighted);
    }
    
    public ResourceLocation getForward(boolean highlighted) {
        return this.forward.get(true, highlighted);
    }
    
    public ResourceLocation getBackward(boolean highlighted) {
        return this.backward.get(true, highlighted);
    }
}
