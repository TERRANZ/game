package ru.terra.game.server.storage.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the map_objects database table.
 * 
 */
@Entity
@Table(name = "map_objects")
public class MapObjectDB implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(nullable = false)
	private int model;

	@Column(nullable = false, length = 512)
	private String name;

	// bi-directional many-to-one association to Maps
	@ManyToOne
	@JoinColumn(name = "uid", nullable = false)
	private Maps map;

	public MapObjectDB()
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

	public int getModel()
	{
		return this.model;
	}

	public void setModel(int model)
	{
		this.model = model;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Maps getMap()
	{
		return this.map;
	}

	public void setMap(Maps map)
	{
		this.map = map;
	}

}