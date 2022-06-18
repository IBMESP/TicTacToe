package com.gmail.ibmesp1.commands;

import com.gmail.ibmesp1.TicTacToe;
import com.gmail.ibmesp1.game.GameStart;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicCommand implements CommandExecutor {

    private TicTacToe plugin;
    private final GameStart gameStart;

    public TicCommand(TicTacToe plugin,GameStart gameStart) {
        this.plugin = plugin;
        this.gameStart = gameStart;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage("");
            return false;
        }

        Player player = (Player) sender;

        if(args.length == 1)
        {
            if(plugin.players.containsKey(player.getUniqueId())){
                Player player1 = Bukkit.getPlayer(plugin.players.get(player.getUniqueId()));

                gameStart.gameStarts(player1,player);

                plugin.players.remove(player.getUniqueId());
            }
        }

        return false;
    }
}
