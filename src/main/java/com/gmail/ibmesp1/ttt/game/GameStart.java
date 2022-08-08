package com.gmail.ibmesp1.ttt.game;

import com.gmail.ibmesp1.ibcore.guis.MenuItems;
import com.gmail.ibmesp1.ibcore.skull.PlayerSkull;
import com.gmail.ibmesp1.ttt.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameStart {

    private final TicTacToe plugin;
    private final int[] glass_slots;
    private final int[] game_slots;
    private final MenuItems menuItems;
    private final PlayerSkull pSkull;

    public GameStart(TicTacToe plugin) {
        this.plugin = plugin;
        glass_slots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 20, 24, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
        game_slots = new int[]{12, 13, 14, 21, 22, 23, 30, 31, 32};
        menuItems = new MenuItems();
        pSkull = new PlayerSkull();
    }

    public void gameStarts(Player player1,Player player2){

        Inventory TicTacToe = Bukkit.createInventory(null,5*9, plugin.getLanguageString("game.title"));

        for (int glass_slot : glass_slots) {
            TicTacToe.setItem(glass_slot, menuItems.glass());
        }

        for (int game_slot: game_slots) {
            TicTacToe.setItem(game_slot, menuItems.createItem(Material.LIME_STAINED_GLASS_PANE," ",null));
        }

        TicTacToe.setItem(19,pSkull.playerSkull(player1));
        TicTacToe.setItem(25,pSkull.playerSkull(player2));

        player1.openInventory(TicTacToe);
        player2.openInventory(TicTacToe);

        plugin.playerOne.put(player1.getUniqueId(),true);
        plugin.playerTwo.put(player2.getUniqueId(),false);
        plugin.player1C.put(player1.getUniqueId(),0);
        plugin.player2C.put(player2.getUniqueId(),0);
    }
}
