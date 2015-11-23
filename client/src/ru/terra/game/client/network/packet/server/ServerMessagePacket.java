package ru.terra.game.client.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes;

public class ServerMessagePacket extends Packet {

	private String message;

	public ServerMessagePacket() {
		super(OpCodes.Server.SMSG_MESSAGE, 0);
	}

	@Override
	public void get(ChannelBuffer buffer) {
		int length = buffer.readShort();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; ++i)
			builder.append(buffer.readChar());
		message = builder.toString();
	}

	@Override
	public void send(ChannelBuffer buffer) {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
