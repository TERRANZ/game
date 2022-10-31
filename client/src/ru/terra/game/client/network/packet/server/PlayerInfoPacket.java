package ru.terra.game.client.network.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;
import ru.terra.game.client.network.packet.Packet;
import ru.terra.game.shared.constants.OpCodes.Server;
import ru.terra.game.shared.entity.PlayerInfo;

public class PlayerInfoPacket extends Packet {

    private final PlayerInfo playerInfo;

    public PlayerInfoPacket(long sender) {
        super(Server.SMSG_PLAYER_INFO, sender);
        playerInfo = new PlayerInfo();
    }

    @Override
    public void get(ChannelBuffer buffer) {
        playerInfo.setName(readString(buffer));
        playerInfo.setHealth_curr(buffer.readInt());
        playerInfo.setHealth_max(buffer.readInt());
        playerInfo.setLevel(buffer.readInt());
        playerInfo.setExp(buffer.readLong());
    }

    @Override
    public void send(ChannelBuffer buffer) {
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}
