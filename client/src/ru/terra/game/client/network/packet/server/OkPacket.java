package ru.terra.game.client.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;
import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes;

public class OkPacket extends Packet {
    private long guid;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public OkPacket(long sender) {
        super(OpCodes.Server.SMSG_OK, sender);
    }

    @Override
    public void get(ChannelBuffer buffer) {
        guid = buffer.readLong();
    }

    @Override
    public void send(ChannelBuffer buffer) {
    }
}
