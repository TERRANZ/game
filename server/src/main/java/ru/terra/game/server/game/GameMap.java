package ru.terra.game.server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.terra.game.server.entity.MapObject;
import ru.terra.game.server.entity.PlayerEntity;

public class GameMap
{
	private HashMap<Long, MapObject> objects = new HashMap<>();
	private ArrayList<PlayerEntity> players = new ArrayList<>();

	public synchronized void loadObjectsToMap(List<MapObject> objects)
	{
		for (MapObject o : objects)
		{
			addObject(o);
		}
	}

	public void addObject(MapObject object)
	{
		objects.put(object.getGUID(), object);
	}

	public void deleteObject(Long guid)
	{
		objects.remove(guid);
	}

	public List<MapObject> getAllObjects()
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
