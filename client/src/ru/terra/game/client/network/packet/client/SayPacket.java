package ru.terra.game.client.network.packet.client;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes;

public class SayPacket extends Packet
{

	private String message;

	public SayPacket(long sender)
	{
		super(OpCodes.Client.CMSG_SAY, sender);
	}

	public SayPacket(long sender, String message)
	{
		super(OpCodes.Client.CMSG_SAY, sender);
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
		int length = buffer.readShort();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; ++i)
			builder.append(buffer.readChar());
		message = builder.toString();
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
		buffer.writeShort(message.length());
		for (int i = 0; i < message.length(); ++i)
		{
			buffer.writeChar(message.charAt(i));
		}
	}

}
