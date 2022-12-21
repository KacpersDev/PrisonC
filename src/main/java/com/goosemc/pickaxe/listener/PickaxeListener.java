package com.goosemc.pickaxe.listener;

import com.goosemc.Prison;
import com.goosemc.pickaxe.Pickaxe;
import com.goosemc.pickaxe.PickaxeEnchantments;
import com.goosemc.utils.Color;
import com.goosemc.utils.Config;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

@Getter
public class PickaxeListener implements Listener {

    private final Prison prison;
    private final Pickaxe pickaxe;

    public PickaxeListener(Prison prison) {
        this.prison = prison;
        this.pickaxe = new Pickaxe(this.prison);
    }
    @EventHandler
    @Deprecated
    public void onClick(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null
                    || event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null)
                return;
            if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                this.pickaxe.openInventory(event.getPlayer());
            }
        }
    }

    @EventHandler
    @Deprecated
    public void onInventory(InventoryClickEvent event) {

        if (event.getView().getTitle().equalsIgnoreCase(Color.translate("&9Upgrade Inventory"))) {
            event.setCancelled(true);
        }

        for (final String i : this.prison.getConfiguration().getConfigurationSection("pickaxe-items").getKeys(false)) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate(this.prison.getConfiguration().getString("pickaxe-items." + i + ".name")))) {
                if (this.prison.getConfiguration().getString("pickaxe-items." + i + ".enchant").equalsIgnoreCase("speed")) {
                    this.pickaxe.upgradeEnchantments(event.getWhoClicked().getUniqueId(), PickaxeEnchantments.SPEED,
                            this.prison.getConfiguration().getInt("pickaxe-items." + i + ".max"));
                }
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (!event.getPlayer().hasPlayedBefore() || this.prison.getPickaxeConfiguration().getString("Player." + event.getPlayer().getUniqueId()) == null) {
            this.prison.getPickaxeConfiguration().set("Player." + event.getPlayer().getUniqueId() + ".speed", 0);
            this.prison.getPickaxeConfiguration().set("Player." + event.getPlayer().getUniqueId() + ".jump", 0);
            new Config(this.prison.getPickaxe(), this.pickaxe.getPrison().getPickaxeConfiguration());
        }
    }

    @EventHandler
    public void onHold(PlayerMoveEvent event){
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(999999999,
                    this.prison.getConfiguration().getInt("Player." + event.getPlayer().getUniqueId() + ".speed")));
        } else {
            event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
        }
    }
}
