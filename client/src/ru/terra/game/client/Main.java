package ru.terra.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.BasicConfigurator;

import ru.terra.game.client.game.GameManager;
import ru.terra.game.client.network.NetworkManager;

public class Main
{

	public static void main(String argv[]) throws IOException
	{
		// GameWindow g = new GameWindow();
		// g.startGame();
		BasicConfigurator.configure();
		String line = "";
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		NetworkManager.getInstance().start();
		GameManager.getInstance().start();
		while (true)
		{
			line = keyboard.readLine();
			if ("login".equals(line))
			{
				GameManager.getInstance().login();
				// NetworkManager.getInstance().sendLogin("my super name");
			}
			else
			{
				GameManager.getInstance().sendSay(line);
			}
		}
	}
}
