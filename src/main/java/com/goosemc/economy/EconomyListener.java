package com.goosemc.economy;

import com.goosemc.Prison;
import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Getter
public class EconomyListener implements Listener {

    private final Prison prison;
    private EconomyManager manager;

    public EconomyListener(Prison prison) {
        this.prison = prison;
    }
    @EventHandler
    @Deprecated
    public void onJoin(PlayerJoinEvent event){
        this.manager = new EconomyManager(this.prison, event.getPlayer().getUniqueId());
        this.manager.createPlayer();
        this.manager.loadEconomy();
        Bukkit.getConsoleSender().sendMessage(Color.translate("&cLOADED " + event.getPlayer().getName() + " ECONOMY"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.manager = new EconomyManager(this.prison, event.getPlayer().getUniqueId());
        this.manager.saveEconomy();
    }
}
