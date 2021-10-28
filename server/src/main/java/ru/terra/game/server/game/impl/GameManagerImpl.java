package ru.terra.game.server.game.impl;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import ru.terra.game.server.entity.MapObject;
import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.GameManager;
import ru.terra.game.server.game.GameMap;
import ru.terra.game.server.game.GameThread;
import ru.terra.game.server.game.events.*;
import ru.terra.game.server.storage.Storage;
import ru.terra.game.server.storage.StorageManager;
import ru.terra.game.shared.constants.OpCodes.Client;

import java.util.*;

public class GameManagerImpl extends GameManager {
    private static final GameManagerImpl instance = new GameManagerImpl();
    private final LinkedList<Event> events = new LinkedList<>();
    private final List<PlayerEntity> players = Collections.synchronizedList(new ArrayList<PlayerEntity>());
    private final HashMap<Long, PlayerEntity> playersMap = new HashMap<>();
    private final Logger log = Logger.getLogger(GameManagerImpl.class);
    private GameMap gameMap1 = new GameMap();
    private final Storage storage = StorageManager.getStorage();

    private GameManagerImpl() {
    }

    @Override
    public synchronized Event getNextEvent() {
        if (events.size() > 0) {
            Event e = events.getLast();
            events.remove(e);
            return e;
        } else
            return null;
    }

    @Override
    public synchronized void addEvent(Event e) {
        events.addFirst(e);
    }

    public static GameManagerImpl getInstance() {
        return instance;
    }

    @Override
    public void start() {
        log.info("Starting game manager...");
        Thread t = new Thread(new GameThread(getInstance()));
        t.start();
        gameMap1 = storage.loadMap(1);
    }

    @Override
    public long playerLoggedIn(Channel channel, String name) {
        log.info("Player logged in : " + name);
        final Storage storage = StorageManager.getStorage();
        PlayerEntity player = storage.loadPlayer(storage.getPlayerGuidByName(name));
        if (player == null) {
            player = new PlayerEntity(-1);
            player.setName(name);
            storage.savePlayer(player);
        }
        player.setChannel(channel);
        addEvent(new LoginEvent(channel, player));
        players.add(player);
        long guid = player.getGUID();
        playersMap.put(guid, player);
        return guid;
    }

    @Override
    public void playerSaid(Channel channel, String message, long player) {
        log.info("Player said  : " + message);
        if (message.startsWith("/"))
            processAdminCommand(message, player);
        else
            addEvent(new SayEvent(channel, message, player));
    }

    @Override
    public void playerWisp(Channel channel, String message, long sender, long target) {
        log.info("player " + sender + " whisps to " + target + " " + message);
        addEvent(new WispEvent(channel, message, sender, target));
    }

    @Override
    public ArrayList<PlayerEntity> getPlayers() {
        synchronized (players) {
            return new ArrayList<>(players);
        }
    }

    @Override
    public synchronized PlayerEntity getPlayer(long guid) {
        return playersMap.get(guid);
    }

    @Override
    public void removePlayer(long guid) {
        log.info("removing player with guid: " + guid);
        synchronized (playersMap) {
            PlayerEntity pe = playersMap.get(guid);
            playersMap.remove(guid);
            synchronized (players) {
                players.remove(pe);
            }
        }
    }

    @Override
    public void removePlayer(Channel channel) {
        log.info("removing player with channel: " + channel.getId());
        synchronized (players) {
            for (PlayerEntity p : players) {
                if (p.getChannel().equals(channel)) {
                    playersMap.remove(p.getGUID());
                    players.remove(p);
                    break;
                }
            }
        }
    }

    @Override
    public void updateGame(int delta) {

    }

    @Override
    public void updatePlayerPos(Channel channel, long sender, int direction, final float x, final float y, final float z, final float h) {
        // log.info("player =" + sender + " moving x = " + x + " y = " + y +
        // " z = " + z);
        addEvent(new PlayerMoveEvent(channel, sender, direction, x, y, z, h));
        if (direction == Client.CMSG_MOVE_STOP) {
            final PlayerEntity p = getPlayer(sender);
            if (p != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        p.setX(x);
                        p.setY(y);
                        p.setZ(z);
                        p.setH(h);
                        storage.savePlayer(p);
                        log.info("player stopped moving, save its position");
                    }
                }).start();

            }
        }
    }

    private void processAdminCommand(String cmd, long playerId) {
        PlayerEntity player = getPlayer(playerId);
        String[] parsedCommand = cmd.split(" ");
        switch (parsedCommand[0]) {
            case "/addo": {
                if (parsedCommand.length == 3) {
                    MapObject mo = new MapObject();
                    mo.applyCoordinates(player);
                    mo.setModel(Long.parseLong(parsedCommand[1]));
                    mo.setName(parsedCommand[2]);
                    gameMap1.addObject(mo);
                }
            }
            break;
            case "/delo": {
            }
            break;
            case "/help": {
            }
            break;
            case "/say": {
                addEvent(new ServerMessageEvent(null, 0, parsedCommand[1]));
            }
            break;
        }
    }

    @Override
    public void serverSay(String message) {
        log.info("Server saying: " + message);
        addEvent(new ServerMessageEvent(null, 0, message));
    }
}