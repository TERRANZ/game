package ru.terra.game.server.network.packet;

import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;

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

	private static Packet getClientPacket(int opCode, long sender)
	{
		switch (opCode)
		{
		case OpCodes.Client.CMSG_LOGIN:
			return new LoginPacket(sender);
		case OpCodes.Client.CMSG_SAY:
			return new SayPacket(sender);
		case OpCodes.Client.CMSG_WISP:
			return new WispPacket(sender);
		}
		return null;
	}

	public static Packet read(ChannelBuffer buffer) throws IOException
	{
		int id = buffer.readUnsignedShort();
		long sguid = buffer.readLong();
		Packet packet = getClientPacket(id, sguid);
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
}
