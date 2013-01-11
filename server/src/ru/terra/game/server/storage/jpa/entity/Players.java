package ru.terra.game.server.storage.jpa.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the players database table.
 * 
 */
@Entity
@Table(name = "players")
public class Players implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(nullable = false)
	private BigInteger exp;

	@Column(nullable = false)
	private float h;

	@Column(name = "health_curr", nullable = false)
	private int healthCurr;

	@Column(name = "health_max", nullable = false)
	private int healthMax;

	@Column(nullable = false)
	private int level;

	@Column(nullable = false, length = 512)
	private String name;

	@Column(nullable = false)
	private BigInteger uid;

	@Column(nullable = false)
	private float x;

	@Column(nullable = false)
	private float y;

	@Column(nullable = false)
	private float z;

	public Players()
	{
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public BigInteger getExp()
	{
		return this.exp;
	}

	public void setExp(BigInteger exp)
	{
		this.exp = exp;
	}

	public float getH()
	{
		return this.h;
	}

	public void setH(float h)
	{
		this.h = h;
	}

	public int getHealthCurr()
	{
		return this.healthCurr;
	}

	public void setHealthCurr(int healthCurr)
	{
		this.healthCurr = healthCurr;
	}

	public int getHealthMax()
	{
		return this.healthMax;
	}

	public void setHealthMax(int healthMax)
	{
		this.healthMax = healthMax;
	}

	public int getLevel()
	{
		return this.level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public BigInteger getUid()
	{
		return this.uid;
	}

	public void setUid(BigInteger uid)
	{
		this.uid = uid;
	}

	public float getX()
	{
		return this.x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return this.y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return this.z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}

}