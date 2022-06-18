package com.gmail.ibmesp1.commands;

import com.gmail.ibmesp1.TicTacToe;
import com.gmail.ibmesp1.game.GameStart;
import com.gmail.ibmesp1.utils.ArmorStands;
import com.gmail.ibmesp1.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class TicCommand implements CommandExecutor {

    private TicTacToe plugin;
    private final GameStart gameStart;
    private final DataManager tablesLoc;
    private final ArmorStands armorStands;

    public TicCommand(TicTacToe plugin, GameStart gameStart, DataManager tablesLoc, ArmorStands armorStands) {
        this.plugin = plugin;
        this.gameStart = gameStart;
        this.tablesLoc = tablesLoc;

        this.armorStands = armorStands;
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
            if(args[0].equalsIgnoreCase("delete")){
                Block targetBlock = player.getTargetBlock(null,5);
                List<String> gameTables = plugin.getConfig().getStringList("gameTables");
                Location loc = targetBlock.getLocation();

                for (String gameTable : gameTables) {
                    if (targetBlock.getType().equals(Material.getMaterial(gameTable))) {
                        String world = loc.getWorld().getName();
                        int x = loc.getBlockX();
                        int y = loc.getBlockY();
                        int z = loc.getBlockZ();

                        String path = world + "-" + x + "-" + y + "-" + z;

                        if(tablesLoc.getConfig().contains("Locations." + path)){
                            List<Entity> entityList = player.getNearbyEntities(5,5,5);

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
                return false;
            }


            if(!plugin.players.containsKey(player.getUniqueId())){
                player.sendMessage(plugin.getLanguageString("game.noInvitation"));
                return false;
            }

            Player player1 = Bukkit.getPlayer(plugin.players.get(player.getUniqueId()));

            gameStart.gameStarts(player1,player);

            plugin.players.remove(player.getUniqueId());
        }

        return false;
    }
}
