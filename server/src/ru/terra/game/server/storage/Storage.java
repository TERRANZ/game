package ru.terra.game.server.storage;

import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.GameMap;

public interface Storage
{
	public void load();

	public void savePlayer(PlayerEntity player);

	public PlayerEntity loadPlayer(long uid);

	public void saveMap(GameMap map);

	public GameMap loadMap();

	public long getGuidByName(String name);
}
