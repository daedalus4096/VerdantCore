package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.menus.slots.FilteredSlotForge;
import com.verdantartifice.verdantcore.common.menus.slots.FilteredSlotProperties;
import com.verdantartifice.verdantcore.common.menus.slots.GenericResultSlotForge;
import com.verdantartifice.verdantcore.platform.services.IMenuService;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MenuServiceForge implements IMenuService {
    @Override
    public Slot makeSlot(IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler forgeHandler) {
            return new SlotItemHandler(forgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler must be an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeGenericResultSlot(Player player, IItemHandlerVC itemHandler, int slot, int x, int y) {
        if (itemHandler instanceof IItemHandler forgeHandler) {
            return new GenericResultSlotForge(player, forgeHandler, slot, x, y);
        } else {
            throw new IllegalArgumentException("itemHandler must be an instance of IItemHandler");
        }
    }

    @Override
    public Slot makeFilteredSlot(IItemHandlerVC itemHandler, int slot, int x, int y, FilteredSlotProperties properties) {
        if (itemHandler instanceof IItemHandler forgeHandler) {
            return new FilteredSlotForge(forgeHandler, slot, x, y, properties);
        } else {
            throw new IllegalArgumentException("itemHandler must be an instance of IItemHandler");
        }
    }
}
