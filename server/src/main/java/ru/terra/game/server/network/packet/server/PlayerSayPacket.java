package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;

public class PlayerSayPacket extends Packet {

	private String message;

	public PlayerSayPacket(long sender) {
		super(Server.SMSG_SAY, sender);
	}

	public PlayerSayPacket(long sender, String message) {
		super(Server.SMSG_SAY, sender);
		this.setMessage(message);
	}

	@Override
	public void get(ChannelBuffer buffer) {
	}

	@Override
	public void send(ChannelBuffer buffer) {
		buffer.writeShort(message.length());
		for (int i = 0; i < message.length(); ++i) {
			buffer.writeChar(message.charAt(i));
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
