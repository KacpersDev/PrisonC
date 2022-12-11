package com.goosemc.command;

import com.goosemc.Prison;
import com.goosemc.utils.Color;
import com.goosemc.utils.Config;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public class SetSpawnCommand implements CommandExecutor {

    private final Prison prison;

    public SetSpawnCommand(Prison prison) {
        this.prison = prison;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (!sender.hasPermission("prison.command.setspawn")) {
            sender.sendMessage(Color.translate("&cNo Permission."));
            return false;
        }

        Player player = (Player) sender;
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        this.prison.getConfiguration().set("spawn.x", x);
        this.prison.getConfiguration().set("spawn.y", y);
        this.prison.getConfiguration().set("spawn.z", z);
        new Config(this.prison.getConfigg(), this.prison.getConfiguration());
        player.sendMessage(Color.translate("&aSpawn has been set"));

        return true;
    }
}
