package ru.terra.game.client.network.netty;

import org.jboss.netty.channel.Channel;

import ru.terra.game.client.game.GameManager;
import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.client.network.packet.server.OkPacket;
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

	public void acceptPacket(Packet message)
	{
		switch (message.getOpCode())
		{
		case Server.SMSG_OK:
		{
			gm.ok((OkPacket) message);
		}
			break;
		}
	}
}
