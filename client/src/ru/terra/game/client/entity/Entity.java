package ru.terra.game.client.entity;

import ru.terra.game.shared.entity.EntityCoordinates;

public abstract class Entity extends EntityCoordinates
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

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}

	public float getH()
	{
		return h;
	}

	public void setH(float h)
	{
		this.h = h;
	}

	public void setPosition(float x, float y, float z, float h)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
	}
}
