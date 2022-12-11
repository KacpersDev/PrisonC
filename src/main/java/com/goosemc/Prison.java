package com.goosemc;

import com.goosemc.command.SetSpawnCommand;
import com.goosemc.command.SpawnCommand;
import com.goosemc.gang.command.GangCommandHandler;
import com.goosemc.listener.PlayerJoinListener;
import com.goosemc.scoreboard.ScoreboardListener;
import com.goosemc.utils.Config;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

@Getter
public final class Prison extends JavaPlugin {
    private static Prison instance;
    private final File configg = new File(getDataFolder(), "config.yml");
    private final YamlConfiguration configuration = new YamlConfiguration();
    private final File board = new File(getDataFolder(), "scoreboard.yml");
    private final YamlConfiguration scoreboard = new YamlConfiguration();
    private final File gang = new File(getDataFolder(), "gangs.yml");
    private final FileConfiguration gangConfiguration = new YamlConfiguration();
    @Override
    public void onEnable() {
        instance = this;
        this.configuration();
        this.loadCommand();
        this.loadListeners();
        this.scoreboard();
    }

    public static Prison getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void loadListeners(){
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerJoinListener(this), this);
    }

    private void loadCommand(){
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));
        Objects.requireNonNull(getCommand("gang")).setExecutor(new GangCommandHandler(this));
    }

    private void configuration(){
        new Config(configg, configuration, "config.yml");
        new Config(board, scoreboard, "scoreboard.yml");
        new Config(gang, gangConfiguration, "gangs.yml");
    }

    private void scoreboard(){
        Assemble assemble = new Assemble(this, new ScoreboardListener(this));
        assemble.setTicks(2);
        assemble.setAssembleStyle(AssembleStyle.MODERN);
    }
}
