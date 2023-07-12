package org.mavydragons.buttermc.cobblegenchest.listeners;

import org.mavydragons.buttermc.Main;
import org.mavydragons.buttermc.cobblegenchest.models.PacketSender;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestPlace implements Listener {

    @EventHandler
    public void onPlace(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        if (!event.hasBlock())
            return;
        if (event.getItem().getType() != Material.CHEST)
            return;
        if (event.getClickedBlock().getType() != Material.COBBLESTONE)
            return;

        Block chestBlock = event.getClickedBlock().getLocation().add(0, 1,0).getBlock();
        if (chestBlock.getType() != Material.AIR)
            return;

        event.setCancelled(true);
        ItemStack fee = new ItemStack(Material.CHEST, 1);
        event.getPlayer().getInventory().removeItem(fee);

        chestBlock.setType(Material.CHEST);
        executeAutoBreaker(event.getClickedBlock());
    }

    private void executeAutoBreaker(Block cobble) {
        PacketSender packet = new PacketSender();
        new BukkitRunnable() {
            int status = 6; //10 will reset it
            Block chestBlock = cobble.getLocation().add(0, 1,0).getBlock();
            Chest chest = (Chest) chestBlock.getState();
            public void run() {
                if (cobble.getType() == Material.AIR)
                    return;
                if (chestBlock.getType() != Material.CHEST) {
                    cobble.setType(Material.COBBLESTONE);
                    packet.sendPacket(cobble.getLocation(), 10);
                    cancel();
                    return;
                }
                if (status == 0) {
                    cobble.setType(Material.AIR);
                    chest.getInventory().addItem(new ItemStack(Material.COBBLESTONE));
                    status = 6;
                    return;
                }
                packet.sendPacket(cobble.getLocation(), status);
                status--;
            }

        }.runTaskTimer(Main.getPlugin(Main.class), 1, 10);
    }
}
