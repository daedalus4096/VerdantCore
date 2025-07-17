package com.verdantartifice.verdantcore.common.items;

import com.verdantartifice.verdantcore.common.capabilities.IItemHandlerPM;

public interface IItemHandlerChangeListener {
    void itemsChanged(int itemHandlerIndex, IItemHandlerPM itemHandler);
}
