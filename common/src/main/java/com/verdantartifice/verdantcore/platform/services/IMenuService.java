package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.menus.slots.FilteredSlotProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public interface IMenuService {
    Slot makeSlot(IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeGenericResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeFilteredSlot(IItemHandlerVC itemHandler, int slot, int x, int y, FilteredSlotProperties properties);
}
