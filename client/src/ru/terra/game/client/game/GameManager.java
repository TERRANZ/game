package ru.terra.game.client.game;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import ru.terra.game.client.entity.Player;
import ru.terra.game.client.network.NetworkManager;
import ru.terra.game.client.network.packet.server.OkPacket;

public class GameManager
{
	private Logger log = Logger.getLogger(GameManager.class);
	private Player player;

	private ArrayList<Player> enemies = new ArrayList<>();

	private GameState gameState;

	private GameManager()
	{
		gameState = GameState.INIT;
	}

	private static GameManager instance = new GameManager();

	public static GameManager getInstance()
	{
		return instance;
	}

	public GameState getGameState()
	{
		return gameState;
	}

	private void setGameState(GameState gameState)
	{
		this.gameState = gameState;
	}

	public void login()
	{
		log.info("login");
		setGameState(GameState.LOGGING_IN);
		NetworkManager.getInstance().sendLogin("TERRANZ");
	}

	public void playerLoggedIn(long guid)
	{
		log.info("player logged in with guid = " + guid);
		setGameState(GameState.LOGGED_IN);
		player = new Player(guid);
		setGameState(GameState.READY);
	}

	public void enemyLoggedIn(long guid, String name)
	{
		log.info("enemy logged in with guid = " + guid);
	}

	public void start()
	{
		log.info("Starting game manager...");
	}

	public void sendSay(String message)
	{
		NetworkManager.getInstance().sendSay(message);
	}

	public void playerSaid(long guid, String message)
	{
		log.info("Player " + guid + " said: " + message);
	}

	public void ok(OkPacket message)
	{
		log.info("ok");
		if (getGameState() == GameState.LOGGING_IN)
		{
			playerLoggedIn(((OkPacket) message).getGuid());
		}
		else if (getGameState() == GameState.AWAYTING_OK)
		{
			setGameState(GameState.READY);
		}
	}

	public long getPlayeGuid()
	{
		return player != null ? player.getGuid() : 0;
	}

}
