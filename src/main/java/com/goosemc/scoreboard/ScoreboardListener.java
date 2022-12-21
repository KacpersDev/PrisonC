package com.goosemc.scoreboard;

import com.goosemc.Prison;
import com.goosemc.economy.EconomyManager;
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
        long tokenUUID = EconomyManager.token.get(player.getUniqueId());
        long moneyUUID = EconomyManager.money.get(player.getUniqueId());

        for (final String lineItem : this.prison.getScoreboard().getStringList("scoreboard-normal")) {
            lines.add(Color.translate(lineItem)
                    .replace("%player%", player.getName())
                    .replace("%token%", coolFormat(tokenUUID, 0))
                    .replace("%money%", coolFormat(moneyUUID, 0)));
        }
        return lines;
    }

    private final char[] c = new char[]{'K', 'M', 'B', 'T', 'Q'};

    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
     * @param n the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    private String coolFormat(double n, int iteration) {
        if (String.valueOf(n).length() > 3) {
            double d = ((long) n / 100) / 10.0;
            boolean isRound = (d * 10) % 10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
            return (d < 1000 ? //this determines the class, i.e. 'k', 'm' etc
                    ((d > 99.9 || isRound || (!isRound && d > 9.99) ? //this decides whether to trim the decimals
                            (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                    ) + "" + c[iteration])
                    : coolFormat(d, iteration + 1));

        }

        return String.valueOf(n);
    }
}
