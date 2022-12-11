package com.goosemc.gang;

import com.goosemc.Prison;
import com.goosemc.utils.Color;
import com.goosemc.utils.Config;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Gang {

    private final Prison prison;
    private String gangName;
    private UUID leaderUUID;
    private List<UUID> members;
    private List<UUID> captains;
    private double money;
    public Gang(Prison prison, String gangName, UUID leaderUUID, double money, List<UUID> members, List<UUID> captains) {
        this.prison = prison;
        this.gangName = gangName;
        this.leaderUUID = leaderUUID;
        this.money = money;
        this.members = members;
        this.captains = captains;
    }

    public Gang(Prison prison) { this.prison = prison; }

    @Deprecated
    public void create(){
        if (getGangByPlayer(leaderUUID) != null) {
            Objects.requireNonNull(Bukkit.getPlayer(leaderUUID)).sendMessage(Color.translate("&cYou cannot create a gang while being in one."));
            return;
        }

        if (isGang()) {
            Objects.requireNonNull(Bukkit.getPlayer(leaderUUID)).sendMessage(Color.translate("&cGang " + gangName + " already exists"));
            return;
        }

        if (gangName.length() > 16) {
            Objects.requireNonNull(Bukkit.getPlayer(leaderUUID)).sendMessage(Color.translate("&cGang name cannot be longer then 16 characters."));
            return;
        }

        this.prison.getGangConfiguration().set("Gang." + this.gangName, "");
        this.prison.getGangConfiguration().set("Gang." + this.gangName + ".leader", leaderUUID.toString());
        this.prison.getGangConfiguration().set("Gang." + this.gangName + ".money", money);
        this.prison.getGangConfiguration().set("Gang." + this.gangName + ".captains", captains);
        this.prison.getGangConfiguration().set("Gang." + this.gangName + ".members", members);
        new Config(this.prison.getGang(), this.prison.getGangConfiguration());

        Bukkit.broadcastMessage(Color.translate("&3&LGANG &f" + gangName + " has been created by &3&l" + Objects.requireNonNull(Bukkit.getPlayer(leaderUUID)).getName()));
    }

    @Deprecated
    public void remove(){

        if (getGangByPlayer(leaderUUID) == null) {
            Objects.requireNonNull(Bukkit.getPlayer(leaderUUID)).sendMessage(Color.translate("&cYou must be in gang to execute this command"));
            return;
        }

        if (!(Objects.requireNonNull(this.prison.getGangConfiguration().getString("Gang." + this.gangName + ".leader")).equalsIgnoreCase(leaderUUID.toString()))) {
            Objects.requireNonNull(Bukkit.getPlayer(leaderUUID)).sendMessage(Color.translate("&cYou are not the leader of gang " + this.gangName));
        }

        this.prison.getGangConfiguration().set("Gang." + this.gangName, null); // removing gang

        Bukkit.broadcastMessage(Color.translate("&3&LGANG &f" + gangName + " has been removed by &3&l" + Objects.requireNonNull(Bukkit.getPlayer(leaderUUID)).getName()));
    }

    public String getGangByPlayer(UUID player){
        if (this.prison.getGangConfiguration().getConfigurationSection("Gang") == null) return null;
        for (String gang : Objects.requireNonNull(this.prison.getGangConfiguration().getConfigurationSection("Gang")).getKeys(false)) {
            if (Objects.requireNonNull(this.prison.getGangConfiguration().getString("Gang." + gang + ".leader")).equalsIgnoreCase(player.toString())) {
                return gang;
            }

            List<String> captains = this.prison.getGangConfiguration().getStringList("Gang." + gang + "captains");
            for (String uuid : captains) {
                if (uuid.equalsIgnoreCase(player.toString())) {
                    return gang;
                }
            }

            List<String> members = this.prison.getGangConfiguration().getStringList("Gang." + gang + ".members");
            for (String uuid : members) {
                if (uuid.equalsIgnoreCase(player.toString())) {
                    return gang;
                }
            }
        }

        return null;
    }

    public String getGangLeader(String gang) {
        return this.prison.getGangConfiguration().getString("Gang." + gang + ".leader");
    }

    public boolean isGang(){
        return this.prison.getGangConfiguration().getBoolean("Gang." + gangName);
    }

    public List<HashMap<String, String>> getGangMoney(){

        return null;
    }

    public void invite(UUID invited){

    }

    public List<HashMap<String, String>> info(){
        return null;
    }
}
