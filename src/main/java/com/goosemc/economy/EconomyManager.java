package com.goosemc.economy;

import com.goosemc.Prison;
import com.goosemc.utils.Config;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Getter
public class EconomyManager {

    private final Prison prison;
    private UUID uuid;

    public static HashMap<UUID, Double> money = new HashMap<>();
    public static HashMap<UUID, Double> token = new HashMap<>();

    public EconomyManager(Prison prison, UUID uuid) {
        this.prison = prison;
        this.uuid = uuid;
    }

    public EconomyManager(Prison prison) {
        this.prison = prison;
    }

    public void createPlayer(){
        if (this.prison.getEconomyConfiguration().getString("Player." + uuid.toString()) == null) {
            this.prison.getEconomyConfiguration().set("Player." + uuid.toString() + ".money", 0);
            this.prison.getEconomyConfiguration().set("Player." + uuid.toString() + ".token", 0);
            new Config(this.prison.getEconomy(), this.prison.getEconomyConfiguration());
        }
    }

    public void loadEconomy(){
        money.put(uuid, this.prison.getEconomyConfiguration().getDouble("Player." + uuid.toString() + ".money"));
        token.put(uuid, this.prison.getEconomyConfiguration().getDouble("Player." + uuid.toString() + ".token"));
    }

    public void saveEconomy(){
        this.prison.getEconomyConfiguration().set("Player." + uuid.toString() + ".money", money.get(uuid));
        this.prison.getEconomyConfiguration().set("Player." + uuid.toString() + ".token", token.get(uuid));
        new Config(this.prison.getEconomy(), this.prison.getEconomyConfiguration());
    }

    public void saveAllEconomy(){
        for (Player players : Bukkit.getOnlinePlayers()) {
            this.prison.getEconomyConfiguration().set("Player." + players.getUniqueId() + ".money", money.get(players.getUniqueId()));
            this.prison.getEconomyConfiguration().set("Player." + players.getUniqueId() + ".token", token.get(players.getUniqueId()));
            new Config(this.prison.getEconomy(), this.prison.getEconomyConfiguration());
        }
    }

    public void resetAllEconomy(){
        for (String uuid : Objects.requireNonNull(this.prison.getEconomyConfiguration().getConfigurationSection("Player")).getKeys(false)) {
            this.prison.getEconomyConfiguration().set("Player." + uuid + ".money", 0);
            this.prison.getEconomyConfiguration().set("Player." + uuid + ".token", 0);
            new Config(this.prison.getEconomy(), this.prison.getEconomyConfiguration());
        }
    }

    public void resetEconomy(){
        this.prison.getEconomyConfiguration().set("Player." + uuid.toString() + ".money", 0);
        this.prison.getEconomyConfiguration().set("Player." + uuid.toString() + ".token", 0);
        new Config(this.prison.getEconomy(), this.prison.getEconomyConfiguration());
    }

    public double getMoney(UUID uuid){
        return money.get(uuid);
    }

    public double getToken(UUID uuid) {
        return token.get(uuid);
    }

    public void putMoney(UUID uuid, double amount) {
        double current = getMoney(uuid);
        money.replace(uuid, current, (current + amount));
    }

    public void putToken(UUID uuid, double amount) {
        double current = getToken(uuid);
        token.replace(uuid, current, (current + amount));
    }
}
