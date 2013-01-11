package ru.terra.game.server.entity;

import org.jboss.netty.channel.Channel;

import ru.terra.game.shared.entity.PlayerInfo;

public class PlayerEntity extends Entity
{
	private static final long serialVersionUID = -3789713613855111717L;
	private Channel channel;
	private PlayerInfo playerInfo;

	public PlayerEntity(long guid)
	{
		if (guid != -1)
			this.guid = guid;
		playerInfo = new PlayerInfo();
	}

	public Channel getChannel()
	{
		return channel;
	}

	public void setChannel(Channel channel)
	{
		this.channel = channel;
	}

	public PlayerInfo getPlayerInfo()
	{
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo)
	{
		this.playerInfo = playerInfo;
	}

	@Override
	public String getName()
	{
		return playerInfo.getName();
	}

	@Override
	public void setName(String name)
	{
		playerInfo.setName(name);
	}

}
