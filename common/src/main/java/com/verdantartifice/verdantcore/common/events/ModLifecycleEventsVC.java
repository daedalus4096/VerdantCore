package com.verdantartifice.verdantcore.common.events;

import com.verdantartifice.verdantcore.common.network.PlayPacketRegistrationVC;
import com.verdantartifice.verdantcore.platform.ServicesVC;

import java.util.function.Consumer;

public class ModLifecycleEventsVC {
    public static void commonSetup(Consumer<Runnable> workConsumer) {
        workConsumer.accept(() -> {
            PlayPacketRegistrationVC.registerMessages();
            ServicesVC.NETWORK.registerConfigMessages();
        });
    }
}
