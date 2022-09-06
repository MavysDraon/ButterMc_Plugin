package me.buttermc.Events;

import me.buttermc.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class MobKillEvent implements Listener{

    private Main plugin;
    public MobKillEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Monster) {
            Player player = event.getEntity().getKiller();
            if (player == null)
                return;
            Random r = new Random();
            int amount = r.nextInt(1000);
            plugin.eco.depositPlayer(player, amount);
            player.sendMessage(ChatColor.GREEN + "" +
                    ChatColor.BOLD + "+ $" + amount);
        }
    }

}
