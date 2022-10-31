package ru.terra.game.client.network.packet.client;

import org.jboss.netty.buffer.ChannelBuffer;
import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes;

public class LoginPacket extends Packet {

    private final String name;

    public LoginPacket(long sender, String name) {
        super(OpCodes.Client.CMSG_LOGIN, sender);
        this.name = name;
    }

    @Override
    public void get(ChannelBuffer buffer) {
    }

    @Override
    public void send(ChannelBuffer buffer) {
        buffer.writeShort(name.length());
        for (int i = 0; i < name.length(); ++i)
            buffer.writeChar(name.charAt(i));
    }

    public String getName() {
        return name;
    }
}
