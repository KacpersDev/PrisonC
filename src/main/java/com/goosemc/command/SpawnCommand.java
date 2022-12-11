package com.goosemc.command;

import com.goosemc.Prison;
import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public class SpawnCommand implements CommandExecutor {

    private final Prison prison;
    public SpawnCommand(Prison prison) {
        this.prison = prison;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        Location spawn = new Location(Bukkit.getWorld("world"),
                this.prison.getConfiguration().getDouble("spawn.x"),
                this.prison.getConfiguration().getDouble("spawn.y"),
                this.prison.getConfiguration().getDouble("spawn.z"));
        player.teleport(spawn);
        player.sendMessage(Color.translate("&aYou have been teleported to spawn."));

        return true;
    }
}
