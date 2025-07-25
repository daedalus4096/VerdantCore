package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.enchantments.EnchantmentHelperPM;
import com.verdantartifice.verdantcore.common.enchantments.EnchantmentsPM;
import com.verdantartifice.verdantcore.common.wands.IWand;
import com.verdantartifice.verdantcore.platform.services.IBlockStateService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class BlockStateServiceForge implements IBlockStateService {
    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return state.getExplosionResistance(level, pos, explosion);
    }

    @Override
    public int getExpDrop(BlockState state, Level level, BlockPos pos, @Nullable Entity breaker, ItemStack tool) {
        return state.getExpDrop(level, level.random, pos, getFortuneLevel(tool, level.registryAccess()), getSilkTouchLevel(tool, level.registryAccess()));
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return state.canHarvestBlock(level, pos, player);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return state.onDestroyedByPlayer(level, pos, player, willHarvest, fluid);
    }

    @Override
    public @Nullable BlockState getShearsModifiedState(BlockState state, UseOnContext context, boolean simulate) {
        for (ToolAction action : ToolActions.DEFAULT_SHEARS_ACTIONS) {
            BlockState modifiedState = state.getToolModifiedState(context, action, simulate);
            if (modifiedState != null) {
                return modifiedState;
            }
        }
        return null;
    }

    private static int getFortuneLevel(ItemStack tool, HolderLookup.Provider registries) {
        if (tool.getItem() instanceof IWand) {
            return EnchantmentHelperPM.getEnchantmentLevel(tool, EnchantmentsPM.TREASURE, registries);
        } else {
            return EnchantmentHelperPM.getEnchantmentLevel(tool, Enchantments.FORTUNE, registries);
        }
    }

    private static int getSilkTouchLevel(ItemStack tool, HolderLookup.Provider registries) {
        return EnchantmentHelperPM.getEnchantmentLevel(tool, Enchantments.SILK_TOUCH, registries);
    }
}
