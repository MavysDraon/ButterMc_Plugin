package org.mavydragons.buttermc.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.mavydragons.buttermc.Main;

public class ButterMCCommand implements CommandExecutor {

    private final Main plugin;

    public ButterMCCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            // Messaggio di aiuto o gestione degli argomenti insufficienti
            return true;
        }

        String subCommand = args[0].toLowerCase();

        // Switch sui sottocomandi
        switch (subCommand) {
            case "pv":
                PrivateVault pvCommand = new PrivateVault();
                return pvCommand.onCommand(sender, cmd, label, args);
            case "mystats":
                StatCommand statCommand = new StatCommand();
                return statCommand.onCommand(sender, cmd, label, args);
            case "startool":
                StarTool starTool = new StarTool();
                return starTool.onCommand(sender, cmd, label, args);
            case "gamble":
                Gamble gamble = new Gamble(plugin);
                return gamble.onCommand(sender, cmd, label, args);
            case "feed":
                Feed feedCommand = new Feed(plugin);
                return feedCommand.onCommand(sender, cmd, label, args);
            case "bonus":
                BonusCommand bonusCommand = new BonusCommand();
                return bonusCommand.onCommand(sender, cmd, label, args);
            case "paint":
                PaintCommand paintCommand = new PaintCommand(plugin);
                return paintCommand.onCommand(sender, cmd, label, args);
            case "skull":
                Skull skull = new Skull();
                return skull.onCommand(sender, cmd, label, args);
            case "launch":
                Launch launch = new Launch();
                return launch.onCommand(sender, cmd, label, args);
            case "kit":
                Kit kit = new Kit();
                return kit.onCommand(sender, cmd, label, args);
            case "hello":
                HelloWorld helloWorld = new HelloWorld();
                return helloWorld.onCommand(sender, cmd, label, args);
            case "doctor":
                Heal heal = new Heal(plugin);
                return heal.onCommand(sender, cmd, label, args);
            case "godboots":
                GodBoots godBoots = new GodBoots();
                return godBoots.onCommand(sender, cmd, label, args);
            case "help":
                HelpCommand helpCommand = new HelpCommand();
                return helpCommand.onCommand(sender, cmd, label, args);
            case "altrocomando":
                 /*Crea un altro comando e richiama il relativo file
                 Esempio: AltroComandoCommand altroComando = new AltroComandoCommand();
                 return altroComando.onCommand(sender, cmd, label, args);*/
            default:
                // Sottocomando non valido, invia un messaggio di errore
                sender.sendMessage("Sottocomando non valido!");
                return true;
        }
    }
}
