package me.buttermc.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class BonusCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("bonus")) {
            // /bonus
            if (!(sender instanceof Player)) {
                // console
                return true;
            }
            Player player = (Player) sender;

            if (player.getInventory().getItemInMainHand().getType()
                    .equals(Material.AIR)) {
                // holding no items
                player.sendMessage("Must be holding an item!");
                return true;
            }

            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 100.0,
                    AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            // add a modifier:
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
            item.setItemMeta(meta);
            player.sendMessage(ChatColor.GOLD + "TRANSFORMED!");

            return true;
        }
        return false;
    }

}
