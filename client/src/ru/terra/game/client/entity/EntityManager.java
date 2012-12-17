package ru.terra.game.client.entity;

public interface EntityManager
{
	public void removeEntity(Entity entity);

	public void addEntity(Entity entity);

	public void shotFired();
}
