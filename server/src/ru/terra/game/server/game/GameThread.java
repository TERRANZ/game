package ru.terra.game.server.game;

import org.apache.log4j.Logger;

import ru.terra.game.server.game.events.Event;
import ru.terra.game.server.game.events.LoginEvent;
import ru.terra.game.server.game.events.PlayerMoveEvent;
import ru.terra.game.server.game.events.SayEvent;
import ru.terra.game.server.network.NetworkManager;

public class GameThread implements Runnable
{
	private GameManager gameManager = GameManager.getGameManager();
	private boolean isRun = true;
	private Logger log = Logger.getLogger(GameThread.class);

	public GameThread(GameManager gameManager)
	{
		super();
		this.gameManager = gameManager;
		log.info("Starting game thread...");
	}

	@Override
	public void run()
	{
		long lastLoop = getTime();
		while (isRun)
		{
			int delta = (int) (getTime() - lastLoop);
			lastLoop = getTime();

			gameManager.updateGame(delta);

			Event e = gameManager.getNextEvent();
			if (e == null)
				try
				{
					Thread.sleep(1);
				} catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			else
			{
				if (e instanceof LoginEvent)
				{
					NetworkManager.getInstance().sendLoginOk(e.getChannel(), e.getSender());
				}
				else if (e instanceof SayEvent)
				{
					NetworkManager.getInstance().sendSay(((SayEvent) e).getSender(), ((SayEvent) e).getMessage());
				}
				else if (e instanceof PlayerMoveEvent)
				{
					PlayerMoveEvent event = (PlayerMoveEvent) e;
					NetworkManager.getInstance().sendPlayerMove(event.getSender(), event.getDirection(), event.getX(), event.getY(), event.getZ(),
							event.getH());
				}
			}
		}
	}

	public long getTime()
	{
		return System.currentTimeMillis() & 0x7FFFFFFFFFFFFFFFL;
	}
}
