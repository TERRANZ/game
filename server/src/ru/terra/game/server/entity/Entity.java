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
	private int rotation = 0;

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

	public int getRotation()
	{
		return rotation;
	}

	public void setRotation(int rotation)
	{
		this.rotation = rotation;
	}
}