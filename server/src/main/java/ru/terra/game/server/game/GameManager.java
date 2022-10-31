package ru.terra.game.server.game;

import org.jboss.netty.channel.Channel;
import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.events.Event;
import ru.terra.game.server.game.impl.GameManagerImpl;

import java.util.ArrayList;

public abstract class GameManager {
    public abstract ArrayList<PlayerEntity> getPlayers();

    public abstract PlayerEntity getPlayer(long guid);

    public abstract void removePlayer(long guid);

    public abstract void removePlayer(Channel channel);

    public abstract long playerLoggedIn(Channel channel, String name);

    public abstract void playerSaid(Channel channel, String message, long player);

    public abstract void playerWisp(Channel channel, String message, long sender, long target);

    public static GameManager getGameManager() {
        return GameManagerImpl.getInstance();
    }

    public abstract void start();

    public abstract Event getNextEvent();

    public abstract void addEvent(Event e);

    public abstract void updateGame(int delta);

    public abstract void updatePlayerPos(Channel channel, long sender, int direction, float x, float y, float z, float h);

    public abstract void serverSay(String message);
}
