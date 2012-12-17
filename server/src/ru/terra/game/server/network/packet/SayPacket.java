package ru.terra.game.server.network.packet;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.shared.constants.OpCodes.Client;

public class SayPacket extends Packet
{

	public SayPacket(long sender)
	{
		super(Client.CMSG_SAY, sender);
	}

	public SayPacket(long sender, String message)
	{
		super(Client.CMSG_SAY, sender);
		setMessage(message);
	}

	private String message;

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
