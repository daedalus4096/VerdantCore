package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.network.ConfigPacketHandlerForge;
import com.verdantartifice.verdantcore.platform.services.INetworkService;

public class NetworkServiceForge implements INetworkService {
    @Override
    public void registerConfigMessages() {
        ConfigPacketHandlerForge.registerMessages();
    }
}
