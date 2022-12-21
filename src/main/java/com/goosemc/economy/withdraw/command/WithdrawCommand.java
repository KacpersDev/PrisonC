package com.goosemc.economy.withdraw.command;

import com.goosemc.Prison;
import com.goosemc.economy.withdraw.Withdraw;
import com.goosemc.utils.Color;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public class WithdrawCommand implements CommandExecutor {

    private final Prison prison;
    private Withdraw withdraw;

    public WithdrawCommand(Prison prison) {
        this.prison = prison;
    }
    @Override
    @Deprecated
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (!(sender.hasPermission("economy.command.withdraw"))) {
            sender.sendMessage(Color.translate("&cNo Permissions"));
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            usage(player);
        } else {
            withdraw = new Withdraw(this.prison, player.getUniqueId());
            if (withdraw.check(Long.parseLong(args[0]))) {
                withdraw.allow(Long.parseLong(args[0]));
            } else {
                withdraw.disAllow();
            }
        }

        return true;
    }

    void usage(Player player){
        player.sendMessage(Color.translate("&cPlease provide an amount to withdraw"));
    }
}
