package com.goosemc.pickaxe;

import com.goosemc.Prison;
import com.goosemc.economy.EconomyManager;
import com.goosemc.utils.Color;
import com.goosemc.utils.Config;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public class Pickaxe {

    private final Prison prison;
    private Inventory inventory;

    public Pickaxe(Prison prison) {
        this.prison = prison;
    }
    @Deprecated
    public void openInventory(Player player){
        inventory = Bukkit.createInventory(player, 54, Color.translate("&9Upgrade Inventory"));
        init(player);
        player.openInventory(inventory);
    }

    @Deprecated
    public void init(Player player){
        for (final String items : this.prison.getConfiguration().getConfigurationSection("pickaxe-items").getKeys(false)) {
            ItemStack item = new ItemStack(Material.valueOf(this.prison.getConfiguration().getString("pickaxe-items." + items + ".item")));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Color.translate(this.prison.getConfiguration().getString("pickaxe-items." + items + ".name")));
            ArrayList<String> lore = new ArrayList<>();

            String SPEED = level(PickaxeEnchantments.SPEED, player.getUniqueId());
            String SPEED_PRICE = calculatePrice(PickaxeEnchantments.SPEED, player.getUniqueId());
            String JUMP = level(PickaxeEnchantments.SPEED, player.getUniqueId());
            String JUMP_PRICE = calculatePrice(PickaxeEnchantments.SPEED, player.getUniqueId());

            for (final String i : this.prison.getConfiguration().getStringList("pickaxe-items." + items + ".lore")) {
                lore.add(Color.translate(i)
                        .replace("%SPEED_LEVEL%", SPEED)
                        .replace("%SPEED_PRICE%", SPEED_PRICE)
                        .replace("%JUMP_LEVEL%", JUMP)
                        .replace("%JUMP_PRICE%", JUMP_PRICE));
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

            inventory.setItem(this.prison.getConfiguration().getInt("pickaxe-items." + items + ".slot"), item);
        }
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, new ItemStack(Material.CYAN_STAINED_GLASS_PANE));
            }
        }
    }

    public String level(PickaxeEnchantments enchantments, UUID uuid){
        int current;
        if (enchantments.equals(PickaxeEnchantments.SPEED)) {
            current = this.prison.getPickaxeConfiguration().getInt("Player." + uuid + ".speed");
            if (current == this.prison.getConfiguration().getInt("pickaxe-items.1.max")) {
                return "MAXED";
            } else {
                return String.valueOf(current);
            }
        }
        return null;
    }

    @Deprecated
    public String calculatePrice(PickaxeEnchantments enchantments, UUID uuid){
        int current;
        if (enchantments.equals(PickaxeEnchantments.SPEED)) {
            if (!level(PickaxeEnchantments.SPEED, uuid).equalsIgnoreCase("maxed")) {
                current = this.prison.getPickaxeConfiguration().getInt("Player." + uuid + ".speed");
                if (current == 0) {
                    current = 1;
                }
                int first = this.prison.getConfiguration().getInt("pickaxe-items.1.level");
                int each = this.prison.getConfiguration().getInt("pickaxe-items.1.each");
                int price = ((current * first) * each);
                return String.valueOf(price);
            } else {
                return "MAXED";
            }
        }
        return null;
    }

    public void upgradeEnchantments(UUID uuid, PickaxeEnchantments enchantments, int max){
        if (enchantments.equals(PickaxeEnchantments.SPEED)) {
            int level = this.prison.getPickaxeConfiguration().getInt("Player." + uuid + ".speed");
            if (level == max) return;
            int price = Integer.parseInt(calculatePrice(PickaxeEnchantments.SPEED, uuid));
            if (EconomyManager.token.get(uuid) < price) {
                Bukkit.getPlayer(uuid).sendMessage(Color.translate("&cYou don't have enough tokens."));
                return;
            } else {
                EconomyManager.token.replace(uuid, EconomyManager.token.get(uuid), (EconomyManager.token.get(uuid) - price));
            }
            this.prison.getPickaxeConfiguration().set("Player." + uuid + ".speed", (level + 1));
            Bukkit.getPlayer(uuid).sendMessage(Color.translate("&aYou have bought an upgrade."));
            new Config(this.prison.getPickaxe(), this.prison.getPickaxeConfiguration());
        }
    }
}
