package com.gmail.ibmesp1.utils;

import com.gmail.ibmesp1.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.management.BufferPoolMXBean;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ArmorStands {

    private final TicTacToe plugin;
    private final DataManager tablesLoc;

    public ArmorStands(TicTacToe plugin, DataManager tablesLoc) {
        this.plugin = plugin;
        this.tablesLoc = tablesLoc;
    }

    public void createHolo(){
        Set<String> section = tablesLoc.getConfig().getConfigurationSection("Locations").getKeys(false);

        for(String key:section){

            String[] parts = key.split("-");

            World world = Bukkit.getWorld(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);

            Location loc = new Location(world,x,y,z);

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

    public void deleteHolo(){

        Set<String> section = tablesLoc.getConfig().getConfigurationSection("Locations").getKeys(false);

        for(String key:section) {

            String[] parts = key.split("-");

            World world = Bukkit.getWorld(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);

            Location loc = new Location(world, x, y, z);

            Collection<Entity> entityList = loc.getWorld().getNearbyEntities(loc,1,1,1);

            for (Entity entity:entityList){
                if (entity.getType().equals(EntityType.ARMOR_STAND)){
                    entity.remove();
                }
            }
        }

    }
}
