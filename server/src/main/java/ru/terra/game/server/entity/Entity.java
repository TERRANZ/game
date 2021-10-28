package ru.terra.game.server.entity;

import ru.terra.game.shared.entity.EntityCoordinates;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity extends EntityCoordinates implements Serializable {
    private static final long serialVersionUID = -2109883228832245234L;
    protected long guid = UUID.randomUUID().getLeastSignificantBits();
    private String name = "";

    public long getGUID() {
        return guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}