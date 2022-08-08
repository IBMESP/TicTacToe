package com.gmail.ibmesp1.ttt.commands;

import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ibcore.commands.SubCommandExecutor;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ttt.TicTacToe;
import com.gmail.ibmesp1.ttt.commands.subcommands.*;
import com.gmail.ibmesp1.ttt.game.GameStart;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TicCommand implements SubCommandExecutor {

    private final TicTacToe plugin;
    private final ArrayList<SubCommand> subCommands;
    private final DataManager tablesLoc;
    private final GameStart gameStart;

    public TicCommand(TicTacToe plugin, DataManager tablesLoc, GameStart gameStart) {
        this.plugin = plugin;
        this.tablesLoc = tablesLoc;
        this.gameStart = gameStart;

        subCommands = new ArrayList<>();
        subCommands.add(new HelpSubCommand());
        subCommands.add(new VersionSubCommand(plugin));
        subCommands.add(new ReloadSubCommand(plugin,tablesLoc));
        subCommands.add(new GiveSubCommand(plugin));
        subCommands.add(new AcceptSubCommand(plugin,gameStart));
        subCommands.add(new DeleteSubCommand(plugin,tablesLoc));
    }

    @Override
    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            if (args.length == 0)
                return true;

            for (int i = 0; i< getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    getSubCommands().get(i).perform(sender,args);
                }
            }
            return false;
        }

        Player player = (Player) sender ;

        if (args.length == 0)
            return true;

        for (int i = 0; i< getSubCommands().size(); i++){
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                getSubCommands().get(i).perform(player,args);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            ArrayList<String> subCommandsArgs = new ArrayList<>();

            for (int i = 0; i< getSubCommands().size(); i++){
                subCommandsArgs.add(getSubCommands().get(i).getName());
            }
            return subCommandsArgs;
        }else if(args.length == 2){
            for (int i = 0; i< getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubCommandsArgs(args);
                }
            }
        }
        return null;
    }
}
