package ru.terra.game.server.game.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.GameManager;
import ru.terra.game.server.game.GameMap;
import ru.terra.game.server.game.GameThread;
import ru.terra.game.server.game.events.Event;
import ru.terra.game.server.game.events.LoginEvent;
import ru.terra.game.server.game.events.PlayerMoveEvent;
import ru.terra.game.server.game.events.SayEvent;
import ru.terra.game.server.game.events.WispEvent;

public class GameManagerImpl extends GameManager
{
	private static GameManagerImpl instance = new GameManagerImpl();
	private LinkedList<Event> events = new LinkedList<>();
	private ArrayList<PlayerEntity> players = new ArrayList<>();
	private HashMap<Long, PlayerEntity> playersMap = new HashMap<>();
	private Logger log = Logger.getLogger(GameManagerImpl.class);
	private GameMap gameMap1 = new GameMap();

	private GameManagerImpl()
	{
	}

	@Override
	public synchronized Event getNextEvent()
	{
		if (events.size() > 0)
		{
			Event e = events.getLast();
			events.remove(e);
			return e;
		}
		else
			return null;
	}

	@Override
	public synchronized void addEvent(Event e)
	{
		events.addFirst(e);
	}

	public static GameManagerImpl getInstance()
	{
		return instance;
	}

	@Override
	public void start()
	{
		log.info("Starting game manager...");
		Thread t = new Thread(new GameThread(getInstance()));
		t.start();
	}

	@Override
	public long playerLoggedIn(Channel channel, String name)
	{
		log.info("Player logged in : " + name);
		PlayerEntity player = new PlayerEntity();
		player.setName(name);
		player.setChannel(channel);
		addEvent(new LoginEvent(channel, name, player.getGUID()));
		players.add(player);
		long guid = player.getGUID();
		playersMap.put(guid, player);
		return guid;
	}

	@Override
	public void playerSaid(Channel channel, String message, long player)
	{
		log.info("Player said  : " + message);
		addEvent(new SayEvent(channel, message, player));
	}

	@Override
	public void playerWisp(Channel channel, String message, long sender, long target)
	{
		log.info("player " + sender + " whisps to " + target + " " + message);
		addEvent(new WispEvent(channel, message, sender, target));
	}

	@Override
	public ArrayList getPlayers()
	{
		synchronized (players)
		{
			return new ArrayList<PlayerEntity>(players);
		}
	}

	@Override
	public PlayerEntity getPlayer(long guid)
	{
		return playersMap.get(guid);
	}

	@Override
	public void removePlayer(long guid)
	{
		log.info("removing player with guid: " + guid);
		PlayerEntity pe = playersMap.get(guid);
		playersMap.remove(guid);
		players.remove(pe);
	}

	@Override
	public void removePlayer(Channel channel)
	{
		log.info("removing player with channel: " + channel.getId());
		for (PlayerEntity p : players)
		{
			if (p.getChannel().equals(channel))
			{
				playersMap.remove(p.getGUID());
				players.remove(p);
			}
		}
	}

	@Override
	public void updateGame(int delta)
	{

	}

	@Override
	public void updatePlayerPos(Channel channel, long sender, int direction, float x, float y, float z, float h)
	{
		PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(channel, sender, direction, x, y, z, h);
		addEvent(playerMoveEvent);
	}
}