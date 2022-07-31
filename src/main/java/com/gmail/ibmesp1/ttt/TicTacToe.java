package com.gmail.ibmesp1.ttt;

import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ibcore.utils.Metrics;
import com.gmail.ibmesp1.ibcore.utils.UpdateChecker;
import com.gmail.ibmesp1.ibcore.utils.Utils;
import com.gmail.ibmesp1.ttt.commands.TicCommand;
import com.gmail.ibmesp1.ttt.commands.TicTab;
import com.gmail.ibmesp1.ttt.game.GameClick;
import com.gmail.ibmesp1.ttt.events.TicEvents;
import com.gmail.ibmesp1.ttt.game.GameStart;
import com.gmail.ibmesp1.ttt.utils.ArmorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class TicTacToe extends JavaPlugin {

    public String name;
    public String version;
    public DataManager languageData;
    public DataManager tablesLoc;
    public HashMap<UUID,UUID> players;
    public HashMap<UUID,Boolean> playerOne;
    public HashMap<UUID,Boolean> playerTwo;
    public HashMap<UUID,Boolean> gameFinished;
    public HashMap<UUID,Boolean> gameInvitation;
    public HashMap<UUID,Integer> player1C;
    public HashMap<UUID,Integer> player2C;
    public GameStart gameStart;
    public ArmorUtils armorUtils;

    public final int languageFileVersion = 2;

    @Override
    public void onEnable() {
        PluginDescriptionFile pdfile = getDescription();
        version = pdfile.getVersion();
        name = "[" + pdfile.getName() + "]";

        languageData = new DataManager(this,"languages/" + getConfig().getString("locale") + ".yml");
        tablesLoc = new DataManager(this,"tablesLoc.yml");

        players= new HashMap<>();
        playerOne = new HashMap<>();
        playerTwo = new HashMap<>();
        gameFinished = new HashMap<>();
        gameInvitation = new HashMap<>();
        player1C = new HashMap<>();
        player2C = new HashMap<>();

        new Metrics(this,15520);

        gameStart = new GameStart(this);
        armorUtils = new ArmorUtils(this,tablesLoc);

        armorUtils.createHolo();

        Bukkit.getConsoleSender().sendMessage("[TicTacToe] - Version: " + version + " Enabled - By Ib");
        registerCommands();
        registerEvents();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getLanguageData().options().copyDefaults(true);
        tablesLoc.getConfig().options().copyDefaults(true);
        tablesLoc.saveConfig();

        new UpdateChecker(this,102743).getLatestVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getLogger().info("[TicTacToe] " + getLanguageString("config.notUpdate"));
            } else {
                Bukkit.getLogger().warning("[TicTacToe] " + getLanguageString("config.update"));
            }
        });

        if (getConfig().getInt("languageFile") < languageFileVersion) {
            Utils.urgentConsoleWarning(name,"You language files are no longer supported with this version!");
            Utils.urgentConsoleWarning(name,"Please update en_US.yml and update any other language files to version " +
                    ChatColor.AQUA + languageFileVersion + ChatColor.RED + ".");
            Utils.urgentConsoleWarning(name,"Please do not update your config.yml until your language files have been updated.");
        }
    }

    @Override
    public void onDisable() {
        armorUtils.deleteHolo();
    }

    public void registerCommands() {
        getCommand("ttt").setExecutor(new TicCommand(this,gameStart, tablesLoc, armorUtils));
        getCommand("ttt").setTabCompleter(new TicTab());
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new TicEvents(this),this);
        Bukkit.getPluginManager().registerEvents(new GameClick(this,tablesLoc),this);
    }

    public FileConfiguration getLanguageData() {
        return languageData.getConfig();
    }

    public String getLanguageString(String path) {
        return languageData.getConfig().getString(path);
    }
}
