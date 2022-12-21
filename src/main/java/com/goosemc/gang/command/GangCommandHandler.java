package com.goosemc.gang.command;

import com.goosemc.Prison;
import com.goosemc.gang.Gang;
import com.goosemc.utils.Color;
import com.goosemc.utils.Config;
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
               if (gangName == null) {
                   player.sendMessage(Color.translate("&cYou are not in gang."));
                   return false;
               }
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
                String invitedGang = this.gang.getGangByPlayer(player.getUniqueId());

                List<String> invited = Gang.invitation.get(invite);
                invited.add(invitedGang);
                Gang.invitation.replace(invite, Gang.invitation.get(invite), invited);
                Bukkit.getPlayer(invite).sendMessage(Color.translate("&9You have been invited to &f&l" + invitedGang + " &a[gang join &f&l" + invitedGang + "&a] &9to join"));
            }
        } else if (args[0].equalsIgnoreCase("join")) {
            if (args.length == 1) {
                usage(player);
            } else {
                String gangName = args[1];
                List<String> gangs = Gang.invitation.get(player.getUniqueId());
                if (gangs != null) {
                    for (String gang : gangs) {
                        if (gangName.equalsIgnoreCase(gang)) {
                            Gang.invitation.get(player.getUniqueId()).remove(gang);
                            this.gang = new Gang(this.prison);
                            this.gang.join(gang, player.getUniqueId());
                            new Config(this.getPrison().getGang(), this.getPrison().getGangConfiguration());
                        }
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("kick")) {
            if (args.length == 1) {
                usage(player);
            } else {
                this.gang = new Gang(this.prison);
                Player target = Bukkit.getPlayer(args[1]);
                this.gang.kick(this.gang.getGangByPlayer(player.getUniqueId()),target.getUniqueId());
                new Config(this.getPrison().getGang(), this.getPrison().getGangConfiguration());
            }
        }

        return true;
    }

    void usage(Player player) {

    }
}
