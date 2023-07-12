package org.mavydragons.buttermc.Commands;

import org.mavydragons.buttermc.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed implements CommandExecutor {

    static Main plugin;
    public Feed(Main main) {
        plugin = main;
    }

    // /feed
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("feed") || label.equalsIgnoreCase("eat")) {
            if (!(sender.hasPermission("feed.use"))) {
                sender.sendMessage(ChatColor.RED + "You cannot do that!");
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You cannot do that!");
                return true;
            }
            Player player = (Player) sender;
            if (player.getFoodLevel() == 20) {
                sender.sendMessage(ChatColor.RED + "You already have full hunger!");
                return true;
            }
            player.setFoodLevel(20);
            sender.sendMessage(ChatColor.GREEN + "You have been fed!");
            return true;

        }
        return false;
    }
}
