package com.verdantartifice.verdantcore.common.items;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerVC;

public interface IItemHandlerChangeListener {
    void itemsChanged(int itemHandlerIndex, IItemHandlerVC itemHandler);
}
