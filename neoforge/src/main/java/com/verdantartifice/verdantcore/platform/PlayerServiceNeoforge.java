package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IPlayerService;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

public class PlayerServiceNeoforge implements IPlayerService {
    @Override
    public void openMenu(ServerPlayer player, MenuProvider provider, BlockPos pos) {
        player.openMenu(provider, buf -> buf.writeBlockPos(pos));
    }
}
