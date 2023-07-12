package org.mavydragons.buttermc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedeemCommand implements CommandExecutor {

    private Main plugin;

    public RedeemCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Questo comando può essere eseguito solo da un giocatore.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Utilizzo corretto: /redeem <codice>");
            return true;
        }

        String code = args[0]; // Ottieni il codice inserito dall'utente

        if (!plugin.getConfig().contains("codici." + code)) {
            player.sendMessage("Il codice inserito non esiste.");
            return true;
        }

        // Il codice esiste, puoi eseguire l'azione desiderata

        // Esempio di azione da eseguire quando il codice è valido
        plugin.getConfig().set("codici." + code + ".ricompensa", "eco give %player% 100");
        plugin.saveConfig();

        player.sendMessage("Hai inserito con successo il codice: " + code);

        return true;
    }

}
