package com.gmail.ibmesp1.ttt.game;

import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ttt.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GameClick implements Listener {

    private final TicTacToe plugin;
    private final DataManager tablesLoc;

    public GameClick(TicTacToe plugin,DataManager tablesLoc) {
        this.plugin = plugin;
        this.tablesLoc = tablesLoc;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){

        Player player = e.getPlayer();
        Action action = e.getAction();
        List<String> gameTables = plugin.getConfig().getStringList("gameTables");

        ItemStack nameTag = new ItemStack(Material.NAME_TAG);
        ItemMeta tagMeta = nameTag.getItemMeta();

        tagMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "TableMaker");
        nameTag.setItemMeta(tagMeta);

        if (!(e.getHand() == EquipmentSlot.HAND)) {
            return;
        }

        if(!action.equals(Action.RIGHT_CLICK_BLOCK)){
            return;
        }

        if(e.getClickedBlock() == null){
            return;
        }

        Material block = e.getClickedBlock().getType();
        Location loc = e.getClickedBlock().getLocation();

        if(!player.getInventory().getItemInMainHand().equals(nameTag)) {
            if (plugin.gameInvitation.containsKey(player.getUniqueId()))
                return;

            if (checkTable(block, loc)) {
                player.sendMessage(plugin.getLanguageString("game.invite"));
                plugin.gameInvitation.put(player.getUniqueId(), true);
            }
            return;
        }

        if(!checkTable(block, loc)){
            for (String gameTable : gameTables) {
                if (block.equals(Material.getMaterial(gameTable))) {
                    String world = loc.getWorld().getName();
                    int x = loc.getBlockX();
                    int y = loc.getBlockY();
                    int z = loc.getBlockZ();

                    String path = world + "_" + x + "_" + y + "_" + z;

                    tablesLoc.getConfig().set("Locations." + path + ".worldType", loc.getWorld().getName());
                    tablesLoc.getConfig().set("Locations." + path + ".x", loc.getBlockX());
                    tablesLoc.getConfig().set("Locations." + path + ".y", loc.getBlockY());
                    tablesLoc.getConfig().set("Locations." + path + ".z", loc.getBlockZ());
                    tablesLoc.saveConfig();

                    Location floc = loc.add(+0.5, 0, +0.5);

                    ArmorStand title = floc.getWorld().spawn(floc, ArmorStand.class);

                    title.setGravity(false);
                    title.setGravity(false);
                    title.setCanPickupItems(false);
                    title.setCustomName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + plugin.getLanguageString("game.table.title"));
                    title.setCustomNameVisible(true);
                    title.setVisible(false);

                    Location sloc = floc.add(0, -0.3, 0);

                    ArmorStand subtitle = floc.getWorld().spawn(sloc, ArmorStand.class);

                    subtitle.setGravity(false);
                    subtitle.setGravity(false);
                    subtitle.setCanPickupItems(false);
                    subtitle.setCustomName(ChatColor.AQUA + plugin.getLanguageString("game.table.subtitle"));
                    subtitle.setCustomNameVisible(true);
                    subtitle.setVisible(false);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if(checkTable(block.getType(),block.getLocation())) {
            e.setCancelled(true);
            player.sendMessage(plugin.getLanguageString("config.delete"));
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
            player.sendMessage(ChatColor.RED + msg + plugin.getLanguageString("game.notOnline"));
            plugin.gameInvitation.remove(player.getUniqueId());
            return;
        }

        if(player == player2){
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("game.autoInvite"));
            plugin.gameInvitation.remove(player.getUniqueId());
            return;
        }

        player.sendMessage(plugin.getLanguageString("game.invited") + player2.getName());

        player2.sendMessage(player.getName() + plugin.getLanguageString("game.invitedBy"));
        player2.sendMessage(plugin.getLanguageString("game.60s"));

        plugin.players.put(player2.getUniqueId(),player.getUniqueId());

        plugin.gameInvitation.remove(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(plugin, ()->
                plugin.players.remove(player2.getUniqueId()),60*20);
        Bukkit.getScheduler().runTaskLater(plugin, ()->
                player2.sendMessage(plugin.getLanguageString("game.expired")),60*20);
    }

    private boolean checkTable(Material block,Location loc){
        if(plugin.getConfig().getStringList("gameTables") == null)
            return false;

        List<String> gameTables = plugin.getConfig().getStringList("gameTables");

        for (String gameTable : gameTables) {
            if (block.equals(Material.getMaterial(gameTable))) {
                String world = loc.getWorld().getName();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();

                String path = world + "_" + x + "_" + y + "_" + z;

                return tablesLoc.getConfig().contains("Locations." + path);
            }
        }
        return false;
    }
}
