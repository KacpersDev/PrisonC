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
    public static HashMap<UUID, Long> money = new HashMap<>();
    public static HashMap<UUID, Long> token = new HashMap<>();

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
        money.put(uuid, this.prison.getEconomyConfiguration().getLong("Player." + uuid.toString() + ".money"));
        token.put(uuid, this.prison.getEconomyConfiguration().getLong("Player." + uuid.toString() + ".token"));
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

    public Long getMoney(UUID uuid){
        return money.get(uuid);
    }

    public Long getToken(UUID uuid) {
        return token.get(uuid);
    }

    public void setMoney(UUID uuid, long amount) {
        money.replace(uuid, money.get(uuid), amount);
    }
    public void setToken(UUID uuid, long amount) {
        token.replace(uuid, token.get(uuid), amount);
    }

    public void addMoney(UUID uuid, long amount) {
        money.replace(uuid, money.get(uuid), (money.get(uuid) + amount));
    }

    public void addToken(UUID uuid, long amount) {
        token.replace(uuid, token.get(uuid), (token.get(uuid) + amount));
    }

    public void removeToken(UUID uuid, String amount) {
        long tokens = token.get(uuid);
        long newAmount = (tokens - Integer.parseInt(amount));
        token.replace(uuid, token.get(uuid), (newAmount));
    }

    public void removeMoney(UUID uuid, String amount) {
        long m = money.get(uuid);
        long newAmount = (m - Integer.parseInt(amount));
        money.replace(uuid, money.get(uuid), (newAmount));
    }
}
