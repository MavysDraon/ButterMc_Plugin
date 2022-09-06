package me.buttermc.Commands;

import me.buttermc.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RandomBlock implements CommandExecutor, Listener {

    private Main plugin;
    public RandomBlock(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // /randomblock
        if (label.equalsIgnoreCase("randomblock")) {
            if (!sender.hasPermission("randomblock.reload")) {
                sender.sendMessage(ChatColor.RED + "You cannot run this command");
                return true;
            }
            if (args.length == 0) {
                // /randomblock
                sender.sendMessage(ChatColor.RED + "Usage: /randomblock reload");
                return true;
            }
            if (args.length > 0) {
                // /randomblock reload
                if (args[0].equalsIgnoreCase("reload")) {
                    for (String msg :  Main.configuration.getConfig().getStringList("reload.message")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                msg));
                    }
                    this.plugin.reloadConfig();
                }
            }
        }
        return false;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        this.plugin.getConfig().getConfigurationSection("blocks").getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase(event.getBlock().getType().toString())) {
                ItemStack[] items = new ItemStack[this.plugin.getConfig().getStringList("blocks." + key).size()];
                ItemStack item = null;
                int position = 0;
                Random r = new Random();
                for (String i :  Main.configuration.getConfig().getStringList("blocks." + key)) {
                    // DIRT DIR
                    try {
                        item = new ItemStack(Material.matchMaterial(i), r.nextInt(16) + 1);
                    } catch(Exception e) {
                        item = new ItemStack(Material.matchMaterial(key));
                    }
                    items[position] = item;
                    position++;
                }
                int num = r.nextInt(items.length);
                event.setDropItems(false);
                World world = event.getPlayer().getWorld();
                world.dropItemNaturally(event.getBlock().getLocation(), items[num]);
            }
        });
    }
}
