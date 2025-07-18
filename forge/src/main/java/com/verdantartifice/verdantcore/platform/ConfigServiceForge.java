package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.config.ConfigForge;
import com.verdantartifice.verdantcore.common.theorycrafting.TheorycraftSpeed;
import com.verdantartifice.verdantcore.platform.services.IConfigService;

/**
 * Forge implementation of the config service.
 *
 * @author Daedalus4096
 */
public class ConfigServiceForge implements IConfigService {
    @Override
    public boolean showAffinities() {
        return ConfigForge.SHOW_AFFINITIES.get();
    }

    @Override
    public boolean showAffinityIcons() {
        return ConfigForge.SHOW_AFFINITY_ICONS.get();
    }

    @Override
    public boolean showWandHud() {
        return ConfigForge.SHOW_WAND_HUD.get();
    }

    @Override
    public boolean radialReleaseToSwitch() {
        return ConfigForge.RADIAL_RELEASE_TO_SWITCH.get();
    }

    @Override
    public boolean radialClipMouse() {
        return ConfigForge.RADIAL_CLIP_MOUSE.get();
    }

    @Override
    public boolean radialAllowClickOutsideBounds() {
        return ConfigForge.RADIAL_ALLOW_CLICK_OUTSIDE_BOUNDS.get();
    }

    @Override
    public boolean enableManaNetworking() {
        return ConfigForge.ENABLE_MANA_NETWORKING.get();
    }

    @Override
    public boolean showUnscannedAffinities() {
        return ConfigForge.SHOW_UNSCANNED_AFFINITIES.get();
    }

    @Override
    public TheorycraftSpeed theorycraftSpeed() {
        return ConfigForge.THEORYCRAFT_SPEED.get();
    }
}
