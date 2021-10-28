package ru.terra.game.server.config.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import ru.terra.game.server.config.Config;
import ru.terra.game.server.storage.StorageImpl;

public class ApacheConfigImpl extends Config {
    private final Logger log = Logger.getLogger(StorageImpl.class);
    private Configuration configuration;

    private ApacheConfigImpl() {
        log.info("Starting configuration manager...");
        try {
            configuration = new PropertiesConfiguration("server.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static final Config instance = new ApacheConfigImpl();

    public static Config getInstance() {
        return instance;
    }

    @Override
    public String read(String key, String defvalue) {
        log.info("reading config " + key + " defval = " + defvalue);
        if (configuration == null)
            return null;
        else {
            return configuration.getString(key, defvalue);
        }
    }

    @Override
    public void write(String key, String value) {
        log.info("write config " + key + " = " + value);
        if (configuration != null)
            configuration.setProperty(key, value);
    }
}
