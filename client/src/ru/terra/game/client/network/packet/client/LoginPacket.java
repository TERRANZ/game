package ru.terra.game.client.network.packet.client;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes;

public class LoginPacket extends Packet
{

	private String name;

	public LoginPacket(String name)
	{
		this.name = name;
		setOpCode(OpCodes.Client.CMSG_LOGIN);
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
		buffer.writeShort(name.length());
		for (int i = 0; i < name.length(); ++i)
			buffer.writeChar(name.charAt(i));
	}

	public String getName()
	{
		return name;
	}
}
