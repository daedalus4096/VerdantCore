package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.misc.GrindstoneChangeRecord;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface IEventService {
    void firePlayerCraftingEvent(Player player, ItemStack crafted, Container craftMatrix);
    void firePlayerSmeltedEvent(Player player, ItemStack smelted);
    boolean canEntityGrief(Level level, @Nullable Entity entity);
    boolean onBlockPlace(@Nullable Entity entity, @NotNull Level level, @NotNull BlockPos pos, @NotNull Direction direction);
    SpawnGroupData finalizeMobSpawn(Mob mob, ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData);
    void fireLivingJumpEvent(LivingEntity entity);
    void setCraftingPlayer(Player player);

    /**
     * Attempts to perform an ender entity teleport by firing the appropriate event.
     *
     * @param entity the entity doing the teleportation
     * @param target the intended target point of the teleportation
     * @return an optional containing the finalized target point of the teleportation, or empty if the teleport was cancelled
     */
    Optional<Vec3> attemptEnderEntityTeleport(LivingEntity entity, Vec3 target);

    int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType);

    int fireBlockBreakEvent(Level level, GameType gameType, ServerPlayer entityPlayer, BlockPos pos);
    boolean isCorrectToolForDrops(Player player, BlockState state, Level level, BlockPos pos);
    GrindstoneChangeRecord onGrindstoneChange(ItemStack top, ItemStack bottom, Container outputSlot, int xp);
}
