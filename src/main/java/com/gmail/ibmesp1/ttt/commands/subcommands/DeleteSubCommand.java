package com.gmail.ibmesp1.ttt.commands.subcommands;

import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ttt.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class DeleteSubCommand extends SubCommand {

    private final TicTacToe plugin;
    private final DataManager tablesLoc;

    public DeleteSubCommand(TicTacToe plugin, DataManager tablesLoc) {
        this.plugin = plugin;
        this.tablesLoc = tablesLoc;
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageString("config.console"));
            return;
        }

        Player player = (Player) sender;

        if(args.length > 1) {
            player.sendMessage(ChatColor.RED + "/ttt delete");
            return;
        }

        if(!player.hasPermission("ttt.delete")){
            player.sendMessage(plugin.getLanguageString("config.perms"));
            return;
        }

        Block targetBlock = player.getTargetBlock(null,5);
        List<String> gameTables = plugin.getConfig().getStringList("gameTables");
        Location loc = targetBlock.getLocation();

        for (String gameTable : gameTables) {
            if (targetBlock.getType().equals(Material.getMaterial(gameTable))) {
                String world = loc.getWorld().getName();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();

                String path = world + "_" + x + "_" + y + "_" + z;

                if(tablesLoc.getConfig().contains("Locations." + path)){
                    Collection<Entity> entityList = loc.getWorld().getNearbyEntities(loc,1,1,1);

                    for (Entity entity:entityList){
                        if (entity.getType().equals(EntityType.ARMOR_STAND)){
                            entity.remove();
                        }
                    }

                    tablesLoc.getConfig().set("Locations." + path,null);
                    tablesLoc.saveConfig();
                }
            }
        }
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
