package org.mavydragons.buttermc.tag.models;

import org.mavydragons.buttermc.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Game {

    private int task; //runnables id

    private Player it;

    private boolean isPlaying; // is the game on or off?

    public Game() {
        this.task = -1;
        this.isPlaying = false;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public Player getIt() {
        return it;
    }

    public void setIt(Player it) {
        this.it = it;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }


    public Player pickFirstIt() {
        return Bukkit.getOnlinePlayers().stream().skip((int)
                (Bukkit.getOnlinePlayers().size() * Math.random())).findFirst().orElse(null);
    }

    public Scoreboard getboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("tagScoreboard", "dummy",
                ChatColor.translateAlternateColorCodes('&', "&a&lSeconds left: "));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore(ChatColor.GOLD + it.getName() + " is it!");
        score.setScore(0);
        return board;
    }

    public void end() {
        setPlaying(false);

        Bukkit.getScheduler().cancelTask(getTask());

        it.sendMessage(ChatColor.RED + "You lost :(");
        it.setGlowing(false);

        for (Player online: Bukkit.getOnlinePlayers()) {
            online.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            if (online != it)
                online.sendMessage(ChatColor.GREEN + "Congrats! You won tag :)");
        }
    }

    public void tagged(Player player) {

        setPlaying(true);

        if (getTask() != -1)
            Bukkit.getScheduler().cancelTask(task);

        setIt(player);
        it.setGlowing(true);

        for (Player online: Bukkit.getOnlinePlayers())
            online.setScoreboard(getboard());

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class),
                new Runnable() {

                    int timeleft = 300; // 5mins

                    public void run() {
                        if (timeleft <= 0) {
                            // game ended
                            end();
                            return;
                        }

                        for (Player online : Bukkit.getOnlinePlayers())
                            online.getScoreboard().getObjective(DisplaySlot.SIDEBAR)
                                    .setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                            "&a&lSeconds left: " + timeleft));

                        timeleft--;

                    }
                }, 0, 20); // 20ticks = 1s

    }
}
