package ru.terra.game.server.storage;

import org.apache.log4j.Logger;

import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.GameMap;
import ru.terra.game.server.network.NetworkManager;

public class StorageImpl implements Storage
{
	private Logger log = Logger.getLogger(StorageImpl.class);

	@Override
	public void savePlayer(PlayerEntity player)
	{
		log.info("saving player " + player.getGUID());
	}

	@Override
	public PlayerEntity loadPlayer(long uid)
	{
		log.info("loading player " + uid);
		return null;
	}

	@Override
	public void saveMap(GameMap map)
	{
		log.info("saving map");
	}

	@Override
	public GameMap loadMap()
	{
		log.info("loading map");
		return null;
	}

	@Override
	public void load()
	{
		log.info("starting storage");
	}

}
