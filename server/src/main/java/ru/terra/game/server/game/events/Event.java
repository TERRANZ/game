package ru.terra.game.server.game.events;

import org.jboss.netty.channel.Channel;

public abstract class Event {
    private long sender = 0;
    private Channel channel;

    public Event(Channel channel, long sender) {
        this.sender = sender;
        this.channel = channel;
    }

    public long getSender() {
        return sender;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
