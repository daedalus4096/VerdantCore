package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.config.ConfigNeoforge;
import com.verdantartifice.verdantcore.common.theorycrafting.TheorycraftSpeed;
import com.verdantartifice.verdantcore.platform.services.IConfigService;

/**
 * Neoforge implementation of the config service.
 *
 * @author Daedalus4096
 */
public class ConfigServiceNeoforge implements IConfigService {
    @Override
    public boolean showAffinities() {
        return ConfigNeoforge.SHOW_AFFINITIES.getAsBoolean();
    }

    @Override
    public boolean showAffinityIcons() {
        return ConfigNeoforge.SHOW_AFFINITY_ICONS.getAsBoolean();
    }

    @Override
    public boolean showWandHud() {
        return ConfigNeoforge.SHOW_WAND_HUD.getAsBoolean();
    }

    @Override
    public boolean radialReleaseToSwitch() {
        return ConfigNeoforge.RADIAL_RELEASE_TO_SWITCH.getAsBoolean();
    }

    @Override
    public boolean radialClipMouse() {
        return ConfigNeoforge.RADIAL_CLIP_MOUSE.getAsBoolean();
    }

    @Override
    public boolean radialAllowClickOutsideBounds() {
        return ConfigNeoforge.RADIAL_ALLOW_CLICK_OUTSIDE_BOUNDS.getAsBoolean();
    }

    @Override
    public boolean enableManaNetworking() {
        return ConfigNeoforge.ENABLE_MANA_NETWORKING.getAsBoolean();
    }

    @Override
    public boolean showUnscannedAffinities() {
        return ConfigNeoforge.SHOW_UNSCANNED_AFFINITIES.getAsBoolean();
    }

    @Override
    public TheorycraftSpeed theorycraftSpeed() {
        return ConfigNeoforge.THEORYCRAFT_SPEED.get();
    }
}
