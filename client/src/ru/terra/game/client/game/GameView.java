package ru.terra.game.client.game;

import ru.terra.game.client.entity.Entity;
import ru.terra.game.client.gui.LWJGLGameViewImpl;

public abstract class GameView
{
	public abstract void init();

	public abstract void loadPlayer();

	public abstract void loadObject(Entity entity);

	public static GameView getView()
	{
		return new LWJGLGameViewImpl();
	}
}
