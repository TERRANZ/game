package ru.terra.game.server.game.events;

import org.jboss.netty.channel.Channel;

public class LoginEvent extends Event
{
	private String name;

	public LoginEvent(Channel channel, String name, long sender)
	{
		super(channel, sender);
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
