package com.goosemc.economy.token;

import com.goosemc.Prison;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Getter
public class TokenCommandHandler implements CommandExecutor {

    private final Prison prison;
    public TokenCommandHandler(Prison prison) {
        this.prison = prison;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return true;
    }
}
