package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;

public class PlayerLoggedInPacket extends Packet
{
	private PlayerEntity player;

	public PlayerLoggedInPacket(long sender, PlayerEntity player)
	{
		super(Server.SMSG_PLAYER_LOGGED_IN, sender);
		this.player = player;
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
		buffer.writeShort(player.getName().length());
		for (int i = 0; i < player.getName().length(); ++i)
		{
			buffer.writeChar(player.getName().charAt(i));
		}
		buffer.writeFloat(player.getX());
		buffer.writeFloat(player.getY());
		buffer.writeFloat(player.getZ());
		buffer.writeFloat(player.getH());
	}
}
