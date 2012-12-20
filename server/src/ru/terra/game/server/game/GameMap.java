package ru.terra.game.server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.terra.game.server.entity.MapObjectEntity;
import ru.terra.game.server.entity.PlayerEntity;

public class GameMap
{
	private HashMap<Long, MapObjectEntity> objects = new HashMap<>();
	private ArrayList<PlayerEntity> players = new ArrayList<>();

	public synchronized void loadObjectsToMap(List<MapObjectEntity> objects)
	{
		for (MapObjectEntity o : objects)
		{
			addObject(o);
		}
	}

	public void addObject(MapObjectEntity object)
	{
		objects.put(object.getGUID(), object);
	}

	public void deleteObject(Long guid)
	{
		objects.remove(guid);
	}

	public List<MapObjectEntity> getAllObjects()
	{
		return new ArrayList<>(objects.values());
	}

	public void addPlayer(PlayerEntity player)
	{
		players.add(player);
	}

	public void removePlayer(PlayerEntity player)
	{
		players.remove(player);
	}
}
