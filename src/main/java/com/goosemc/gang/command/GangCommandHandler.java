package com.goosemc.gang.command;

import com.goosemc.Prison;
import com.goosemc.gang.Gang;
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
import java.util.Objects;
import java.util.UUID;

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
        } else if (args[0].equalsIgnoreCase("invite")) {
            if (args.length == 1) {
                usage(player);
            } else {
                UUID invite = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId();
                this.gang = new Gang(this.prison);

                if (this.gang.getGangByPlayer(player.getUniqueId()) == null) {
                    player.sendMessage(Color.translate("&cYou are not in gang"));
                    return false;
                }

                if (this.gang.getGangLeader(player.getUniqueId().toString()) == null) {
                    player.sendMessage(Color.translate("&cYou must be a captain or leader to invite players to gang"));
                    return false;
                }

                List<String> captains = this.gang.getGangCaptains(this.gang.getGangByPlayer(player.getUniqueId()));
                for (String captain : captains) {
                    if (!player.getUniqueId().toString().equalsIgnoreCase(captain)) {
                        player.sendMessage(Color.translate("&cYou don't have permission to invite people to gang"));
                        return false;
                    }
                }

                if (this.gang.getGangByPlayer(invite) != null) {
                    player.sendMessage(Color.translate("&c" + Bukkit.getPlayer(invite) + " is currently in gang " + this.gang.getGangByPlayer(invite)));
                    return false;
                }

                this.gang.invite(invite, gang.getGangByPlayer(player.getUniqueId()));
            }
        }

        return true;
    }

    void usage(Player player) {

    }
}
