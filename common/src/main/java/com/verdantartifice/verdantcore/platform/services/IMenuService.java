package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.menus.CalcinatorMenu;
import com.verdantartifice.verdantcore.common.menus.RunecarvingTableMenu;
import com.verdantartifice.verdantcore.common.menus.slots.FilteredSlotProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public interface IMenuService {
    Slot makeSlot(IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeGenericResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeFilteredSlot(IItemHandlerVC itemHandler, int slot, int x, int y, FilteredSlotProperties properties);
    Slot makeWandSlot(IItemHandlerVC itemHandler, int slot, int x, int y, boolean allowStaves);
    Slot makeCalcinatorFuelSlot(CalcinatorMenu menu, IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeCalcinatorResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeInfernalFurnaceResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeConcocterResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y);
    Slot makeRunecarvingResultSlot(RunecarvingTableMenu menu, Player player, IItemHandlerVC itemHandler, int slot, int x, int y);
}
