package ru.terra.game.server.game.events;

import org.jboss.netty.channel.Channel;

public class SayEvent extends Event {
    private String message;

    public SayEvent(Channel channel, String message, long sender) {
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
