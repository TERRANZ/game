package ru.terra.game.server.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable
{
	private static final long serialVersionUID = -2109883228832245234L;
	private long guid = UUID.randomUUID().getLeastSignificantBits();
	private float health = 100f;
	private String name = "";
	private float x = 0f;
	private float y = 0f;
	private float z = 0f;
	private float h = 0f;

	public long getGUID()
	{
		return guid;
	}

	public float getHealth()
	{
		return health;
	}

	public void setHealth(float health)
	{
		this.health = health;
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