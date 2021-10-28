package ru.terra.game.server.storage;

public class StorageManager {
    private final Storage storage = new StorageImpl();

    private static final StorageManager instance = new StorageManager();

    private StorageManager() {
    }

    public static Storage getStorage() {
        return instance.storage;
    }

}
