package com.verdantartifice.verdantcore.platform.services;

import net.minecraft.network.Connection;

public interface ITestService {
    String getTestNamespace(Class<?> testClazz);
    void configureMockConnection(Connection connection);
}
