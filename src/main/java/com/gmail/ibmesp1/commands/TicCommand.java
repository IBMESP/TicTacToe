package com.gmail.ibmesp1.commands;

import com.gmail.ibmesp1.TicTacToe;
import com.gmail.ibmesp1.game.GameStart;
import com.gmail.ibmesp1.utils.ArmorUtils;
import com.gmail.ibmesp1.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.List;

public class TicCommand implements CommandExecutor {

    private TicTacToe plugin;
    private final GameStart gameStart;
    private final DataManager tablesLoc;

    public TicCommand(TicTacToe plugin, GameStart gameStart, DataManager tablesLoc) {
        this.plugin = plugin;
        this.gameStart = gameStart;
        this.tablesLoc = tablesLoc;
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
                return false;
            }

            if(args[0].equalsIgnoreCase("version")){
                player.sendMessage(ChatColor.GREEN + "[TicTacToe]" + ChatColor.RESET + " Version" + plugin.version);
            }

            if(args[0].equalsIgnoreCase("reload")){
                if (player.hasPermission("ttt.reload")) {
                    plugin.reloadConfig();
                    tablesLoc.reloadConfig();
                    player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("config.reloaded"));
                    System.out.println(plugin.getLanguageString("config.reloaded"));
                }else{
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
                }
            }

            if(args[0].equalsIgnoreCase("help")){
                //help
            }

            if(args[0].equalsIgnoreCase("give")){
                ItemStack nameTag = new ItemStack(Material.NAME_TAG);
                ItemMeta tagMeta = nameTag.getItemMeta();

                tagMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "TableMaker");
                //tagMeta.
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
