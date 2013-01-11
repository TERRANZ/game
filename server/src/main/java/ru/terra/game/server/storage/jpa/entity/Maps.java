package ru.terra.game.server.storage.jpa.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the maps database table.
 * 
 */
@Entity
@Table(name = "maps")
@NamedQueries({ @NamedQuery(name = "Maps.findByID", query = "SELECT m FROM Maps m where m.mapId =:id") })
public class Maps implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "entity_guid", nullable = false)
	private BigInteger entityGuid;

	@Column(name = "map_id", nullable = false)
	private BigInteger mapId;

	@Column(nullable = false)
	private float x;

	@Column(nullable = false)
	private float y;

	@Column(nullable = false)
	private float z;

	// bi-directional many-to-one association to MapObject
	@OneToMany(mappedBy = "map")
	private List<MapObjectDB> mapObjects;

	public Maps()
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

	public BigInteger getEntityGuid()
	{
		return this.entityGuid;
	}

	public void setEntityGuid(BigInteger entityGuid)
	{
		this.entityGuid = entityGuid;
	}

	public BigInteger getMapId()
	{
		return this.mapId;
	}

	public void setMapId(BigInteger mapId)
	{
		this.mapId = mapId;
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

	public List<MapObjectDB> getMapObjects()
	{
		return this.mapObjects;
	}

	public void setMapObjects(List<MapObjectDB> mapObjects)
	{
		this.mapObjects = mapObjects;
	}

}