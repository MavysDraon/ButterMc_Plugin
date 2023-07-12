package org.mavydragons.buttermc.License.commands;

import org.mavydragons.buttermc.Main;
import org.mavydragons.buttermc.License.models.Information;
import org.mavydragons.buttermc.License.models.InformationDataType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;


public class GetLicense implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player player = (Player) sender;

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.DARK_RED + "Cannot give ID, inventory full!");
            return true;
        }

        Information info = new Information(player);
        info.setIssuedDate(new Date());

        player.getInventory().addItem(getID(info));
        player.sendMessage(ChatColor.AQUA + "ID Issued!");
        return true;
    }


    /***
     * Was in it's own Util class, however moved it here for video convenience.
     *
     * @param information - License Information object
     * @return Fixed ItemStack
     */
    private ItemStack getID(Information information) {
        ItemStack id = new ItemStack(Material.PAPER);
        ItemMeta meta = id.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GRAY + information.getName() + "'s ID");

        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "license");

        meta.getPersistentDataContainer().set(key, new InformationDataType(), information);

        id.setItemMeta(meta);
        return id;
    }

}
