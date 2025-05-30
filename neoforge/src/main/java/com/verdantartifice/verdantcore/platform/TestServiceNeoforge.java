package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.ITestService;
import net.minecraft.network.Connection;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

public class TestServiceNeoforge implements ITestService {
    @Override
    public String getTestNamespace(Class<?> testClazz) {
        GameTestHolder holder = testClazz.getAnnotation(GameTestHolder.class);
        if (holder != null && !holder.value().isEmpty()) {
            return holder.value();
        } else {
            return null;
        }
    }

    @Override
    public void configureMockConnection(Connection connection) {
        NetworkRegistry.configureMockConnection(connection);
    }
}
