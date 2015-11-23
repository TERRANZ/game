package ru.terra.game.server.game.events;

import org.jboss.netty.channel.Channel;

import ru.terra.game.server.entity.PlayerEntity;

public class LoginEvent extends Event
{

	private PlayerEntity playerEntity;

	public LoginEvent(Channel channel, PlayerEntity playerEntity)
	{
		super(channel, playerEntity.getGUID());
		this.playerEntity = playerEntity;
	}

	public PlayerEntity getPlayerEntity()
	{
		return playerEntity;
	}

	public void setPlayerEntity(PlayerEntity playerEntity)
	{
		this.playerEntity = playerEntity;
	}
}
