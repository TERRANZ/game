package ru.terra.game.client.entity;

public interface Entity
{
	public void update(EntityManager manager, int delta);

	public void render();

	public float getSize();

	public float getX();

	public float getY();

	public void onCollide(EntityManager manager, Entity other);

	public boolean isCollides(Entity other);
}
