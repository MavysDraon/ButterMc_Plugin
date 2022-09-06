package me.buttermc.Commands.CommandEvent;

import me.buttermc.Main;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private final Main plugin;
    public PlayerInteract(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPaint(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        if (!event.getItem().getType().isBlock())
            return;
        if (!plugin.hasPainters())
            return;
        if (!plugin.getPainters().contains(event.getPlayer()))
            return;
        if (event.getPlayer().rayTraceBlocks(50) == null)
            return;

        //THEY ARE A PLAYER
        event.setCancelled(true);

        Block block = event.getPlayer().rayTraceBlocks(50).getHitBlock();
        block.setType(event.getItem().getType());
        return;
    }
}
