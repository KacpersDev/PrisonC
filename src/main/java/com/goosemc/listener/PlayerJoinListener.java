package com.goosemc.listener;

import com.goosemc.Prison;
import com.goosemc.economy.EconomyManager;
import com.goosemc.pickaxe.Pickaxe;
import com.goosemc.utils.PrisonPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

@Getter
public class PlayerJoinListener implements Listener {
    private final Prison prison;
    private PrisonPlayer prisonPlayer;

    private Pickaxe pickaxe;
    public PlayerJoinListener(Prison prison) {
        this.prison = prison;
    }

    @Deprecated
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.pickaxe = new Pickaxe(this.prison);
        event.setJoinMessage(null);
        event.getPlayer().getActivePotionEffects().clear();
        this.prisonPlayer = new PrisonPlayer(this.prison, event.getPlayer());

        if (!this.prisonPlayer.joined()) {
            this.prisonPlayer.applyJoinItems();
            Location spawnLocation
                    = new Location(Bukkit.getWorld("world"),
                    this.getPrison().getConfiguration().getDouble("spawn.x"),
                    this.getPrison().getConfiguration().getDouble("spawn.z"),
                    this.getPrison().getConfiguration().getDouble("spawn.y"));
            event.getPlayer().teleport(spawnLocation);
        }

        event.getPlayer().addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(999999999,100));
        event.getPlayer().setFoodLevel(20);
        event.getPlayer().setHealth(20);
    }
}
