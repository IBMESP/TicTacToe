package com.gmail.ibmesp1.game;

import com.gmail.ibmesp1.TicTacToe;
import com.gmail.ibmesp1.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameClick implements Listener {

    private final TicTacToe plugin;
    private final GameStart gameStart;
    private final DataManager tablesLoc;

    public GameClick(TicTacToe plugin, GameStart gameStart,DataManager tablesLoc) {
        this.plugin = plugin;
        this.gameStart = gameStart;
        this.tablesLoc = tablesLoc;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){

        Player player = e.getPlayer();
        Material block = e.getClickedBlock().getType();
        Action action = e.getAction();
        ItemStack conv = new ItemStack(Material.NAME_TAG);
        Location loc = e.getClickedBlock().getLocation();
        int tables = tablesLoc.getConfig().getInt("tables");

        if(!action.equals(Action.RIGHT_CLICK_BLOCK)){
            return;
        }

        if(!player.getInventory().getItemInMainHand().equals(conv)) {

            if (plugin.gameInvitation.containsKey(player.getUniqueId())) {
                return;
            }

            if (checkTable(block, loc)) {
                player.sendMessage(plugin.getLanguageString("game.invite"));
                plugin.gameInvitation.put(player.getUniqueId(), true);
            }
            return;
        }

        if(!checkTable(block, loc)){
            tables++;

            tablesLoc.getConfig().set("tables", tables);

            tablesLoc.getConfig().set("Locations." + block.name() + tables + ".world", loc.getWorld().getName());
            tablesLoc.getConfig().set("Locations." + block.name() + tables + ".x", loc.getBlockX());
            tablesLoc.getConfig().set("Locations." + block.name() + tables + ".y", loc.getBlockY());
            tablesLoc.getConfig().set("Locations." + block.name() + tables + ".z", loc.getBlockZ());
            tablesLoc.saveConfig();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String msg = e.getMessage();

        if(!plugin.gameInvitation.containsKey(player.getUniqueId())){
            return;
        }

        e.setCancelled(true);

        Player player2 = Bukkit.getPlayer(msg);

        if(player2 == null){
            player.sendMessage(ChatColor.RED + msg + plugin.getLanguageString("notOnline"));
            return;
        }

        if(player == player2){
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("autoInvite"));
            return;
        }

        player2.sendMessage(player.getName() + plugin.getLanguageString("game.invitedBy"));

        plugin.players.put(player2.getUniqueId(),player.getUniqueId());

        plugin.gameInvitation.remove(player.getUniqueId());
    }

    private boolean checkTable(Material block,Location loc){

        List<String> gameTables = plugin.getConfig().getStringList("gameTables");

        for (String gameTable : gameTables) {
            if (block.equals(Material.getMaterial(gameTable))) {

                int tables = tablesLoc.getConfig().getInt("tables");

                for (int i = 0; i < tables; i++) {

                    String world = tablesLoc.getConfig().getString("Locations." + block.name() + i + ".world");
                    int x = tablesLoc.getConfig().getInt("Locations." + block.name() + i + ".x");
                    int y = tablesLoc.getConfig().getInt("Locations." + block.name() + i + ".y");
                    int z = tablesLoc.getConfig().getInt("Locations." + block.name() + i + ".z");

                    try {
                        if (world.equals(loc.getWorld().getName()) && x == loc.getBlockX() && y == loc.getBlockY() && z == loc.getBlockZ()) {
                            return true;
                        }
                    } catch (NullPointerException ignored) {}
                }
            }
        }
        return false;
    }
}

