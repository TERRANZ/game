package ru.terra.game.client.network.packet;

import org.jboss.netty.buffer.ChannelBuffer;

//пакет без параметров
public class SimplePacket extends Packet {

    public SimplePacket(int opCode, long sender) {
        super(opCode, sender);
    }

    @Override
    public void get(ChannelBuffer buffer) {
    }

    @Override
    public void send(ChannelBuffer buffer) {
    }

}
