package com.goosemc.listener;

import com.goosemc.Prison;
import com.goosemc.economy.EconomyManager;
import lombok.Getter;
import org.bukkit.event.Listener;

@Getter
public class PlayerChatListener implements Listener {

    private final Prison prison;
    private final EconomyManager manager;

    public PlayerChatListener(Prison prison) {
        this.prison = prison;
        this.manager = new EconomyManager(this.prison);
    }
}
