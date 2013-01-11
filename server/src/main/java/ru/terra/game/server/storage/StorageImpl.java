package ru.terra.game.server.storage;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.PortableServer.POA;

import ru.terra.game.server.entity.MapObject;
import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.GameMap;
import ru.terra.game.server.storage.jpa.controller.MapObjectJpaController;
import ru.terra.game.server.storage.jpa.controller.MapsJpaController;
import ru.terra.game.server.storage.jpa.controller.PlayersJpaController;
import ru.terra.game.server.storage.jpa.controller.exceptions.IllegalOrphanException;
import ru.terra.game.server.storage.jpa.controller.exceptions.NonexistentEntityException;
import ru.terra.game.server.storage.jpa.entity.MapObjectDB;
import ru.terra.game.server.storage.jpa.entity.Maps;
import ru.terra.game.server.storage.jpa.entity.Players;
import ru.terra.game.shared.entity.PlayerInfo;

public class StorageImpl implements Storage
{
	private Logger log = Logger.getLogger(StorageImpl.class);
	private PlayersJpaController pjc = new PlayersJpaController();
	private MapsJpaController mjc = new MapsJpaController();
	private MapObjectJpaController mojc = new MapObjectJpaController();

	public StorageImpl()
	{

	}

	@Override
	public void savePlayer(PlayerEntity player)
	{
		log.info("saving player " + player.getGUID());
		Players p = pjc.findPlayerByGUID(player.getGUID());
		if (p == null)
		{
			p = new Players();
			p.setId(-1);
			p.setUid(BigInteger.valueOf(player.getGUID()));
		}
		p.setExp(BigInteger.valueOf(player.getPlayerInfo().getExp()));
		p.setH(player.getH());
		p.setHealthCurr(player.getPlayerInfo().getHealth_curr());
		p.setHealthMax(player.getPlayerInfo().getHealth_max());
		p.setLevel(player.getPlayerInfo().getLevel());
		p.setName(player.getPlayerInfo().getName());
		p.setX(player.getX());
		p.setY(player.getY());
		p.setZ(player.getZ());
		if (p.getId() == -1)
			pjc.create(p);
		else
			try
			{
				pjc.edit(p);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
	}

	@Override
	public PlayerEntity loadPlayer(long uid)
	{
		log.info("loading player " + uid);
		PlayerEntity ret = new PlayerEntity(uid);
		Players p = pjc.findPlayerByGUID(uid);
		if (p != null)
		{
			ret.setH(p.getH());
			ret.setX(p.getX());
			ret.setY(p.getY());
			ret.setZ(p.getZ());
			PlayerInfo pi = ret.getPlayerInfo();
			pi.setExp(p.getExp().longValue());
			pi.setHealth_curr(p.getHealthCurr());
			pi.setHealth_max(p.getHealthMax());
			pi.setLevel(p.getLevel());
			pi.setName(p.getName());
		}
		else
		{
			return null;
		}
		return ret;
	}

	@Override
	public void saveMap(GameMap map)
	{
		log.info("saving map");
	}

	@Override
	public GameMap loadMap(int id)
	{
		log.info("loading map id = " + id);
		GameMap ret = new GameMap();
		List<Maps> map = mjc.findMapsByMapId(id);
		if (map != null)
		{
			for (Maps m : map)
			{
				for (MapObjectDB modb : m.getMapObjects())
				{
					MapObject mo = new MapObject();
					mo.setH(m.getX());
					mo.setModel(modb.getModel());
					mo.setName(modb.getName());
					mo.setX(m.getY());
					mo.setY(m.getY());
					mo.setZ(m.getZ());
					ret.addObject(mo);
				}
			}
		}
		else
		{
			Maps m = new Maps();
			mjc.create(m);
		}
		return ret;
	}

	@Override
	public void load()
	{
		log.info("starting storage");
	}

	@Override
	public long getPlayerGuidByName(String name)
	{
		log.info("getting uid for name " + name);
		return pjc.getPlayerGuidByName(name);
	}
}
