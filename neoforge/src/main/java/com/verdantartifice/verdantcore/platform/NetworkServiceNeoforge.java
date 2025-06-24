package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.INetworkService;

public class NetworkServiceNeoforge implements INetworkService {
    @Override
    public void registerConfigMessages() {
        // Neoforge network payload registration is done in response to a dedicated event, rather than during common
        // setup, so nothing needs to be done here.
    }
}
