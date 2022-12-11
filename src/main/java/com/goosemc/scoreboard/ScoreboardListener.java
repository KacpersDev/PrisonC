package com.goosemc.scoreboard;

import com.goosemc.Prison;
import com.goosemc.utils.Color;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScoreboardListener implements AssembleAdapter {

    private final Prison prison;

    public ScoreboardListener(Prison prison) {
        this.prison = prison;
    }
    @Override
    public String getTitle(Player player) {
        return Color.translate(this.prison.getScoreboard().getString("scoreboard-title"));
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();
        for (final String lineItem : this.prison.getScoreboard().getStringList("scoreboard-normal")) {
            lines.add(Color.translate(lineItem)
                    .replace("%player%", player.getName()));
        }
        return lines;
    }
}
