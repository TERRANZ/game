package ru.terra.game.client.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.client.entity.Player;
import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;

public class PlayerLoggedInPacket extends Packet
{

	private Player enemy;

	public PlayerLoggedInPacket(long sender)
	{
		super(Server.SMSG_PLAYER_LOGGED_IN, sender);
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
		int length = buffer.readShort();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; ++i)
			builder.append(buffer.readChar());
		String name = builder.toString();
		enemy = new Player(getSender(), name);
		readEntityPosition(enemy, buffer);
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
	}

	public Player getEnemy()
	{
		return enemy;
	}
}
