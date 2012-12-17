package ru.terra.game.server.network.packet;

import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.shared.constants.OpCodes;

public abstract class Packet
{
	private int opCode;
	private long sender;

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

	private static Packet getClientPacket(int opCode)
	{
		switch (opCode)
		{
		case OpCodes.Client.CMSG_LOGIN:
			return new LoginPacket();
		case OpCodes.Client.CMSG_SAY:
			return new SayPacket();
		case OpCodes.Client.CMSG_WISP:
			return new WispPacket();
		}
		return null;
	}

	public static Packet read(ChannelBuffer buffer) throws IOException
	{
		int id = buffer.readUnsignedShort();
		Packet packet = getClientPacket(id);
		if (packet == null)
			throw new IOException("Bad packet ID: " + id);
		packet.get(buffer);
		return packet;
	}

	public static Packet write(Packet packet, ChannelBuffer buffer)
	{
		buffer.writeChar(packet.getOpCode());
		packet.send(buffer);
		return packet;
	}

	// Функции, которые должен реализовать каждый класс пакета
	public abstract void get(ChannelBuffer buffer);

	public abstract void send(ChannelBuffer buffer);
}
