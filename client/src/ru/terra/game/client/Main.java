package ru.terra.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import ru.terra.game.client.game.GUIManager;
import ru.terra.game.client.game.GameManager;
import ru.terra.game.client.network.NetworkManager;

public class Main {

	public static void main(String argv[]) throws IOException {
		BasicConfigurator.configure();
		NetworkManager.getInstance().start();
		GameManager.getInstance().start();
		GUIManager.getInstance().start();
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		while (true) {
			line = keyboard.readLine();
			if ("login".equals(line)) {
			} else {
				Logger.getLogger(Main.class).info("sending say : " + line);
				GameManager.getInstance().sendSay(line);
			}
		}
	}
}
