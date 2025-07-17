package com.verdantartifice.verdantcore.common.network;

import com.verdantartifice.verdantcore.common.network.packets.data.TileToClientPacket;
import com.verdantartifice.verdantcore.common.network.packets.data.TileToServerPacket;
import commonnetwork.api.Network;

public class PlayPacketRegistrationVC {
    public static void registerMessages() {
        Network
                // Client-bound play channel packets
                .registerPacket(TileToClientPacket.type(), TileToClientPacket.class, TileToClientPacket.STREAM_CODEC, TileToClientPacket::onMessage)
                // Server-bound play channel packets
                .registerPacket(TileToServerPacket.type(), TileToServerPacket.class, TileToServerPacket.STREAM_CODEC, TileToServerPacket::onMessage)
                ;
    }
}
