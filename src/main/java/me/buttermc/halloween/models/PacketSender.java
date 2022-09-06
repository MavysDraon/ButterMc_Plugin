package me.buttermc.halloween.models;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketSender {

    private final Player player;

    private final EntityPlayer npc;

    public PacketSender(Player player, EntityPlayer npc) {
        this.player = player;
        this.npc = npc;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityPlayer getNpc() {
        return npc;
    }

    public void send() {
        PlayerConnection connection = ((CraftPlayer)getPlayer()).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, getNpc()));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(getNpc()));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(getNpc(),
                (byte) (getNpc().yaw * 256 / 360)));
    }

    public void remove() {
        PlayerConnection connection = ((CraftPlayer)getPlayer()).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, getNpc()));
        connection.sendPacket(new PacketPlayOutEntityDestroy(getNpc().getId()));
    }

    public void update(Location loc) {
        PlayerConnection connection = ((CraftPlayer)getPlayer()).getHandle().playerConnection;
        loc.setDirection(loc.getDirection().multiply(-1));

        connection.sendPacket(new PacketPlayOutEntityHeadRotation(getNpc(),
                (byte) (loc.getYaw() * 256/360)));
        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(getNpc().getId(),
                (byte) (loc.getYaw()*256/360), (byte)(loc.getPitch() * 256/360), true));
    }
}
