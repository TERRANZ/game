package ru.terra.game.server.storage;

public class StorageManager
{
	private Storage storage = new StorageImpl();

	private static StorageManager instance = new StorageManager();

	private StorageManager()
	{
	}

	public static Storage getStorage()
	{
		return instance.storage;
	}

}
