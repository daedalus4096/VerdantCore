package com.verdantartifice.verdantcore.common.menus.base;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;
import com.verdantartifice.verdantcore.common.tiles.base.AbstractTileSidedInventoryVC;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;

/**
 * Base class for a menu that serves a mod block entity with an attached sided item handler.
 * 
 * @author Daedalus4096
 */
public abstract class AbstractTileSidedInventoryMenu<T extends AbstractTileSidedInventoryVC> extends AbstractTileMenu<T> {
    protected AbstractTileSidedInventoryMenu(MenuType<?> menuType, int containerId, Class<T> tileClass, Level level, BlockPos tilePos, T tile) {
        super(menuType, containerId, tileClass, level, tilePos, tile);
    }
    
    public IItemHandlerVC getTileInventory(Direction face) {
        return ServicesVC.CAPABILITIES.itemHandler(this.tile, face).orElseThrow(IllegalStateException::new);
    }

    public IItemHandlerVC getTileInventory(int index) {
        IItemHandlerVC retVal = this.tile.getRawItemHandler(index);
        if (retVal == null) {
            throw new IllegalStateException("No tile inventory found for index " + index);
        } else {
            return retVal;
        }
    }
}
