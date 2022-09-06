package me.buttermc.wolver.models;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

public class WolfMembers extends EntityWolf {

    public WolfMembers(Location loc, String name) {
        super(EntityTypes.WOLF, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());

        this.setHealth(300);
        this.setCustomNameVisible(true);
        this.setCustomName(new ChatComponentText(
                ChatColor.translateAlternateColorCodes('&', name)));

    }

    public void initPathfinder() {
        super.initPathfinder();
        this.goalSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityHuman>(
                this, EntityHuman.class, true));
    }

}
