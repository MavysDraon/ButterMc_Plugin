package me.buttermc.customhusk.mobs;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

public class CustomHusk extends EntityZombieHusk{

    public CustomHusk(Location loc) {
        super(EntityTypes.HUSK, ((CraftWorld) loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());

        this.setBaby(true);
        this.setCustomName(new ChatComponentText(ChatColor.YELLOW + "Husky"));
        this.setCustomNameVisible(true);
    }

    @Override
    public void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.2D));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 0.2D, false, 1, null));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.2D));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(9,new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityChicken>(this, EntityChicken.class, true));
        this.targetSelector.a(3, new PathfinderGoalAvoidTarget<EntityTurtle>(this, EntityTurtle.class, 20, 1.0D, 1.0D));
    }
}
