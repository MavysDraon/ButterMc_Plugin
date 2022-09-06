package me.buttermc.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("buttermc")) {
            if (args.length >= 1) {
                Player player = (Player) sender;

                if (args[0].equalsIgnoreCase("help-page1")) {
                    player.sendMessage(format("&a&lButter&f&lMc &7> &f&oHelp &8&lPage 1"));
                    player.sendMessage(format(""));
                    player.sendMessage(format("Hello"));
                    player.sendMessage(format("Doctor"));
                    player.sendMessage(format("Feed aliases: eat"));
                    player.sendMessage(format("Kit"));
                    player.sendMessage(format("Launch aliases: lch"));
                    player.sendMessage(format("StarTool"));
                    player.sendMessage(format("Gamble"));
                    player.sendMessage(format("Skull"));
                    player.sendMessage(format("RandomBlock"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("help-page2")) {
                    player.sendMessage(format("&a&lButter&f&lMc &7> &f&oHelp &8&lPage 2"));
                    player.sendMessage(format("Mystats"));
                    player.sendMessage(format("Pv"));
                    player.sendMessage(format("Telepathy"));
                    player.sendMessage(format("Trails"));
                    player.sendMessage(format("Spawnhusk"));
                    player.sendMessage(format("Bonus"));
                    player.sendMessage(format("Paint"));
                    player.sendMessage(format("Tag"));
                    player.sendMessage(format("Map"));
                    return true;
                }
                /*if (args[0].equalsIgnoreCase("playerkills")) {
                    player.sendMessage(ChatColor.AQUA + "You have " + player.getStatistic(Statistic.PLAYER_KILLS) + " player kills");
                    return true;
                }*/
            }
        }
        return false;
    }

    private String format(String endi) {
        return ChatColor.translateAlternateColorCodes('&', endi);
    }
}
