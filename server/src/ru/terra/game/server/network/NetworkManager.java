package ru.terra.game.server.network;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.GameManager;
import ru.terra.game.server.network.packet.OkPacket;
import ru.terra.game.server.network.packet.SayPacket;

public class NetworkManager
{
	private static NetworkManager instance = new NetworkManager();
	private Logger log = Logger.getLogger(NetworkManager.class);

	private NetworkManager()
	{
	}

	public static NetworkManager getInstance()
	{
		return instance;
	}

	public void start()
	{
		log.info("Starting network manager...");
		Thread t = new Thread(new NetworkThread());
		t.start();
	}

	public void sendLoginOk(Channel channel, long guid)
	{
		log.info("sending login ok to guid " + guid);
		channel.write(new OkPacket(guid));
	}

	public void sendOk(PlayerEntity playerEntity)
	{
		log.info("sending login ok to player " + playerEntity.getName());
		playerEntity.getChannel().write(new OkPacket(playerEntity.getGUID()));
	}

	public void sendSay(long playerGUID, String message)
	{
		log.info("sending say from " + playerGUID + " : " + message);
		for (PlayerEntity playerEntity : GameManager.getGameManager().getPlayers())
		{
			if (playerEntity.getGUID() != playerGUID)
				playerEntity.getChannel().write(new SayPacket(playerGUID, message));
		}
	}
}