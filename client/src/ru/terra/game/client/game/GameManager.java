package ru.terra.game.client.game;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

import ru.terra.game.client.entity.MapObject;
import ru.terra.game.client.entity.Player;
import ru.terra.game.client.network.NetworkManager;
import ru.terra.game.client.network.packet.MovementPacket;
import ru.terra.game.client.network.packet.server.OkPacket;
import ru.terra.game.shared.constants.OpCodes.Client;
import ru.terra.game.shared.entity.PlayerInfo;

public class GameManager {
	private Logger log = Logger.getLogger(GameManager.class);
	private Player player;
	private HashMap<Long, Player> enemies = new HashMap<Long, Player>();
	private HashMap<Long, MapObject> mapObjects = new HashMap<Long, MapObject>();
	private GameState gameState;
	private NetworkManager nm = NetworkManager.getInstance();

	private GameManager() {
		gameState = GameState.INIT;
	}

	private static GameManager instance = new GameManager();

	public static GameManager getInstance() {
		return instance;
	}

	public GameState getGameState() {
		return gameState;
	}

	private void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public void login() {
		if (gameState == GameState.INIT) {
			log.info("login");
			setGameState(GameState.LOGGING_IN);
			nm.sendLogin("TERRANZ" + new Date().getTime());
		}
	}

	public void playerLoggedIn(long guid) {
		log.info("player logged in with guid = " + guid);
		setGameState(GameState.LOGGED_IN);
		player = new Player(guid, "playername");
		setGameState(GameState.READY);
	}

	public void enemyLoggedIn(Player player) {
		log.info("enemy logged in with guid = " + player.getGuid());
		enemies.put(player.getGuid(), player);
		GameView.getView().enemyLoggedIn(player);
	}

	public void enemyAlreadyInGame(Player player) {
		log.info("enemy already in game with guid = " + player.getGuid());
		enemies.put(player.getGuid(), player);
		GameView.getView().enemyLoggedIn(player);
	}

	public void enemyLoggedOut(long guid) {
		log.info("enemy " + guid + " logged out");
		enemies.remove(guid);
	}

	public void start() {
		log.info("Starting game manager...");
	}

	public void sendSay(String message) {
		if (getGameState() == GameState.READY)
			nm.sendSay(message);
	}

	public void playerSaid(long guid, String message) {
		log.info("Player " + guid + " said: " + message);
		GameView.getView().playerSay(enemies.get(guid), message);
	}

	public void ok(OkPacket message) {
		log.info("ok");
		if (getGameState() == GameState.LOGGING_IN) {
			playerLoggedIn(((OkPacket) message).getGuid());
		} else if (getGameState() == GameState.AWAYTING_OK) {
			setGameState(GameState.READY);
		}
	}

	public long getPlayeGuid() {
		return player != null ? player.getGuid() : 0;
	}

	public Player getPlayer() {
		return player;
	}

	public void entityVectorMoving(long guid, float x, float y, float z, float h, boolean stop) {
		Player enemy = enemies.get(guid);
		GameView.getView().entityVectorMove(enemy, x, y, z, h, stop);
	}

	public void setEntityPosition(long guid, float x, float y, float z, float h) {
		Player enemy = enemies.get(guid);
		enemy.setPosition(x, y, z, h);
		GameView.getView().updateEntityPosition(enemy);
	}

	public void sendPlayerMove(int direction, float x, float y, float z, float h) {
		MovementPacket movementPacket = new MovementPacket(direction, getPlayeGuid(), x, y, z, h);
		nm.sendMove(movementPacket);
	}

	public void entityAdd(MapObject mapObject) {
		mapObjects.put(mapObject.getGuid(), mapObject);
		GameView.getView().addMapObject(mapObject);
	}

	public void playerInfoUpdate(Long target, PlayerInfo playerInfo) {
	}

	public void sendPlayerCurrPos(float x, float y, float z, float h) {
		MovementPacket movementPacket = new MovementPacket(Client.CMSG_MOVE_TELEPORT, getPlayeGuid(), x, y, z, h);
		nm.sendMove(movementPacket);
	}

	public void serverSaid(String message) {
		GameView.getView().serverMessage(message);
	}
}
