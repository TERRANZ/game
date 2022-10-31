package ru.terra.game.server.game.events;

import org.jboss.netty.channel.Channel;

public class ServerMessageEvent extends Event {

    private String message;

    public ServerMessageEvent(Channel channel, long sender, String message) {
        super(channel, sender);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
