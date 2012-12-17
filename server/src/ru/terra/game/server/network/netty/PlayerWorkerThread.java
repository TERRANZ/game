package ru.terra.game.server.network.netty;

import org.jboss.netty.channel.Channel;

import ru.terra.game.server.game.GameManager;
import ru.terra.game.server.network.packet.LoginPacket;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.server.network.packet.SayPacket;
import ru.terra.game.server.network.packet.WispPacket;
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
		}
	}
}
