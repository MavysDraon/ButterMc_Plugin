package me.buttermc.Commands;

import me.buttermc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {


    static Main plugin;
    public Heal(Main main) {
        plugin = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("doctor")) {
            if (sender.hasPermission("heal.use")) {
                if (args.length == 0) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        if (p.getHealth() == 20.0) {
                            p.sendMessage(ChatColor.BLUE + "You already have full health!");
                            return true;
                        }
                        p.setHealth(Main.configuration.getConfig().getDouble("heal-amount"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.configuration.getConfig().getString("messages.heal").replace("%player%", p.getName())));

                        Main.configuration.reloadConfig();
                        Main.configuration.getConfig().set("messages.no-permission", "&cYou cannot do that!");
                        Main.configuration.saveConfig();

                        return true;
                    }
                    sender.sendMessage(ChatColor.DARK_RED + "Try /heal <player-name>");
                    return true;
                }
                if (args.length >= 1) {
                    if (Bukkit.getPlayerExact(args[0]) != null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        target.setHealth(20.0);
                        target.sendMessage(ChatColor.GREEN+ "You have been healed!");
                        sender.sendMessage(ChatColor.GREEN + "You healed " + target.getName());
                        return true;

                    }
                    sender.sendMessage(ChatColor.DARK_RED + "Player not found");
                    return true;

                }
            }
            sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to do that!");
            return true;
        }
        return false;
    }

}
