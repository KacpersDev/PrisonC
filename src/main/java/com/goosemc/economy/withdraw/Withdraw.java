package com.goosemc.economy.withdraw;

import com.goosemc.Prison;
import com.goosemc.economy.EconomyManager;
import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Withdraw {

    //MAX - 999099999999999927
    private final Prison prison;
    private final UUID uuid;

    public Withdraw(Prison prison, UUID uuid) {
        this.prison = prison;
        this.uuid = uuid;
    }

    @Deprecated
    @SuppressWarnings(value = "ALL")
    public void allow(long amount){
        ItemStack item = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        meta.setDisplayName(Color.translate("&6&lTokens"));
        lore.add(Color.translate(""));
        lore.add(Color.translate("&9&lInformation"));
        lore.add(Color.translate("&f* &9&lTokens:"));
        lore.add(String.valueOf(amount));
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        Bukkit.getPlayer(uuid).getInventory().addItem(item);
        long value = ((EconomyManager.token.get(uuid)) - amount);
        EconomyManager.token.replace(uuid, EconomyManager.token.get(uuid), (value));
    }

    public void disAllow(){
        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Color.translate("&cFailed to withdraw."));
    }

    @Deprecated
    public boolean check(long withdraw){
        long amount = (EconomyManager.token.get(uuid));
        return amount >= withdraw;
    }
}
