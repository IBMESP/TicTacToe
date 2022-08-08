package com.gmail.ibmesp1.ttt.commands.subcommands;

import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ttt.TicTacToe;
import com.gmail.ibmesp1.ttt.utils.ArmorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadSubCommand extends SubCommand {

    private final TicTacToe plugin;
    private final ArmorUtils armorUtils;
    private final DataManager tablesLoc;

    public ReloadSubCommand(TicTacToe plugin, DataManager tablesLoc) {
        this.plugin = plugin;
        this.tablesLoc = tablesLoc;

        armorUtils = new ArmorUtils(plugin,tablesLoc);
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(args.length > 1) {
            if(!(sender instanceof Player)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ttt reload");
                return;
            }

            Player player = (Player) sender;

            player.sendMessage(ChatColor.RED + "/ttt reload");
            return;
        }

        if(!(sender instanceof Player)) {
            armorUtils.deleteHolo();
            plugin.reloadConfig();
            tablesLoc.reloadConfig();
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageString("config.reloaded"));
            armorUtils.createHolo();
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ttt.reload")) {
            player.sendMessage(plugin.getLanguageString("config.perms"));
            return;
        }
        armorUtils.deleteHolo();
        plugin.reloadConfig();
        tablesLoc.reloadConfig();
        player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("config.reloaded"));
        Bukkit.getConsoleSender().sendMessage(plugin.getLanguageString("config.reloaded"));
        armorUtils.createHolo();
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
