package ru.terra.game.client.gui.jmonkey;

import ru.terra.game.client.entity.Entity;
import ru.terra.game.client.game.GameView;

public class JMonkeyGameView extends GameView
{
	@Override
	public void init()
	{
		JMEGameViewImpl jmeGameViewImpl = new JMEGameViewImpl();
		jmeGameViewImpl.start();
	}

	@Override
	public void loadPlayer()
	{
	}

	@Override
	public void loadObject(Entity entity)
	{
	}
}
