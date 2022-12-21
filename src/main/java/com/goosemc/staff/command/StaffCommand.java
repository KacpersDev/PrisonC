package com.goosemc.staff.command;

import com.goosemc.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffCommand implements CommandExecutor {

    public final static List<UUID> staff = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (!(sender.hasPermission("staff.command"))) {
            sender.sendMessage(Color.translate("&cNo Permissions."));
            return false;
        }

        Player player = (Player) sender;


        return true;
    }
}
