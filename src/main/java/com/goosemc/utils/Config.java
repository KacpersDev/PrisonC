package com.goosemc.utils;

import com.goosemc.Prison;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    @SuppressWarnings("ALL")
    public Config(File file, FileConfiguration configuration, String dir) {
        if (!file.exists()) {
            file.getParentFile().mkdir();
            Prison.getInstance().saveResource(dir, false);
        }

        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    // RELOAD
    public Config(File file, FileConfiguration configuration) {
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
