package me.buttermc.license.listeners;

import me.buttermc.Main;
import me.buttermc.license.models.Information;
import me.buttermc.license.models.InformationDataType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;

import java.text.SimpleDateFormat;

public class UseLicense implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        if (event.getItem().getType() != Material.PAPER)
            return;
        if (!event.getItem().hasItemMeta())
            return;
        if (!event.getItem().getItemMeta().getDisplayName().contains("ID")) // allows fake id check
            return;

        Player player = event.getPlayer();
        PersistentDataContainer container = event.getItem().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "license");

        if (container.has(key, new InformationDataType())) {
            Information info = container.get(key, new InformationDataType());
            player.sendMessage(ChatColor.GREEN + info.getName() + "'s ID");
            player.sendMessage(ChatColor.GREEN + "UUID: " + info.getUuid());
            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
            player.sendMessage(ChatColor.GREEN + "Date Issued: " + sd.format(info.getIssuedDate()));
        }
        player.sendMessage(ChatColor.DARK_RED + "This is a fake id!");
    }
}
