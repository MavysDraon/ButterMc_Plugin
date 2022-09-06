package me.buttermc.wolver.models;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

import java.util.Arrays;
import java.util.List;

public class WolfAlpha extends EntityWolf {

    public WolfAlpha(Location loc) {
        super(EntityTypes.WOLF, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());

        this.setHealth(500);
        this.setCustomNameVisible(true);
        this.setCustomName(new ChatComponentText(
                ChatColor.translateAlternateColorCodes('&', "64&lAlpha Wolf")));

        spawnMembers(loc);
    }

    public void initPathfinder() {
        super.initPathfinder();
        this.goalSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityHuman>(
                this, EntityHuman.class, true));
        this.goalSelector.a(1, new PathfinderGoalNearestAttackableTarget<WolfAlpha>(
                this, WolfAlpha.class, false));
    }

    public void spawnMembers(Location loc) {
        List<WolfMembers> pack = Arrays.asList(
                new WolfMembers(loc, "&c&oBeta Wolf"),
                new WolfMembers(loc, "&c&oDelta Wolf"),
                new WolfMembers(loc, "&c&oWarrior Wolf"),
                new WolfMembers(loc, "&c&oHunter Wolf"));

        WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
        for (WolfMembers wolf : pack)
            world.addEntity(wolf);
    }
}
