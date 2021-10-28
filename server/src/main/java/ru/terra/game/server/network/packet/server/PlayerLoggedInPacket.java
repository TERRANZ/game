package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;
import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;

public class PlayerLoggedInPacket extends Packet {
    private final PlayerEntity player;

    public PlayerLoggedInPacket(long sender, PlayerEntity player) {
        super(Server.SMSG_PLAYER_LOGGED_IN, sender);
        this.player = player;
    }

    @Override
    public void get(ChannelBuffer buffer) {
    }

    @Override
    public void send(ChannelBuffer buffer) {
        writeString(buffer, player.getName());
        buffer.writeFloat(player.getX());
        buffer.writeFloat(player.getY());
        buffer.writeFloat(player.getZ());
        buffer.writeFloat(player.getH());
    }
}
