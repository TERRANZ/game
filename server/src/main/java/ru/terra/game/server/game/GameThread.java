package ru.terra.game.server.game;

import org.apache.log4j.Logger;
import ru.terra.game.server.game.events.*;
import ru.terra.game.server.network.NetworkManager;

public class GameThread implements Runnable {
    private GameManager gameManager = GameManager.getGameManager();
    private final boolean isRun = true;
    private final Logger log = Logger.getLogger(GameThread.class);
    private NetworkManager instance;

    public GameThread(GameManager gameManager) {
        super();
        this.gameManager = gameManager;
        log.info("Starting game thread...");
    }

    @Override
    public void run() {
        long lastLoop = getTime();
        while (isRun) {
            int delta = (int) (getTime() - lastLoop);
            lastLoop = getTime();

            gameManager.updateGame(delta);

            Event e = gameManager.getNextEvent();
            if (e == null)
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            else {
                instance = NetworkManager.getInstance();
                if (e instanceof LoginEvent) {
                    instance.sendLoginOk(e.getChannel(), ((LoginEvent) e).getPlayerEntity());
                } else if (e instanceof SayEvent) {
                    NetworkManager.getInstance().sendSay(e.getSender(), ((SayEvent) e).getMessage());
                } else if (e instanceof PlayerMoveEvent) {
                    PlayerMoveEvent event = (PlayerMoveEvent) e;
                    instance.sendPlayerMove(event.getSender(), event.getDirection(), event.getX(), event.getY(), event.getZ(),
                            event.getH());
                } else if (e instanceof ServerMessageEvent) {
                    ServerMessageEvent event = (ServerMessageEvent) e;
                    instance.sendServerMessage(event.getMessage());
                }
            }
        }
    }

    public long getTime() {
        return System.currentTimeMillis() & 0x7FFFFFFFFFFFFFFFL;
    }
}
