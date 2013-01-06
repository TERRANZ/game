package ru.terra.game.client.network.packet;

import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.client.entity.Entity;
import ru.terra.game.client.network.packet.client.SayPacket;
import ru.terra.game.client.network.packet.server.OkPacket;
import ru.terra.game.shared.constants.OpCodes;

public abstract class Packet
{
	private int opCode;
	private long sender;

	public Packet(int opCode, long sender)
	{
		this.opCode = opCode;
		this.sender = sender;
	}

	public long getSender()
	{
		return sender;
	}

	public void setSender(long sender)
	{
		this.sender = sender;
	}

	public int getOpCode()
	{
		return opCode;
	}

	public void setOpCode(int id)
	{
		this.opCode = id;
	}

	private static Packet getServerPacket(int opCode, long sender)
	{
		switch (opCode)
		{
		case OpCodes.Server.SMSG_OK:
			return new OkPacket(sender);
		case OpCodes.Server.SMSG_SAY:
			return new SayPacket(sender);
		}
		return null;
	}

	public static Packet read(ChannelBuffer buffer) throws IOException
	{
		int id = buffer.readUnsignedShort();
		long sguid = buffer.readLong();
		Packet packet = getServerPacket(id, sguid);
		if (packet == null)
			throw new IOException("Bad packet ID: " + id);

		packet.get(buffer);
		return packet;
	}

	public static Packet write(Packet packet, ChannelBuffer buffer)
	{
		buffer.writeChar(packet.getOpCode());
		buffer.writeLong(packet.getSender());
		packet.send(buffer);
		return packet;
	}

	// Функции, которые должен реализовать каждый класс пакета
	public abstract void get(ChannelBuffer buffer);

	public abstract void send(ChannelBuffer buffer);

	protected void readEntityPosition(Entity entity, ChannelBuffer buffer)
	{
		if (entity != null)
		{
			float x = buffer.readFloat();
			float y = buffer.readFloat();
			float z = buffer.readFloat();
			float h = buffer.readFloat();
			entity.setPosition(x, y, z, h);
		}
	}
}
