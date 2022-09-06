package me.buttermc.custommob.events;

import me.buttermc.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EntityDeath implements Listener {

    private Main plugin;
    public EntityDeath(Main plugin) {
        this.plugin = plugin;
    }

    private ItemStack[] goldsack = {
            new ItemStack(Material.GOLD_NUGGET,64),
            new ItemStack(Material.GOLD_BLOCK,3),
            new ItemStack(Material.GOLDEN_AXE,1),
            new ItemStack(Material.DIAMOND,16),
            new ItemStack(Material.DIAMOND_BOOTS,1),
            new ItemStack(Material.ENDER_PEARL,10),
    };

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        if (event.getEntity().getCustomName() == null)
            return;
        if (!event.getEntity().getCustomName().contains("Thief"))
            return;

        Random r = new Random();
        event.getEntity().getWorld().dropItemNaturally(
                event.getEntity().getLocation(),
                goldsack[r.nextInt(goldsack.length + 0) - 0]);

        for (ItemStack item : plugin.stolenItems) {
            if (item != null)
                event.getEntity().getWorld().dropItemNaturally(
                        event.getEntity().getLocation(), item);
        }
    }
}
