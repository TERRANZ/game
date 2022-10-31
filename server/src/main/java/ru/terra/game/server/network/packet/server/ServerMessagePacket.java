package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes;

public class ServerMessagePacket extends Packet {

    private final String message;

    public ServerMessagePacket(String message) {
        super(OpCodes.Server.SMSG_MESSAGE, 0);
        this.message = message;
    }

    @Override
    public void get(ChannelBuffer buffer) {
    }

    @Override
    public void send(ChannelBuffer buffer) {
        buffer.writeShort(message.length());
        for (int i = 0; i < message.length(); ++i) {
            buffer.writeChar(message.charAt(i));
        }
    }

}
