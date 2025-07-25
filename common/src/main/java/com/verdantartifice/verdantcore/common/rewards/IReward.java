package com.verdantartifice.verdantcore.common.rewards;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Abstraction interface intended for use in determining rewards for linguistics comprehension grids.
 * 
 * @author Daedalus4096
 */
public interface IReward {
    void grant(ServerPlayer player);
    Component getDescription(Player player);
    ResourceLocation getIconLocation(Player player);
    Optional<Component> getAmountText();
}
