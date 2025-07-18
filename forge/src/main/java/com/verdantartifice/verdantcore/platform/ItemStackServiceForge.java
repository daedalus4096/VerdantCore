package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IItemStackService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemStackServiceForge implements IItemStackService {
    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        return stack.onBlockStartBreak(pos, player);
    }

    @Override
    public boolean canGrindstoneRepair(ItemStack stack) {
        return stack.canGrindstoneRepair();
    }
}
