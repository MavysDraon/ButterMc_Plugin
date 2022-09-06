package me.buttermc.halloween;

import me.buttermc.Main;
import me.buttermc.halloween.models.Herobrine;
import me.buttermc.halloween.models.PacketSender;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ScaryHandler {

    private final Main plugin;
    public ScaryHandler(Main plugin) {
        this.plugin= plugin;
    }

    public void excute(Player player) {
        Herobrine brine = new Herobrine(player);
        brine.spawn();

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (4*20), 1));
        player.setSprinting(false);
        player.setWalkSpeed(.01F);
        if (player.isFlying())
            player.setFlySpeed(.01F);

        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1.0F, 0.2F);

        PacketSender packets = new PacketSender(player, brine.getNpc());
        packets.send();

        new BukkitRunnable() {
            int count = 0;
            public void run() {
                if (count % 10 == 0)
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BREATH, .7F, 0.5F);
                if (count >= 60) {
                    //remove npc
                    player.setWalkSpeed(0.2F);
                    if (player.isFlying())
                        player.setFlySpeed(.2F);
                    packets.remove();
                    cancel(); // make sure to have this!
                }
                packets.update(player.getLocation());
                count++;
            }


        }.runTaskTimer(plugin, 0, 0);
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty())
                    return;
                Player player = Bukkit.getOnlinePlayers().stream().skip((int)
                        (Bukkit.getOnlinePlayers().size() * Math.random())).findFirst().orElse(null);
                if (player.getWorld().getTime() >= 14000) {
                    excute(player);
                    return;
                }
            }
        }, 40, (20 * 15)); // 20ticks per sec
    }
}
