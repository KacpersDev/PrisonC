package com.goosemc.pickaxe.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MineListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent event) {

        if (event.getPlayer().getLocation().getWorld().toString().equalsIgnoreCase("world")) {
            event.setDropItems(false);
        }
    }
}
