package ru.terra.game.server.game.events;

import org.jboss.netty.channel.Channel;

public class PlayerMoveEvent extends Event
{

	private float x;
	private float h;
	private float y;
	private float z;
	private int direction;

	public PlayerMoveEvent(Channel channel, long sender, int direction, float x, float y, float z, float h)
	{
		super(channel, sender);
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getH()
	{
		return h;
	}

	public void setH(float h)
	{
		this.h = h;
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

	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int direction)
	{
		this.direction = direction;
	}
}
