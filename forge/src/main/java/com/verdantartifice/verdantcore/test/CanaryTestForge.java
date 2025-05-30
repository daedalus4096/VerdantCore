package com.verdantartifice.verdantcore.test;

import com.verdantartifice.verdantcore.Constants;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(Constants.MOD_ID + ".forge")
public class CanaryTestForge extends AbstractCanaryTest {
    @GameTest(template = TestUtilsForge.DEFAULT_TEMPLATE)
    @Override
    public void canary(GameTestHelper helper) {
        super.canary(helper);
    }
}
