package ru.terra.game.shared.entity;

import java.io.Serializable;

public class PlayerInfo implements Serializable
{

	private static final long serialVersionUID = 3096487093002673707L;

	private String name;
	private int health_curr, health_max;
	private int level;
	private long exp;

	public PlayerInfo()
	{
		name = "unnamed";
		health_curr = 100;
		health_max = 100;
		exp = 0;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getHealth_curr()
	{
		return health_curr;
	}

	public void setHealth_curr(int health_curr)
	{
		this.health_curr = health_curr;
	}

	public int getHealth_max()
	{
		return health_max;
	}

	public void setHealth_max(int health_max)
	{
		this.health_max = health_max;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public long getExp()
	{
		return exp;
	}

	public void setExp(long exp)
	{
		this.exp = exp;
	}
}
