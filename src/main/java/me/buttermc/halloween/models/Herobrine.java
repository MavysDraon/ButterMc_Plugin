package me.buttermc.halloween.models;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Herobrine {

    private final Player player;

    private World world;

    private EntityPlayer npc;

    public Herobrine(Player player) {
        this.player = player;
        this.setWorld(player.getWorld());
        generateNPC();
    }

    private void generateNPC() {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld)getWorld()).getHandle();
        GameProfile profile = getSkin();
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));

        npc.setCustomNameVisible(false);
        setNpc(npc);
    }

    private GameProfile getSkin() {
        // Skin #994316404 generated on Feb 6, 2020 9:54:32 PM via MineSkin.org - https://minesk.in/994316404
        GameProfile skin994316404 = new GameProfile(UUID.fromString("337b573b-1fd5-4b8f-ac42-28a827c381ec"), "");
        skin994316404.getProperties().put("textures", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1ODEwNDc2NzE3NzUsInByb2ZpbGVJZCI6IjU2Njc1YjIyMzJmMDRlZTA4OTE3OWU5YzkyMDZjZmU4IiwicHJvZmlsZU5hbWUiOiJUaGVJbmRyYSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY1ZjFkZTZkMzhiYTljODRiMGRmNDI0NjgxZTc4NWM1MjMwMmQ0M2U3NzNhYWEzODQwMjA5MjM0NTVmODk2NSJ9fX0=", "BFQODG2LMZaGMI7ltEP/EiBPgkn6dqYEOTG2ao6LeNJx4zAXsrTahvrXiMrqZZcjSKkJ0PZnrH+t73EjFjbU6gfM54W7WJEhp2KeBwpsnBhZwUbqeRq9ZErf2bKxlf5+vnQBCWKzGUNcaL2oyvNzL2XCXATtj6gngD1J/3+jbZfTX7Qm0vJ5A4bwIXeGKnrgPJWulyclqK8lE+gPFGOjRvQUbtWhD1ZO1Z4f/xPydfJpLCCLJ/KoG2c5FhN5fVnF7K2bZNDOtaY0lPLz5dpTMSUA89PAD+iQlX9ogpVLE4YhCNCXjys/2956wbynRzsjAbrFxgW7gjUNzjPp28PiBe7Wn9V3/PSjVKE/jz2aSrEBSFoHMtzs7jaVtkIJ1dWhif8wYvhsvG2WkLLcB8i/jtnrbi4h+gKYH7AP+b7U3w+Omb7FbG3C3Kv9l6uOr8KVzhBMklS9UXRDe+KM8oKkzFOclkAQ4I9Cv7VhS9NMYPR3myHHKvnHOv/37i4YdgCaKY7trZYdZ9Shfjol6HATFrB9Au01IelpRustN6AU5tItqV1zHw3taW0OZFcCpv7jN1HF7pYcmXRsVpvvYysbDKbVKOI6AR34vLWv+A+/+njcqlZMLgYjgHpLbYPq/KEiz7HraM8atAbEgZxLAQTcLcT3NbDhGZS+hDviXK5uZFI="));
        return skin994316404;
    }

    public void spawn() {
        Location loc = player.getLocation();
        double distance = 1.5;
        if (player.isSprinting())
            distance = 5.5;
        if (player.getFacing() == BlockFace.NORTH)
            loc.setZ(loc.getZ() - distance);
        if (player.getFacing() == BlockFace.SOUTH)
            loc.setZ(loc.getZ() + distance);
        if (player.getFacing() == BlockFace.WEST)
            loc.setX(loc.getX() - distance);
        if (player.getFacing() == BlockFace.EAST)
            loc.setX(loc.getX() + distance);

        loc.setDirection(loc.getDirection().multiply(-1));
        getNpc().setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public EntityPlayer getNpc() {
        return npc;
    }

    public void setNpc(EntityPlayer npc) {
        this.npc = npc;
    }
}
