package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.menus.CalcinatorMenu;
import com.verdantartifice.verdantcore.common.menus.RunecarvingTableMenu;
import com.verdantartifice.verdantcore.common.menus.slots.CalcinatorFuelSlotNeoforge;
import com.verdantartifice.verdantcore.common.menus.slots.CalcinatorResultSlotNeoforge;
import com.verdantartifice.verdantcore.common.menus.slots.ConcocterResultSlotNeoforge;
import com.verdantartifice.verdantcore.common.menus.slots.FilteredSlotNeoforge;
import com.verdantartifice.verdantcore.common.menus.slots.FilteredSlotProperties;
import com.verdantartifice.verdantcore.common.menus.slots.GenericResultSlotNeoforge;
import com.verdantartifice.verdantcore.common.menus.slots.InfernalFurnaceResultSlotNeoforge;
import com.verdantartifice.verdantcore.common.menus.slots.RunecarvingResultSlotNeoforge;
import com.verdantartifice.verdantcore.common.menus.slots.WandSlotNeoforge;
import com.verdantartifice.verdantcore.platform.services.IMenuService;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MenuServiceNeoforge implements IMenuService {
    @Override
    public Slot makeSlot(IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new SlotItemHandler(neoforgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeGenericResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new GenericResultSlotNeoforge(player, neoforgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeFilteredSlot(IItemHandlerVC itemHandler, int slot, int x, int y, FilteredSlotProperties properties) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new FilteredSlotNeoforge(neoforgeHandler, slot, x, y, properties);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeWandSlot(IItemHandlerVC itemHandler, int slot, int x, int y, boolean allowStaves) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new WandSlotNeoforge(neoforgeHandler, slot, x, y, allowStaves);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeCalcinatorFuelSlot(CalcinatorMenu menu, IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new CalcinatorFuelSlotNeoforge(menu, neoforgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeCalcinatorResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new CalcinatorResultSlotNeoforge(player, neoforgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeInfernalFurnaceResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new InfernalFurnaceResultSlotNeoforge(player, neoforgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeConcocterResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new ConcocterResultSlotNeoforge(player, neoforgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeRunecarvingResultSlot(RunecarvingTableMenu menu, Player player, IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler neoforgeHandler) {
            return new RunecarvingResultSlotNeoforge(menu, player, neoforgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler is not an instance of IItemHandler");
        }
    }
}
