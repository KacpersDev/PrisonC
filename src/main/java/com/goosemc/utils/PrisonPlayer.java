package com.goosemc.utils;

import com.goosemc.Prison;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@Getter
public class PrisonPlayer {

    private final Prison prison;
    private final Player player;
    public PrisonPlayer(Prison prison, Player player) {
        this.prison = prison;
        this.player = player;
    }

    public boolean joined() { return player.hasPlayedBefore(); }

    @Deprecated
    @SuppressWarnings("ALL")
    public void applyJoinItems(){
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setDisplayName(Color.translate(this.prison.getConfiguration().getString("pickaxe-name")
                .replace("%player%", player.getName())));
        pickaxe.setItemMeta(meta);
        pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(org.bukkit.Color.AQUA);
        helmet.setItemMeta(helmetMeta);

        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(org.bukkit.Color.AQUA);
        chestplate.setItemMeta(chestplateMeta);

        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(org.bukkit.Color.AQUA);
        leggings.setItemMeta(leggingsMeta);

        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(org.bukkit.Color.AQUA);
        boots.setItemMeta(bootsMeta);

        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItemInMainHand(pickaxe);
    }
}
