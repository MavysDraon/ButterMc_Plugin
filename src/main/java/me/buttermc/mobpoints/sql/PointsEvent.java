package me.buttermc.mobpoints.sql;

import me.buttermc.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PointsEvent implements Listener {

    private final Main plugin;
    public PointsEvent(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.datasql.createPlayer(player);
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            plugin.datasql.addPoints(player.getUniqueId(), 1);
            player.sendMessage("Point added!");
        }
    }

}
