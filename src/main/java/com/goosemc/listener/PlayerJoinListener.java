package com.goosemc.listener;

import com.goosemc.Prison;
import com.goosemc.utils.PrisonPlayer;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

@Getter
public class PlayerJoinListener implements Listener {
    private final Prison prison;
    private PrisonPlayer prisonPlayer;
    public PlayerJoinListener(Prison prison) {
        this.prison = prison;
    }

    @Deprecated
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.prisonPlayer = new PrisonPlayer(this.prison, event.getPlayer());

        if (!this.prisonPlayer.joined()) {
            this.prisonPlayer.applyJoinItems();
        }
        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(9999999,1));
        event.getPlayer().addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(999999999,100));
        event.getPlayer().setFoodLevel(20);
        event.getPlayer().setHealth(20);
    }
}
