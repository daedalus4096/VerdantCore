package com.verdantartifice.verdantcore.common.events;

import com.verdantartifice.verdantcore.Constants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Neoforge listeners for mod lifecycle related events.
 * 
 * @author Daedalus4096
 */
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModLifecycleEventListeners {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        ModLifecycleEventsVC.commonSetup(event::enqueueWork);
    }
}
