package com.verdantartifice.verdantcore.test;

import net.minecraft.gametest.framework.GameTestHelper;

public abstract class AbstractCanaryTest extends AbstractBaseTest {
    public void canary(GameTestHelper helper) {
        helper.assertTrue(true, "Something's wrong with the universe!");
        helper.succeed();
    }
}
