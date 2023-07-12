package org.mavydragons.buttermc.Commands;

import com.sun.source.tree.BreakTree;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.mavydragons.buttermc.Main;

public class PrivateVault implements CommandExecutor, Listener {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("pv")) {
            if (sender.hasPermission("pv.use")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("No!");
                    return true;
                }
                Player player = (Player) sender;
                Inventory inv = Bukkit.createInventory(player, 54, player.getName() + "'s Private Vault");

                if (Main.menus.containsKey(player.getUniqueId().toString()))
                    inv.setContents(Main.menus.get(player.getUniqueId().toString()));

                player.openInventory(inv);


            }
            sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to do that!");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onGUIClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().contains(event.getPlayer().getName() + "'s Private Vault")) {
            Main.menus.put(event.getPlayer().getUniqueId().toString(), event.getInventory().getContents());
        }
    }
}
