package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;

public class PlayerInGamePacket extends Packet
{

	private PlayerEntity playerEntity;

	public PlayerInGamePacket(long sender, PlayerEntity playerEntity)
	{
		super(Server.SMSG_PLAYER_IN_GAME, sender);
		this.playerEntity = playerEntity;
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
		writeString(buffer, playerEntity.getName());
		writePosition(buffer, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.getH());
	}

}
