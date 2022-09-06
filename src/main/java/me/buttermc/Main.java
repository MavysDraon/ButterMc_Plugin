package me.buttermc;

import me.buttermc.Commands.*;
import me.buttermc.Commands.CommandEvent.*;
import me.buttermc.Commands.TabCompleter.*;
import me.buttermc.DiamondCounter.Files.DataManager;
import me.buttermc.Events.*;
import me.buttermc.TelepathyEnchant.*;
import me.buttermc.TrailsGui.commands.Trail;
import me.buttermc.TrailsGui.events.*;
import me.buttermc.TrailsGui.models.GUI;
import me.buttermc.customhusk.commands.SpawnMob;
import me.buttermc.custommaps.commands.ImageCommand;
import me.buttermc.custommaps.data.ImageManager;
import me.buttermc.custommob.events.*;
import me.buttermc.halloween.ScaryHandler;
import me.buttermc.license.commands.GetLicense;
import me.buttermc.license.listeners.UseLicense;
import me.buttermc.mobpoints.sql.*;
import me.buttermc.privatechests.listeners.*;
import me.buttermc.tag.*;
import me.buttermc.tag.models.Game;
import me.buttermc.wolver.listeners.SpawnEntity;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.*;

public final class Main extends JavaPlugin implements Listener{

    public MySQL SQL;
    public SQLGetter datasql;
    public Bar bar;
    public Game game;
    public static DataManager configuration;
    public CustomRecipe cup;

    public static Map<String, ItemStack[]> menus = new HashMap<String, ItemStack[]>();

    private final List<Player> painters = new ArrayList<Player>();

    public List<ItemStack> stolenItems = new ArrayList<>();

    public Economy eco;


    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info(format("&c+-------------------+"));
        Bukkit.getLogger().info(format("&c|       Enable      |"));
        Bukkit.getLogger().info(format("&c|      ButterMc     |"));
        Bukkit.getLogger().info(format("&c===================="));
        Bukkit.getLogger().info(format("&c|   Version: 1.0    |"));
        Bukkit.getLogger().info(format("&c|       Beta        |"));
        Bukkit.getLogger().info(format("&c+-------------------+"));

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
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new EntityDeath(this), this);
        pm.registerEvents(new BlockPlace(this), this);
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
        pm.registerEvents(new SpawnEntity(), this);
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

        ScaryHandler handler = new ScaryHandler(this);
        handler.start();

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
        getCommand("randomblock").setExecutor(new RandomBlock(this));
        getCommand("mystats").setExecutor(new StatCommand());
        getCommand("pv").setExecutor(new PrivateVault());
        getCommand("telepathy").setExecutor(new TelepathyCommand());
        getCommand("trails").setExecutor(new Trail());
        getCommand("spawnhusk").setExecutor(new SpawnMob());
        getCommand("bonus").setExecutor(new BonusCommand());
        getCommand("paint").setExecutor(new PaintCommand(this));
        getCommand("tag").setExecutor(new TagCommand(this));
        getCommand("map").setExecutor(new ImageCommand());
        getCommand("getID").setExecutor(new GetLicense());
        getCommand("buttermc").setExecutor(new HelpCommand());
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
