package ru.terra.game.server.entity;

import java.io.Serializable;
import java.util.UUID;

import ru.terra.game.shared.entity.EntityCoordinates;

public abstract class Entity extends EntityCoordinates implements Serializable 
{
	private static final long serialVersionUID = -2109883228832245234L;
	protected long guid = UUID.randomUUID().getLeastSignificantBits();
	private String name = "";


	public long getGUID()
	{
		return guid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
}