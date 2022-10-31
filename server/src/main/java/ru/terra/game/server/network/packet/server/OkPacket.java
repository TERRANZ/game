package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;

public class OkPacket extends Packet {
    private final long guid;

    public OkPacket(long guid) {
        super(Server.SMSG_OK, guid);
        this.guid = guid;
    }

    @Override
    public void get(ChannelBuffer buffer) {

    }

    @Override
    public void send(ChannelBuffer buffer) {
        buffer.writeLong(guid);
    }

}
