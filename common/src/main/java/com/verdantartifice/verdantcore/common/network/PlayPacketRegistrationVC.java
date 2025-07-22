package com.verdantartifice.verdantcore.common.network;

import com.verdantartifice.verdantcore.common.network.packets.data.SyncResearchFlagsPacket;
import com.verdantartifice.verdantcore.common.network.packets.data.TileToClientPacket;
import com.verdantartifice.verdantcore.common.network.packets.data.TileToServerPacket;
import com.verdantartifice.verdantcore.common.network.packets.linguistics.UnlockGridNodeActionPacket;
import commonnetwork.api.Network;

public class PlayPacketRegistrationVC {
    public static void registerMessages() {
        Network
                // Client-bound play channel packets
                .registerPacket(TileToClientPacket.type(), TileToClientPacket.class, TileToClientPacket.STREAM_CODEC, TileToClientPacket::onMessage)
                // Server-bound play channel packets
                .registerPacket(TileToServerPacket.type(), TileToServerPacket.class, TileToServerPacket.STREAM_CODEC, TileToServerPacket::onMessage)
                .registerPacket(SyncResearchFlagsPacket.type(), SyncResearchFlagsPacket.class, SyncResearchFlagsPacket.STREAM_CODEC, SyncResearchFlagsPacket::onMessage)
                .registerPacket(UnlockGridNodeActionPacket.type(), UnlockGridNodeActionPacket.class, UnlockGridNodeActionPacket.STREAM_CODEC, UnlockGridNodeActionPacket::onMessage)
                ;
    }
}
