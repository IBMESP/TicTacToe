package com.gmail.ibmesp1.ttt.commands.subcommands;

import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ttt.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class VersionSubCommand extends SubCommand {

    private final TicTacToe plugin;

    public VersionSubCommand(TicTacToe plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(args.length > 1) {
            if (!(sender instanceof Player)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ttt version");
                return;
            }

            Player player = (Player) sender;

            player.sendMessage(ChatColor.RED + "/ttt version");
            return;
        }

        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TicTacToe]" + ChatColor.RESET + " Version " + plugin.version);
            return;
        }

        Player player = (Player) sender;

        player.sendMessage(ChatColor.GREEN + "[TicTacToe]" + ChatColor.RESET + " Version " + plugin.version);
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
