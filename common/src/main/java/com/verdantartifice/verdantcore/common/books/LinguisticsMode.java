package com.verdantartifice.verdantcore.common.books;

import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Defines what mode of operation a linguistics device is currently operating in, and thus what menu
 * it's currently showing, or was last showing.
 * 
 * @author Daedalus4096
 */
public enum LinguisticsMode implements StringRepresentable {
    STUDY_VOCABULARY("study_vocabulary"),
    GAIN_COMPREHENSION("gain_comprehension"),
    TRANSCRIBE_WORKS("transcribe_works");

    private final String tag;
    private final Component tooltip;
    private final ResourceLocation iconSprite;

    LinguisticsMode(String tag) {
        this.tag = tag;
        this.tooltip = Component.translatable("tooltip.verdantcore.scribe_table.mode." + tag);
        this.iconSprite = ResourceUtils.loc("linguistics/" + tag);
    }

    public String getTag() {
        return this.tag;
    }
    
    public Component getTooltip() {
        return this.tooltip;
    }
    
    public ResourceLocation getIconSprite() {
        return this.iconSprite;
    }
    
    @Override
    public @NotNull String getSerializedName() {
        return this.tag;
    }
    
    @Nullable
    public static LinguisticsMode fromName(@Nullable String name) {
        for (LinguisticsMode mode : values()) {
            if (mode.getSerializedName().equals(name)) {
                return mode;
            }
        }
        return null;
    }
}
