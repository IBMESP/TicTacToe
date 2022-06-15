package com.gmail.ibmesp1.events;

import com.gmail.ibmesp1.TicTacToe;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TicEvents implements Listener {

    private final TicTacToe plugin;
    private final int[] glass_slots;
    private final ItemStack O;
    private final ItemStack X;
    private int player1Counter;
    private int player2Counter;

    private int[] h1;
    private int[] h2;
    private int[] h3;
    private int[] v1;
    private int[] v2;
    private int[] v3;
    private int[] d1;
    private int[] d2;

    public TicEvents(TicTacToe plugin) {
        this.plugin = plugin;
        O = new ItemStack(Material.BARRIER);
        X = new ItemStack(Material.STONE);
        glass_slots = new int[]{0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20, 24, 25, 26};

        h1 = new int[]{3,4,5};
        h2 = new int[]{12,13,14};
        h3 = new int[]{21,22,23};

        v1 = new int[]{3,12,21};
        v2 = new int[]{4,13,22};
        v3 = new int[]{5,14,23};

        d1 = new int[]{3,13,23};
        d2 = new int[]{5,13,21};
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        InventoryView inventoryView = e.getView();

        if (!inventoryView.getTitle().equals(plugin.getLanguageString("table.title"))) {
            return;
        }

        e.setCancelled(true);

        if (ArrayUtils.contains(glass_slots, e.getSlot())) {
            return;
        }

        Inventory TicTacToe = e.getInventory();
        List<HumanEntity> players = e.getViewers();

        Player player1 = (Player) players.get(0);
        Player player2 = (Player) players.get(1);

        Player player = (Player) e.getWhoClicked();

        if (plugin.playerOne.containsKey(player.getUniqueId())) {
            if (plugin.playerOne.get(player.getUniqueId())) {
                int slot = e.getSlot();

                TicTacToe.setItem(slot,O);

                plugin.playerOne.replace(player1.getUniqueId(), false);
                plugin.playerTwo.replace(player2.getUniqueId(), true);

                player1Counter++;

            } else {
                player.sendMessage("No es tu turno");
            }
        } else {
            if (plugin.playerTwo.get(player.getUniqueId())) {
                int slot = e.getSlot();

                TicTacToe.setItem(slot, X);

                plugin.playerOne.replace(player1.getUniqueId(), true);
                plugin.playerTwo.replace(player2.getUniqueId(), false);

                player2Counter++;
            } else {
                player.sendMessage("No es tu turno");
            }
        }

        if(player1Counter > 2){
            checkVictory(h1,player1,TicTacToe);
            checkVictory(h2,player1,TicTacToe);
            checkVictory(h3,player1,TicTacToe);
            checkVictory(v1,player1,TicTacToe);
            checkVictory(v2,player1,TicTacToe);
            checkVictory(v3,player1,TicTacToe);
            checkVictory(d1,player1,TicTacToe);
            checkVictory(d2,player1,TicTacToe);
        }else if(player2Counter > 2) {
            checkVictory(h1, player2, TicTacToe);
            checkVictory(h2, player2, TicTacToe);
            checkVictory(h3, player2, TicTacToe);
            checkVictory(v1, player2, TicTacToe);
            checkVictory(v2, player2, TicTacToe);
            checkVictory(v3, player2, TicTacToe);
            checkVictory(d1, player2, TicTacToe);
            checkVictory(d2, player2, TicTacToe);
        }
    }

    private void checkVictory(int[] cases,Player player,Inventory TicTacToe) {
        for (int slot : cases) {
            if (TicTacToe.getItem(slot).equals(O)) {
                if (slot == cases[2]) {
                    player.sendMessage("Victoria Magistral");
                }
            } else {
                break;
            }
        }
    }
}
