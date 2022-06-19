package com.gmail.ibmesp1.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TicTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            List<String> fill = new ArrayList<>();
            fill.add("accept");
            fill.add("reload");
            fill.add("help");
            fill.add("give");

            for (String s : fill) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                    commands.add(s);
                }
            }
            return commands;
        }
        return null;
    }
}
