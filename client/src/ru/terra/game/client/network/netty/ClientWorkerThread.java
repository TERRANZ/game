package ru.terra.game.client.network.netty;

import org.jboss.netty.channel.Channel;

import ru.terra.game.client.game.GameManager;
import ru.terra.game.client.network.packet.MovementPacket;
import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.client.network.packet.client.SayPacket;
import ru.terra.game.client.network.packet.server.MapObjectAddPacket;
import ru.terra.game.client.network.packet.server.OkPacket;
import ru.terra.game.client.network.packet.server.PlayerLoggedInPacket;
import ru.terra.game.shared.constants.OpCodes.Client;
import ru.terra.game.shared.constants.OpCodes.Server;

public class ClientWorkerThread
{

	private GameManager gm = GameManager.getInstance();

	public ClientWorkerThread(ClientHandler clientHandler, Channel channel)
	{
	}

	public void disconnectedFromChannel()
	{
	}

	public void acceptPacket(Packet packet)
	{
		switch (packet.getOpCode())
		{
		case Server.SMSG_OK:
		{
			gm.ok((OkPacket) packet);
		}
			break;
		case Server.SMSG_SAY:
		{
			gm.playerSaid(((SayPacket) packet).getSender(), ((SayPacket) packet).getMessage());
		}
			break;
		case Server.SMSG_PLAYER_LOGGED_IN:
		{
			gm.enemyLoggedIn(((PlayerLoggedInPacket) packet).getEnemy());
		}
			break;
		case Server.SMSG_MAPOBJECT_ADD:
		{
			gm.entityAdd(((MapObjectAddPacket) (packet)).getMapObject());
		}
			break;
		case Client.CMSG_MOVE_BACK:
		case Client.CMSG_MOVE_FORWARD:
		case Client.CMSG_MOVE_LEFT:
		case Client.CMSG_MOVE_RIGHT:
		case Client.CMSG_MOVE_TELEPORT:
		case Server.SMSG_MOVE_HEARTBEAT:
		{
			MovementPacket movementPacket = (MovementPacket) packet;
			gm.entityMoved(movementPacket.getSender(), movementPacket.getX(), movementPacket.getY(), movementPacket.getZ(), movementPacket.getH());
		}
			break;

		}
	}
}
