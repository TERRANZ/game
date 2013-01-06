package ru.terra.game.client.network.packet.client;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Client;

public class LogOutPacket extends Packet
{

	public LogOutPacket(long sender)
	{
		super(Client.CMSG_LOGOUT, sender);
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
	}

}
