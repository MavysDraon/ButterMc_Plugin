package me.buttermc.Events;

import me.buttermc.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DiamondCounterEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            Player player = event.getPlayer();
            int amount = 0;

            if (Main.configuration.getConfig().contains("players." + player.getUniqueId().toString() + ".total"))
                amount = Main.configuration.getConfig().getInt("players." + player.getUniqueId().toString() + ".total");

            Main.configuration.getConfig().set("players." + player.getUniqueId().toString() + ".total", (amount + 1));
            Main.configuration.saveConfig();
        }
    }

}
