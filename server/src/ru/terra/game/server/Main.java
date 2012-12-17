package ru.terra.game.server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import ru.terra.game.server.network.NetworkManager;

public class Main
{
	public static void main(String[] args)
	{
		BasicConfigurator.configure();
		Logger.getLogger(Main.class).info("Starting server...");
		NetworkManager.getInstance().start();
	}
}
