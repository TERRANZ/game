package ru.terra.game.server.game.events;

import org.jboss.netty.channel.Channel;

public class WispEvent extends Event
{
	public long target;
	public String message;

	public WispEvent(Channel channel, String message, long sender, long target)
	{
		super(channel, sender);
		this.target = target;
		this.message = message;
	}

}
