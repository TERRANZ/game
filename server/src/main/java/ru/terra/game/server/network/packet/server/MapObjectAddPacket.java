package ru.terra.game.server.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import ru.terra.game.server.entity.MapObject;
import ru.terra.game.server.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;

public class MapObjectAddPacket extends Packet
{

	private MapObject mapObject;

	public MapObjectAddPacket(long sender)
	{
		super(Server.SMSG_MAPOBJECT_ADD, sender);
	}

	@Override
	public void get(ChannelBuffer buffer)
	{
	}

	@Override
	public void send(ChannelBuffer buffer)
	{
		buffer.writeShort(mapObject.getName().length());
		for (int i = 0; i < mapObject.getName().length(); ++i)
		{
			buffer.writeChar(mapObject.getName().charAt(i));
		}
		buffer.writeFloat(mapObject.getX());
		buffer.writeFloat(mapObject.getY());
		buffer.writeFloat(mapObject.getZ());
		buffer.writeFloat(mapObject.getH());
		buffer.writeLong(mapObject.getModel());
	}

	public MapObject getMapObject()
	{
		return mapObject;
	}

	public void setMapObject(MapObject mapObject)
	{
		this.mapObject = mapObject;
	}
}
