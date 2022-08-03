package com.gmail.ibmesp1.ttt.commands;

import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ttt.TicTacToe;
import com.gmail.ibmesp1.ttt.game.GameStart;
import com.gmail.ibmesp1.ttt.utils.ArmorUtils;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.List;

public class TicCommand implements CommandExecutor {

    private final TicTacToe plugin;
    private final GameStart gameStart;
    private final DataManager tablesLoc;
    private final ArmorUtils armorUtils;

    public TicCommand(TicTacToe plugin, GameStart gameStart, DataManager tablesLoc, ArmorUtils armorUtils) {
        this.plugin = plugin;
        this.gameStart = gameStart;
        this.tablesLoc = tablesLoc;
        this.armorUtils = armorUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(plugin.name + ChatColor.RED + " You can't execute this from the console");
            return false;
        }

        Player player = (Player) sender;

        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("delete")){
                if(!player.hasPermission("ttt.delete")){
                    player.sendMessage(plugin.getLanguageString("config.perms"));
                    return false;
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

                        String path = world + x + "-" + y + "-" + z;

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
                player.sendMessage(ChatColor.GREEN + "[TicTacToe]" + ChatColor.RESET + " Version " + plugin.version);
                return false;
            }

            if(args[0].equalsIgnoreCase("reload")){
                if (!player.hasPermission("ttt.reload")) {
                    player.sendMessage(plugin.getLanguageString("config.perms"));
                    return false;
                }
                armorUtils.deleteHolo();
                plugin.reloadConfig();
                tablesLoc.reloadConfig();
                player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("config.reloaded"));
                System.out.println(plugin.getLanguageString("config.reloaded"));
                armorUtils.createHolo();
                return false;
            }

            if(args[0].equalsIgnoreCase("help")){
                player.sendMessage(ChatColor.YELLOW+"--------------------"+ChatColor.WHITE+"TicTacToe Help"+ChatColor.YELLOW+"--------------------");
                player.sendMessage(ChatColor.YELLOW+"/ttt version:"+ChatColor.WHITE+" Backpacks version");
                player.sendMessage(ChatColor.YELLOW+"/ttt accept:"+ChatColor.WHITE+" To accept an invitation");
                player.sendMessage(ChatColor.YELLOW+"/ttt reload:"+ChatColor.WHITE+" To reload backpack config files");
                player.sendMessage(ChatColor.YELLOW+"/ttt give:"+ChatColor.WHITE+" To give the TableMaker");
                player.sendMessage(ChatColor.YELLOW+"/ttt delete:"+ChatColor.WHITE+" To delete the game table you are looking at");
                return false;
            }

            if(args[0].equalsIgnoreCase("give")){
                if(!player.hasPermission("ttt.give")){
                    player.sendMessage(plugin.getLanguageString("config.perms"));
                    return false;
                }
                ItemStack nameTag = new ItemStack(Material.NAME_TAG);
                ItemMeta tagMeta = nameTag.getItemMeta();

                tagMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "TableMaker");
                nameTag.setItemMeta(tagMeta);
                player.getInventory().addItem(nameTag);
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
