package ru.terra.game.client.network;

import org.jboss.netty.channel.Channel;

import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.client.network.packet.client.LoginPacket;
import ru.terra.game.client.network.packet.client.SayPacket;

public class NetworkManager
{
	private static NetworkManager instance = new NetworkManager();

	private NetworkManager()
	{
	}

	public static NetworkManager getInstance()
	{
		return instance;
	}

	public void start()
	{
		Thread t = new Thread(new NetworkThread());
		t.start();
	}

	private Channel channel;

	public Channel getChannel()
	{
		return channel;
	}

	public void setChannel(Channel channel)
	{
		this.channel = channel;
	}

	private void sendPacket(Packet p)
	{
		getChannel().write(p);
	}

	public void sendLogin(String name)
	{
		sendPacket(new LoginPacket(name));
	}

	public void sendSay(String message)
	{
		sendPacket(new SayPacket(message));
	}

}
