package org.mavydragons.buttermc;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void senderMessage(CommandSender sender, String message, boolean usePlaceholderAPI) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (usePlaceholderAPI) {
                message = PlaceholderAPI.setPlaceholders(player, message);
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sendMessage(sender, message, false);
    }
}
