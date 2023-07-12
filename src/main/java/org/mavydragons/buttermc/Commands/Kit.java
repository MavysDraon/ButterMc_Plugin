package org.mavydragons.buttermc.Commands;

import org.mavydragons.buttermc.Main;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Kit implements CommandExecutor, Listener {

    Economy eco = null;

    private Main plugin;


    HashMap<String, Boolean> part = new HashMap<String, Boolean>();
    HashMap<String, Integer> id = new HashMap<String, Integer>();
    int taskID;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("kit")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.DARK_RED + "You cannot run this command!");
                return true;
            }
            Player player = (Player) sender;
            openGUI(player);
            return true;
        }
        return false;
    }

    public void openGUI(Player player) {

        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Kit GUI");

        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_AQUA + "Star Axe");
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);

        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Click to buy!");
        lore.add(ChatColor.AQUA + "$100,000");
        meta.setLore(lore);

        item.setItemMeta(meta);

        inv.setItem(4, item);

        player.openInventory(inv);

    }

    public ItemStack giveItem(String name) {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_AQUA + name + "'s  Star Axe");
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);

        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Legendary!");
        lore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Star Axe");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!ChatColor.stripColor(e.getView().getTitle().toString()).equalsIgnoreCase("Kit GUI")) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;
        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Star Axe")) {
            Player player = (Player) e.getWhoClicked();
            if (eco.getBalance(player) < 100000) {
                player.sendMessage(ChatColor.DARK_RED + "You cannot afford this!");
                player.closeInventory();
                e.setCancelled(true);
                return;
            }
            eco.withdrawPlayer(player, 100000);
            player.getInventory().addItem(giveItem(player.getName()));
            player.closeInventory();
            e.setCancelled(true);
            return;
        }
        return;

    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {

        Player p = event.getPlayer();

        if (!(p.getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE) &&
                (p.getInventory().getItemInMainHand().getItemMeta().hasLore())) {
            if (part.get(p.getName()) == null) part.put(p.getName(), false);    // <-- {Cmaxx=false}
            if (part.get(p.getName()) == true) return;

            if (part.get(p.getName()) == false) {
                // Does not have particle effect
                part.put(p.getName(), true);
                taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    double a = 0;

                    public void run() {
                        if (id.get(p.getName()) == null) {
                            id.put(p.getName(), taskID);
                        }

                        a += Math.PI / 16;
                        Location loc = p.getLocation();
                        Location first = loc.clone().add( Math.cos(a), Math.sin(a) + 1, Math.sin(a));
                        Location second = loc.clone().add( Math.cos(a + Math.PI), Math.sin(a) + 1, Math.sin(a + Math.PI));

                        p.getWorld().spawnParticle(Particle.HEART, first, 0, 0, 0, 0, 0);
                        p.getWorld().spawnParticle(Particle.HEART, second, 0, 0, 0, 0, 0);
                    }
                },0,1);
            } else {
                if (part.get(p.getName()) == null) part.put(p.getName(), false);

                if (part.get(p.getName()) == true) {
                    Bukkit.getScheduler().cancelTask(id.get(p.getName())); // <-- {Cmaxx=1217}
                    id.remove(p.getName());
                    part.put(p.getName(), false);
                }
            }
        }

    }
}
