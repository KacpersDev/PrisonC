package com.goosemc.staff.listener;

import com.goosemc.staff.command.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class VanishJoinListener implements Listener {

    @EventHandler
    @Deprecated
    public void onJoin(PlayerJoinEvent event){

        if (!event.getPlayer().hasPermission("vanish.bypass")) {
            for (UUID vanish : VanishCommand.vanished) {
                event.getPlayer().hidePlayer(Bukkit.getPlayer(vanish));
            }
        }
    }
}
