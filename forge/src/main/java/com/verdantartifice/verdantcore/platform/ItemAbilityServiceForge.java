package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IItemAbilityService;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.loot.CanToolPerformAction;

public class ItemAbilityServiceForge implements IItemAbilityService {
    @Override
    public boolean canAxeStrip(ItemStack stack) {
        return stack.canPerformAction(ToolActions.AXE_STRIP);
    }

    @Override
    public boolean canShieldBlock(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    @Override
    public LootItemCondition.Builder makeShearsDigLootCondition() {
        return CanToolPerformAction.canToolPerformAction(ToolActions.SHEARS_DIG);
    }
}
