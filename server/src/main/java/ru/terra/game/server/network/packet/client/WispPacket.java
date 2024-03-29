package ru.terra.game.server.network.packet.client;

import org.jboss.netty.buffer.ChannelBuffer;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Client;

public class WispPacket extends Packet {

    private long target = 0l;
    private String message = "";

    public WispPacket(long sender, long target, String message) {
        super(Client.CMSG_WISP, sender);
        this.target = target;
        this.message = message;
    }

    public WispPacket(long sender) {
        super(Client.CMSG_WISP, sender);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTarget() {
        return target;
    }

    public void setTarget(long target) {
        this.target = target;
    }

    @Override
    public void get(ChannelBuffer buffer) {
        int length = buffer.readShort();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i)
            builder.append(buffer.readChar());
        message = builder.toString();
        setTarget(buffer.readLong());
    }

    @Override
    public void send(ChannelBuffer buffer) {
        buffer.writeShort(message.length());
        for (int i = 0; i < message.length(); ++i) {
            buffer.writeChar(message.charAt(i));
        }
        buffer.writeLong(target);
    }

}
