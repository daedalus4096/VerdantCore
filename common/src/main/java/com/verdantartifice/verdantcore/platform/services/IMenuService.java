package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerPM;
import com.verdantartifice.verdantcore.common.menus.CalcinatorMenu;
import com.verdantartifice.verdantcore.common.menus.RunecarvingTableMenu;
import com.verdantartifice.verdantcore.common.menus.slots.FilteredSlotProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public interface IMenuService {
    Slot makeSlot(IItemHandlerPM itemHandler, int slot, int x, int y);
    Slot makeGenericResultSlot(Player player, IItemHandlerPM itemHandler, int slot, int x, int y);
    Slot makeFilteredSlot(IItemHandlerPM itemHandler, int slot, int x, int y, FilteredSlotProperties properties);
    Slot makeWandSlot(IItemHandlerPM itemHandler, int slot, int x, int y, boolean allowStaves);
    Slot makeCalcinatorFuelSlot(CalcinatorMenu menu, IItemHandlerPM itemHandler, int slot, int x, int y);
    Slot makeCalcinatorResultSlot(Player player, IItemHandlerPM itemHandler, int slot, int x, int y);
    Slot makeInfernalFurnaceResultSlot(Player player, IItemHandlerPM itemHandler, int slot, int x, int y);
    Slot makeConcocterResultSlot(Player player, IItemHandlerPM itemHandler, int slot, int x, int y);
    Slot makeRunecarvingResultSlot(RunecarvingTableMenu menu, Player player, IItemHandlerPM itemHandler, int slot, int x, int y);
}
