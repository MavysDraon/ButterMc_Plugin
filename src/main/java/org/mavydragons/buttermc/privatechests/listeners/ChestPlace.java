package org.mavydragons.buttermc.privatechests.listeners;

import org.mavydragons.buttermc.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChestPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.CHEST)
            return;
        if (!(event.getBlock().getState() instanceof TileState))
            return;

        TileState state = (TileState) event.getBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class),
                "private-chests");

        container.set(key, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());

        state.update();  //important!

        event.getPlayer().sendMessage("Chest locked!");
    }

}
