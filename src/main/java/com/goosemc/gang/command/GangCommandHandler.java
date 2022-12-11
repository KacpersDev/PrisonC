package com.goosemc.gang.command;

import com.goosemc.Prison;
import com.goosemc.gang.Gang;
import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Getter
public class GangCommandHandler implements CommandExecutor {

    private final Prison prison;
    private Gang gang;
    public GangCommandHandler(Prison prison) {
        this.prison = prison;
    }

    @Override
    @Deprecated
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {
            usage(player);
        } else if (args[0].equalsIgnoreCase("create")) {
            if (args.length == 1) {
                usage(player);
            } else {
                String gangName = args[1];
                new Gang(this.prison,gangName,player.getUniqueId(),0, new ArrayList<>(),new ArrayList<>()).create();
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
           this.gang = new Gang(this.prison);
           if (this.gang.getGangByPlayer(player.getUniqueId()) != null) {
               String gangName = this.gang.getGangByPlayer(player.getUniqueId());
               String gangLeader = this.gang.getGangLeader(gangName);
               if (!gangLeader.equalsIgnoreCase(player.getUniqueId().toString())) {
                   player.sendMessage(Color.translate("&cYou must be leader to delete a gang."));
                   return false;
               } else {
                   new Gang(this.prison,gangName,player.getUniqueId(),0, null, null).remove();
               }
           }
        }

        return true;
    }

    void usage(Player player) {

    }
}
