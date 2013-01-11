package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;
import ru.terra.game.shared.entity.PlayerInfo;

public class PlayerInfoPacket extends Packet
{

	private PlayerInfo playerInfo;

	public PlayerInfoPacket(long sender, PlayerInfo playerInfo)
	{
		super(Server.SMSG_PLAYER_INFO, sender);
		this.playerInfo = playerInfo;
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
		if (playerInfo != null)
		{
			writeString(buffer, playerInfo.getName());
			buffer.writeInt(playerInfo.getHealth_curr());
			buffer.writeInt(playerInfo.getHealth_max());
			buffer.writeInt(playerInfo.getLevel());
			buffer.writeLong(playerInfo.getExp());
		}
	}
}
