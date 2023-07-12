package org.mavydragons.buttermc;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.mavydragons.buttermc.Commands.*;
import org.mavydragons.buttermc.Commands.CommandEvent.CustomRecipe;
import org.mavydragons.buttermc.Commands.CommandEvent.PlayerInteract;
import org.mavydragons.buttermc.Commands.CommandEvent.StarEvents;
import org.mavydragons.buttermc.Commands.TabCompleter.HelpTab;
import org.mavydragons.buttermc.Commands.TabCompleter.StatTab;
import org.mavydragons.buttermc.DiamondCounter.Files.DataManager;
import org.mavydragons.buttermc.Events.*;
import org.mavydragons.buttermc.TelepathyEnchant.CustomEnchants;
import org.mavydragons.buttermc.TelepathyEnchant.TelepathyCommand;
import org.mavydragons.buttermc.TrailsGui.commands.Trail;
import org.mavydragons.buttermc.TrailsGui.events.ClickEvent;
import org.mavydragons.buttermc.TrailsGui.events.Movement;
import org.mavydragons.buttermc.TrailsGui.events.Quit;
import org.mavydragons.buttermc.TrailsGui.models.GUI;
import org.mavydragons.buttermc.CustomMaps.commands.ImageCommand;
import org.mavydragons.buttermc.CustomMaps.data.ImageManager;
import org.mavydragons.buttermc.License.commands.GetLicense;
import org.mavydragons.buttermc.License.listeners.UseLicense;
import org.mavydragons.buttermc.mobpoints.Sql.MySQL;
import org.mavydragons.buttermc.mobpoints.Sql.PointsEvent;
import org.mavydragons.buttermc.mobpoints.Sql.SQLGetter;
import org.mavydragons.buttermc.privatechests.listeners.ChestOpen;
import org.mavydragons.buttermc.privatechests.listeners.ChestPlace;
import org.mavydragons.buttermc.tag.TagCommand;
import org.mavydragons.buttermc.tag.TaggedEvent;
import org.mavydragons.buttermc.tag.models.Game;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin implements Listener {

    public MySQL SQL;
    public SQLGetter datasql;
    public Bar bar;
    public Game game;
    public static DataManager configuration;
    public CustomRecipe cup;

    //                KEY     VALUE
    public static Map<String, ItemStack[]> menus = new HashMap<String, ItemStack[]>();

    private final List<Player> painters = new ArrayList<Player>();

    public List<ItemStack> stolenItems = new ArrayList<>();

    public Economy eco;


    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info(format("&a+-------------------+"));
        Bukkit.getLogger().info(format("&a|       Enable      |"));
        Bukkit.getLogger().info(format("&a|      ButterMc     |"));
        Bukkit.getLogger().info(format("&a===================="));
        Bukkit.getLogger().info(format("&a|   Version: 1.0    |"));
        Bukkit.getLogger().info(format("&a|      Beta-8       |"));
        Bukkit.getLogger().info(format("&a+-------------------+"));

        if (!setupEconomy()) {
            System.out.println(ChatColor.RED + "You must have Vault"
                    + " and an Economy plugin Installed");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            this.getServer().getPluginManager().registerEvents(new PAPIJoinEvent(), this);
        else
            this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);

        this.saveDefaultConfig();
        registerCmd();
        registerCmdTab();

        CustomEnchants.register();

        Main.configuration = new DataManager(this);
        this.cup = new CustomRecipe(this);

        GUI menu = new GUI();
        menu.register();

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Kit(), this);
        pm.registerEvents(new ClickEvent(), this);
        pm.registerEvents(new CreeperExplode(), this);
        pm.registerEvents(new ChestOpen(), this);
        pm.registerEvents(new ChestPlace(), this);
        pm.registerEvents(new UseLicense(), this);
        pm.registerEvents(new StarEvents(), this);
        pm.registerEvents(new Quit(), this);
        pm.registerEvents(new Movement(), this);
        pm.registerEvents(new MobKillEvent(this), this);
        pm.registerEvents(new ChatEvent(), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new TaggedEvent(this), this);
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new PointsEvent(this), this);
        this.getServer().getPluginManager().registerEvents(this, this);


        Bukkit.addRecipe(cup.getRecipe());
        Bukkit.addRecipe(cup.getPickaxeRecipe());

        if (configuration.getConfig().contains("data-pv")) {
            this.restoreInvs();
            this.getConfig().set("data-pv", null);
            this.saveConfig();
        }


        bar = new Bar(this);
        bar.createBar();


        if (Bukkit.getOnlinePlayers().size() > 0)
            for (Player on : Bukkit.getOnlinePlayers())
                bar.addPlayer(on);

        this.game = new Game();
        this.SQL = new MySQL();
        this.datasql = new SQLGetter(this);


        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            //e.printStackTrace();
            // Login info is incorrect
            // they are not using a database
            Bukkit.getLogger().info("Database not connected");
        }

        if (SQL.isConnected()) {
            Bukkit.getLogger().info("Database is connected!");
            datasql.createTable();
            this.getServer().getPluginManager().registerEvents(this, this);
        }

        ImageManager manager = ImageManager.getInstance();
        manager.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Off");

        if (!menus.isEmpty()) {
            this.saveInvs();
        }

        if (game.isPlaying())
            game.end();

        /*bar.getBar().removeAll();*/

        SQL.disconnect();

    }

    public void registerCmd() {
        getCommand("doctor").setExecutor(new Heal(this));
        getCommand("feed").setExecutor(new Feed(this)); // /feed /eat
        getCommand("eat").setExecutor(new Feed(this));
        getCommand("kit").setExecutor(new Kit());
        getCommand("launch").setExecutor(new Launch());
        getCommand("hello").setExecutor(new HelloWorld());
        getCommand("gamble").setExecutor(new Gamble(this));
        getCommand("mystats").setExecutor(new StatCommand());
        getCommand("pv").setExecutor(new PrivateVault());
        getCommand("telepathy").setExecutor(new TelepathyCommand());
        getCommand("trails").setExecutor(new Trail());
        getCommand("bonus").setExecutor(new BonusCommand());
        getCommand("paint").setExecutor(new PaintCommand(this));
        getCommand("tag").setExecutor(new TagCommand(this));
        getCommand("map").setExecutor(new ImageCommand());
        getCommand("getID").setExecutor(new GetLicense());
        getCommand("buttermc").setExecutor(new HelpCommand());
        getCommand("redeem").setExecutor(new RedeemCommand(this));
    }
    public void registerCmdTab() {
        getCommand("mystats").setTabCompleter(new StatTab());
        getCommand("buttermc").setTabCompleter(new HelpTab());
    }


    public void saveInvs() {
        for (Map.Entry<String, ItemStack[]> entry : menus.entrySet()) {
            this.getConfig().set("data-pv." + entry.getKey(), entry.getValue());
        }
        this.saveConfig();
    }
    public void restoreInvs() {
        this.getConfig().getConfigurationSection("data-pv").getKeys(false).forEach(key ->{
            ItemStack[] content = ((List<ItemStack>) this.getConfig().get("data-pv." + key)).toArray(new ItemStack[0]);
            menus.put(key, content);
        });
    }


    public static void senderMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                message = PlaceholderAPI.setPlaceholders(player, message);
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }








    public void addPainter(Player player) {
        painters.add(player);
    }

    public void removePainter(Player player) {
        painters.remove(player);
    }

    public List<Player> getPainters() {
        return painters;
    }

    public boolean hasPainters() {
        return !painters.isEmpty();
    }












    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!bar.getBar().getPlayers().contains(event.getPlayer()))
            bar.addPlayer(event.getPlayer());
    }
























    private String format(String endi) {
        return ChatColor.translateAlternateColorCodes('&', endi);
    }



    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economy = getServer().
                getServicesManager().getRegistration(
                        net.milkbowl.vault.economy.Economy.class);
        if (economy != null)
            eco = economy.getProvider();
        return (eco != null);
    }
}
