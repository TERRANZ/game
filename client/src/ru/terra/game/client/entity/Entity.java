package ru.terra.game.client.entity;

public abstract class Entity
{
	private String name;
	private long guid;

	public Entity(long guid, String name)
	{
		this.guid = guid;
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

	public long getGuid()
	{
		return guid;
	}

	public void setGuid(long guid)
	{
		this.guid = guid;
	}
}
