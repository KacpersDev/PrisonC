package com.goosemc.economy.token;

import com.goosemc.Prison;
import com.goosemc.economy.EconomyManager;
import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public class TokenCommandHandler implements CommandExecutor {

    private final Prison prison;
    private final EconomyManager manager;
    private static final long MAX = Long.parseLong("999099999999999927");
    public TokenCommandHandler(Prison prison) {
        this.prison = prison;
        this.manager = new EconomyManager(this.prison);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            usage(sender);
        } else if (args[0].equalsIgnoreCase("set")) {
            if (!sender.hasPermission("economy.command.set")) {
                sender.sendMessage(Color.translate("&cNo Permission"));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (args.length == 2) {
                    usage(sender);
                } else {
                    long amount = Long.parseLong(args[2]);
                    if (amount > MAX) {
                        sender.sendMessage(Color.translate("&cMax tokens reached."));
                        return false;
                    }
                    this.manager.setToken(Objects.requireNonNull(target).getUniqueId(), amount);
                    sender.sendMessage(Color.translate("&cYou have set " + amount + " of tokens to " + target.getName()));
                }
            }
        } else if (args[0].equalsIgnoreCase("add")) {
            if (!sender.hasPermission("economy.command.add")) {
                sender.sendMessage(Color.translate("&cNo Permission"));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (args.length == 2) {
                    usage(sender);
                } else {
                    long amount = Long.parseLong(args[2]);
                    if (EconomyManager.token.get(target.getUniqueId()) + amount > MAX) {
                        sender.sendMessage(Color.translate("&cMax tokens reached."));
                        return false;
                    }
                    this.manager.addToken(Objects.requireNonNull(target).getUniqueId(), amount);
                    sender.sendMessage(Color.translate("&cYou have added " + amount + " of tokens to " + target.getName()));
                }
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (!sender.hasPermission("economy.command.remove")) {
                sender.sendMessage(Color.translate("&cNo Permission"));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (args.length == 2) {
                    usage(sender);
                } else {
                    long amount = Integer.parseInt(args[2]);
                    if (String.valueOf(amount).length() > 18) {
                        sender.sendMessage(Color.translate("&cFailed to remove token."));
                        return false;
                    }
                    if ((this.manager.getToken(Objects.requireNonNull(target).getUniqueId())) < amount) {
                        sender.sendMessage(Color.translate("&c" + target.getName() + " doesn't have " + amount + " of tokens."));
                        return false;
                    }
                    this.manager.removeToken(Objects.requireNonNull(target).getUniqueId(), String.valueOf(amount));
                    sender.sendMessage(Color.translate("&cYou have removed " + amount + " of tokens from " + target.getName()));
                }
            }
        } else if (args[0].equalsIgnoreCase("check")) {

            if (!sender.hasPermission("economy.command.check")) {
                sender.sendMessage(Color.translate("&cNo Permission"));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                long tokenAmount = this.manager.getToken(Objects.requireNonNull(target).getUniqueId());
                sender.sendMessage(Color.translate("&c" + target.getName() + " has " + tokenAmount + " of tokens."));
            }
        }

        return true;
    }

    void usage(CommandSender sender){
        for (final String i : this.prison.getConfiguration().getStringList("token.usage")) {
            sender.sendMessage(Color.translate(i));
        }
    }
}
