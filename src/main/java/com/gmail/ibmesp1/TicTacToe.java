package com.gmail.ibmesp1;

import com.gmail.ibmesp1.command.TicCommand;
import com.gmail.ibmesp1.events.TicEvents;
import com.gmail.ibmesp1.utils.DataManager;
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
    public HashMap<UUID,Boolean> playerOne;
    public HashMap<UUID,Boolean> playerTwo;
    public HashMap<UUID,Boolean> gameFinished;


    public final int languageFileVersion = 1;

    @Override
    public void onEnable() {
        PluginDescriptionFile pdffile = getDescription();
        version = pdffile.getVersion();
        name = "[" + pdffile.getName() + "]";

        languageData = new DataManager(this,"languages/" + getConfig().getString("locale") + ".yml");

        playerOne = new HashMap<>();
        playerTwo = new HashMap<>();
        gameFinished = new HashMap<>();

        Bukkit.getConsoleSender().sendMessage("[TicTacToe] - Version: " + version + " Enabled - By Ib");
        registerCommands();
        registerEvents();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getLanguageData().options().copyDefaults(true);

        if (getConfig().getInt("languageFile") < languageFileVersion) {
            urgentConsoleWarning("You language files are no longer supported with this version!");
            urgentConsoleWarning("Please update en_US.yml and update any other language files to version " +
                    ChatColor.AQUA + languageFileVersion + ChatColor.RED + ".");
            urgentConsoleWarning("Please do not update your config.yml until your language files have been updated.");
        }
    }

    @Override
    public void onDisable() {

    }

    public void registerCommands() {
        getCommand("ttt").setExecutor(new TicCommand(this));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new TicEvents(this),this);
    }

    public FileConfiguration getLanguageData() {
        return languageData.getConfig();
    }

    public String getLanguageString(String path) {
        return languageData.getConfig().getString(path);
    }

    private void urgentConsoleWarning(String msg) {
        Bukkit.getConsoleSender().sendMessage("[TicTacToe] " + ChatColor.RED + msg);
    }
}
