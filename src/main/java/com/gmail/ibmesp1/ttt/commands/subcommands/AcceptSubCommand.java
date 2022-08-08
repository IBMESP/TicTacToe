package com.gmail.ibmesp1.ttt.commands.subcommands;

import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ttt.TicTacToe;
import com.gmail.ibmesp1.ttt.game.GameStart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AcceptSubCommand extends SubCommand {

    private final TicTacToe plugin;
    private final GameStart gameStart;

    public AcceptSubCommand(TicTacToe plugin, GameStart gameStart) {
        this.plugin = plugin;
        this.gameStart = gameStart;
    }

    @Override
    public String getName() {
        return "accept";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageString("config.console"));
            return;
        }

        Player player = (Player) sender;

        if(args.length > 1) {
            player.sendMessage(ChatColor.RED + "/ttt accept");
            return;
        }

        if(!plugin.players.containsKey(player.getUniqueId())){
            player.sendMessage(plugin.getLanguageString("game.noInvitation"));
            return;
        }

        Player player1 = Bukkit.getPlayer(plugin.players.get(player.getUniqueId()));

        gameStart.gameStarts(player1,player);

        plugin.players.remove(player.getUniqueId());
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
