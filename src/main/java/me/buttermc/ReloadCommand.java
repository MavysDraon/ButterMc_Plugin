package me.buttermc.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ReloadCommand implements CommandExecutor {

    private final Plugin plugin;

    public ReloadCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("buttermc.reload")) {
            plugin.reloadConfig();
            String reloadMessage = plugin.getConfig().getString("reload_message", "Il plugin è stato ricaricato con successo.");
            sender.sendMessage(reloadMessage);
        } else {
            sender.sendMessage("Non hai il permesso per eseguire questo comando.");
        }
        return true;
    }
}
