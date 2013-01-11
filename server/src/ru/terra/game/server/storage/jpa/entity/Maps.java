package ru.terra.game.server.storage.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the maps database table.
 * 
 */
@Entity
@Table(name="maps")
public class Maps implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="entity_guid", nullable=false)
	private BigInteger entityGuid;

	@Column(name="map_id", nullable=false)
	private BigInteger mapId;

	@Column(nullable=false)
	private float x;

	@Column(nullable=false)
	private float y;

	@Column(nullable=false)
	private float z;

	//bi-directional many-to-one association to MapObject
	@OneToMany(mappedBy="map")
	private List<MapObject> mapObjects;

	public Maps() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getEntityGuid() {
		return this.entityGuid;
	}

	public void setEntityGuid(BigInteger entityGuid) {
		this.entityGuid = entityGuid;
	}

	public BigInteger getMapId() {
		return this.mapId;
	}

	public void setMapId(BigInteger mapId) {
		this.mapId = mapId;
	}

	public float getX() {
		return this.x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return this.z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public List<MapObject> getMapObjects() {
		return this.mapObjects;
	}

	public void setMapObjects(List<MapObject> mapObjects) {
		this.mapObjects = mapObjects;
	}

}