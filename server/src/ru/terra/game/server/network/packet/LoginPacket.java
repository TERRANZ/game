package ru.terra.game.server.network.packet;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.shared.constants.OpCodes.Client;

public class LoginPacket extends Packet
{

	private String name;

	public LoginPacket(long sender)
	{
		super(Client.CMSG_LOGIN, sender);
	}

	public LoginPacket(long sender, String name)
	{
		super(Client.CMSG_LOGIN, sender);
		this.name = name;
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
		int length = buffer.readShort();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; ++i)
			builder.append(buffer.readChar());
		name = builder.toString();
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
	}

	public String getName()
	{
		return name;
	}
}
