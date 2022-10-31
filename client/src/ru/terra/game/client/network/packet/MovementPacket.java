package ru.terra.game.client.network.packet;

import org.jboss.netty.buffer.ChannelBuffer;

public class MovementPacket extends Packet {

    private float z;
    private float y;
    private float x;
    private float h;

    public MovementPacket(int opCode, long sender) {
        super(opCode, sender);
    }

    public MovementPacket(int opCode, long sender, float x, float y, float z, float h) {
        super(opCode, sender);
        this.x = x;
        this.y = y;
        this.z = z;
        this.h = h;
    }

    @Override
    public void get(ChannelBuffer buffer) {
        x = buffer.readFloat();
        y = buffer.readFloat();
        z = buffer.readFloat();
        h = buffer.readFloat();
    }

    @Override
    public void send(ChannelBuffer buffer) {
        buffer.writeFloat(x);
        buffer.writeFloat(y);
        buffer.writeFloat(z);
        buffer.writeFloat(h);
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

}
