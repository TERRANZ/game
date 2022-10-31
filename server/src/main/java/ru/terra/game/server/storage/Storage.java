package ru.terra.game.server.storage;

import ru.terra.game.server.entity.PlayerEntity;
import ru.terra.game.server.game.GameMap;

public interface Storage {
    void load();

    void savePlayer(PlayerEntity player);

    PlayerEntity loadPlayer(long uid);

    void saveMap(GameMap map);

    GameMap loadMap(int id);

    long getPlayerGuidByName(String name);
}
