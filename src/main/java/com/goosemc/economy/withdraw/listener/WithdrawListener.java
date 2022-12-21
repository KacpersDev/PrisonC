package com.goosemc.economy.withdraw.listener;

import com.goosemc.Prison;
import com.goosemc.economy.EconomyManager;
import com.goosemc.economy.withdraw.Withdraw;
import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class WithdrawListener implements Listener {

    private final Prison prison;

    public WithdrawListener(Prison prison) {
        this.prison = prison;
    }

    @EventHandler
    @Deprecated
    @SuppressWarnings("ALL")
    public void onRedeem(PlayerInteractEvent event) {

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item == null || item.getType() == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) return;
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate("&6&lTokens"))) {
                if (item.containsEnchantment(Enchantment.DURABILITY)) {
                    if (item.getItemMeta().getLore() == null) return;
                    List<String> lore = item.getItemMeta().getLore();
                    long amount = Long.parseLong(lore.get(3));
                    validate(event.getPlayer(), amount);
                    EconomyManager.token.replace(event.getPlayer().getUniqueId(), (EconomyManager.token.get(event.getPlayer().getUniqueId())), (EconomyManager.token.get(event.getPlayer().getUniqueId()) + amount));
                    remove(event.getPlayer());
                }
            }
        }
    }

    @Deprecated
    void validate(Player player, long amount){
        long MAX = Long.parseLong("999099999999999927");
        if (EconomyManager.token.get(player.getUniqueId()) + amount > MAX) {
            long value = (MAX - amount);
            new Withdraw(this.prison, player.getUniqueId()).allow(value);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "token set " + player.getName() + " 999099999999999927");
        } else if (EconomyManager.token.get(player.getUniqueId()) == MAX) {
            player.sendMessage(Color.translate("&cYou have reached max tokens"));
        }
    }

    void remove(Player player) {
        if (player.getInventory().getItemInMainHand().getAmount() > 1) {
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
    }
}
