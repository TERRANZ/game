package ru.terra.game.server.network.netty;

import org.jboss.netty.channel.Channel;

import ru.terra.game.server.game.GameManager;
import ru.terra.game.server.network.packet.MovementPacket;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.server.network.packet.client.LoginPacket;
import ru.terra.game.server.network.packet.client.SayPacket;
import ru.terra.game.server.network.packet.client.WispPacket;
import ru.terra.game.shared.constants.OpCodes.Client;

public class PlayerWorkerThread
{
	private Channel channel;
	private PlayerHandler playerHandler;
	private GameManager gm = GameManager.getGameManager();

	public PlayerWorkerThread(PlayerHandler playerHandler, Channel channel)
	{
		this.playerHandler = playerHandler;
		this.channel = channel;
	}

	public void disconnectedFromChannel()
	{
		gm.removePlayer(channel);
	}

	public void acceptPacket(Packet message)
	{
		switch (message.getOpCode())
		{
		case Client.CMSG_LOGIN:
		{
			gm.playerLoggedIn(channel, ((LoginPacket) message).getName());
		}
			break;
		case Client.CMSG_SAY:
		{
			gm.playerSaid(channel, ((SayPacket) message).getMessage(), ((SayPacket) message).getSender());
		}
			break;
		case Client.CMSG_WISP:
		{
			WispPacket p = (WispPacket) message;
			gm.playerWisp(channel, p.getMessage(), p.getSender(), p.getTarget());
		}
			break;
		case Client.CMSG_MOVE_BACK:
		case Client.CMSG_MOVE_FORWARD:
		case Client.CMSG_MOVE_LEFT:
		case Client.CMSG_MOVE_RIGHT:
		case Client.CMSG_MOVE_TELEPORT:
		{
			MovementPacket p = (MovementPacket) message;
			gm.updatePlayerPos(channel, p.getSender(), p.getOpCode(), p.getX(), p.getY(), p.getZ(), p.getH());
		}
			break;
		}
	}
}
