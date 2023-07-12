package org.mavydragons.buttermc.Events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener{


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(format("&a&lButter&f&lMc &7> &bWelcome to the server ")
                + event.getPlayer());
    }

    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
