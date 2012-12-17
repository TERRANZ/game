package ru.terra.game.server.entity;

import org.jboss.netty.channel.Channel;

public class PlayerEntity extends Entity
{
	private Channel channel;

	public Channel getChannel()
	{
		return channel;
	}

	public void setChannel(Channel channel)
	{
		this.channel = channel;
	}

}
