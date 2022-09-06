package me.buttermc.license.models;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Information implements Serializable {

    private static final long serialVersioUID = 1L;
    private UUID uuid;
    private String name;

    private Date issuedDate;

    public Information(Player player) {
        this.setUuid(player.getUniqueId());
        this.setName(player.getName());
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

}
