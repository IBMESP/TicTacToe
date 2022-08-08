package com.gmail.ibmesp1.ttt.commands.subcommands;

import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(args.length > 1) {
            if(!(sender instanceof Player)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ttt help");
                return;
            }

            Player player = (Player) sender;

            player.sendMessage(ChatColor.RED + "/ttt help");
            return;
        }

        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"--------------------"+ChatColor.WHITE+"TicTacToe Help"+ChatColor.YELLOW+"--------------------");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"/ttt version:"+ChatColor.WHITE+" Backpacks version");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"/ttt accept:"+ChatColor.WHITE+" To accept an invitation");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"/ttt reload:"+ChatColor.WHITE+" To reload backpack config files");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"/ttt give:"+ChatColor.WHITE+" To give the TableMaker");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"/ttt delete:"+ChatColor.WHITE+" To delete the game table you are looking at");
            return;
        }

        Player player = (Player) sender;

        player.sendMessage(ChatColor.YELLOW+"--------------------"+ChatColor.WHITE+"TicTacToe Help"+ChatColor.YELLOW+"--------------------");
        player.sendMessage(ChatColor.YELLOW+"/ttt version:"+ChatColor.WHITE+" Backpacks version");
        player.sendMessage(ChatColor.YELLOW+"/ttt accept:"+ChatColor.WHITE+" To accept an invitation");
        player.sendMessage(ChatColor.YELLOW+"/ttt reload:"+ChatColor.WHITE+" To reload backpack config files");
        player.sendMessage(ChatColor.YELLOW+"/ttt give:"+ChatColor.WHITE+" To give the TableMaker");
        player.sendMessage(ChatColor.YELLOW+"/ttt delete:"+ChatColor.WHITE+" To delete the game table you are looking at");
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
