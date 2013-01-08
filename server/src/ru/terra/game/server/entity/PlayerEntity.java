package ru.terra.game.server.entity;

import org.jboss.netty.channel.Channel;

public class PlayerEntity extends Entity
{
	private static final long serialVersionUID = -3789713613855111717L;
	private Channel channel;

	public PlayerEntity(long guid)
	{
		if (guid != -1)
			this.guid = guid;
	}

	public Channel getChannel()
	{
		return channel;
	}

	public void setChannel(Channel channel)
	{
		this.channel = channel;
	}

}
