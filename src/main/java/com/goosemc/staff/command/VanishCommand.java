package com.goosemc.staff.command;

import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class VanishCommand implements CommandExecutor {

    public final static List<UUID> vanished = new ArrayList<>();
    @Override
    @Deprecated
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (!(sender.hasPermission("staff.command.vanish"))) {
            sender.sendMessage(Color.translate("&cNo Permissions"));
            return false;
        }

        Player player = (Player) sender;

        if (!vanished.contains(player.getUniqueId())) {
            vanished.add(player.getUniqueId());
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (!players.hasPermission("vanish.bypass")) {
                    players.hidePlayer(player);
                }
            }
            player.sendMessage(Color.translate("&aVanish has been enabled."));
        } else {
            vanished.remove(player.getUniqueId());
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.showPlayer(player);
            }
            player.sendMessage(Color.translate("&cVanish has been disabled."));
        }

        return true;
    }
}
