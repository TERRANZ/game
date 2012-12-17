package ru.terra.game.server.config;

public abstract class Config
{
	public abstract String read(String key, String defvalue);

	public abstract void write(String key, String value);

	public String read(String key)
	{
		return read(key, null);
	}

	public static Config getConfig()
	{
		return null;
	}
}
