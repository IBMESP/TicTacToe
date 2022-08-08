package com.gmail.ibmesp1.ttt.commands.subcommands;

import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ttt.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GiveSubCommand extends SubCommand {

    private final TicTacToe plugin;

    public GiveSubCommand(TicTacToe plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageString("config.console"));
            return;
        }

        Player player = (Player) sender;

        if(args.length > 1) {
            player.sendMessage(ChatColor.RED + "/ttt give");
            return;
        }

        if(!player.hasPermission("ttt.give")){
            player.sendMessage(plugin.getLanguageString("config.perms"));
            return;
        }
        ItemStack nameTag = new ItemStack(Material.NAME_TAG);
        ItemMeta tagMeta = nameTag.getItemMeta();

        tagMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "TableMaker");
        nameTag.setItemMeta(tagMeta);
        player.getInventory().addItem(nameTag);
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
