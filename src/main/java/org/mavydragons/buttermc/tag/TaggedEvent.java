package org.mavydragons.buttermc.tag;

import org.mavydragons.buttermc.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TaggedEvent implements Listener {

    private Main plugin;
    public TaggedEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTag(EntityDamageByEntityEvent event) {
        if (!plugin.game.isPlaying())
            return;
        if (!(event.getEntity() instanceof Player))
            return;
        if (!(event.getDamager() instanceof Player))
            return;

        event.setCancelled(true);

        if ((Player)event.getDamager() != plugin.game.getIt())
            return;

        ((Player)event.getDamager()).setGlowing(false);

        plugin.game.tagged((Player) event.getEntity());

        event.getEntity().getWorld().spawnParticle(Particle.FLASH, event.getEntity().getLocation(), 0);

        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + (Player) event.getEntity() +
                " is it!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.game.isPlaying())
            return;

        event.getPlayer().setScoreboard(plugin.game.getboard());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!plugin.game.isPlaying())
            return;
        if (plugin.game.getIt() == event.getPlayer()) {
            event.getPlayer().setGlowing(false);
            plugin.game.end();
            Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD +
                    event.getPlayer().getName() + " has left the game... sore loser");
        }
    }

}
