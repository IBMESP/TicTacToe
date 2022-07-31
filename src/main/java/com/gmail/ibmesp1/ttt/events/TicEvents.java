package com.gmail.ibmesp1.ttt.events;

import com.gmail.ibmesp1.ibcore.skull.NonPlayerSkulls;
import com.gmail.ibmesp1.ttt.TicTacToe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class TicEvents implements Listener {

    private final TicTacToe plugin;
    private final ItemStack glass;
    private final ItemStack O;
    private final ItemStack X;
    private final ItemStack head;
    private int player1Counter;
    private int player2Counter;
    private NonPlayerSkulls nps;

    private final int[] h1;
    private final int[] h2;
    private final int[] h3;
    private final int[] v1;
    private final int[] v2;
    private final int[] v3;
    private final int[] d1;
    private final int[] d2;

    public TicEvents(TicTacToe plugin) {
        this.plugin = plugin;
        nps = new NonPlayerSkulls();
        O = nps.getSkull("NTU5MjAxZDhmNjZmMWVjMTNjMTgyMjNmMzYzNjgzMjRjZTY4ZjIwNmEyODE0NGZkM2RiMTVhMzQzNTE2YSJ9fX0=");
        X = nps.getSkull("NWE2Nzg3YmEzMjU2NGU3YzJmM2EwY2U2NDQ5OGVjYmIyM2I4OTg0NWU1YTY2YjVjZWM3NzM2ZjcyOWVkMzcifX19");

        SkullMeta OMeta = (SkullMeta) O.getItemMeta();
        OMeta.setDisplayName(ChatColor.BOLD + "O");
        O.setItemMeta(OMeta);

        SkullMeta XMeta = (SkullMeta) X.getItemMeta();
        XMeta.setDisplayName(ChatColor.BOLD + "X");
        X.setItemMeta(XMeta);

        glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        head = new ItemStack(Material.PLAYER_HEAD);

        h1 = new int[]{12,13,14};
        h2 = new int[]{21,22,23};
        h3 = new int[]{30,31,32};

        v1 = new int[]{12,21,30};
        v2 = new int[]{13,22,31};
        v3 = new int[]{14,23,32};

        d1 = new int[]{12,22,32};
        d2 = new int[]{14,22,30};
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        InventoryView inventoryView = e.getView();

        player1Counter = 0;
        player2Counter = 0;

        if (!inventoryView.getTitle().equals(plugin.getLanguageString("game.title"))) {
            return;
        }

        e.setCancelled(true);

        if (e.getCurrentItem().equals(glass) || e.getCurrentItem().equals(head)) {
            return;
        }

        if(e.getCurrentItem().equals(O) || e.getCurrentItem().equals(X)){
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

                player1Counter = plugin.player1C.get(player1.getUniqueId());
                player1Counter++;
                plugin.player1C.replace(player1.getUniqueId(),player1Counter);

            } else {
                player.sendMessage(plugin.getLanguageString("game.turn"));
            }
        } else {
            if (plugin.playerTwo.get(player.getUniqueId())) {
                int slot = e.getSlot();

                TicTacToe.setItem(slot, X);

                plugin.playerOne.replace(player1.getUniqueId(), true);
                plugin.playerTwo.replace(player2.getUniqueId(), false);

                player2Counter = plugin.player2C.get(player2.getUniqueId());
                player2Counter++;
                plugin.player2C.replace(player2.getUniqueId(),player2Counter);
            } else {
                player.sendMessage(plugin.getLanguageString("game.turn"));
            }
        }

        if(plugin.player1C.get(player1.getUniqueId()) > 2){
            if(checkVictory(h1,player1,player2,O,TicTacToe) || checkVictory(h2,player1,player2,O,TicTacToe) || checkVictory(h3,player1,player2,O,TicTacToe) ||
                    checkVictory(v1,player1,player2,O,TicTacToe) || checkVictory(v2,player1,player2,O,TicTacToe) || checkVictory(v3,player1,player2,O,TicTacToe) ||
                    checkVictory(d1,player1,player2,O,TicTacToe) || checkVictory(d2,player1,player2,O,TicTacToe)){
                endGame(player1,player2);
                plugin.gameFinished.put(player1.getUniqueId(),true);
                plugin.gameFinished.put(player2.getUniqueId(),true);
                player1.closeInventory();
                player2.closeInventory();
                return;
            }
        }

        if(plugin.player2C.get(player2.getUniqueId()) > 2) {
            if(checkVictory(h1,player2,player1,X,TicTacToe) || checkVictory(h2,player2,player1,X,TicTacToe) || checkVictory(h3,player2,player1,X,TicTacToe) ||
                    checkVictory(v1,player2,player1,X,TicTacToe) || checkVictory(v2,player2,player1,X,TicTacToe) || checkVictory(v3,player2,player1,X,TicTacToe) ||
                    checkVictory(d1,player2,player1,X,TicTacToe) || checkVictory(d2,player2,player1,X,TicTacToe)){
                endGame(player1,player2);
                plugin.gameFinished.put(player1.getUniqueId(),true);
                plugin.gameFinished.put(player2.getUniqueId(),true);
                player1.closeInventory();
                player2.closeInventory();
                return;
            }

            if(plugin.player1C.get(player1.getUniqueId()) == 5 && plugin.player2C.get(player2.getUniqueId()) == 4){
                endGame(player1,player2);
                plugin.gameFinished.put(player1.getUniqueId(),true);
                plugin.gameFinished.put(player2.getUniqueId(),true);
                player1.closeInventory();
                player2.closeInventory();
                player1.sendMessage(plugin.getLanguageString("game.tie"));
                player2.sendMessage(plugin.getLanguageString("game.tie"));
            }
        }
    }

    @EventHandler
    public void closeInv(InventoryCloseEvent e){
        InventoryView inventoryView = e.getView();

        if (!inventoryView.getTitle().equals(plugin.getLanguageString("game.title"))) {
            return;
        }

        if(plugin.gameFinished.containsKey(e.getPlayer().getUniqueId())){
            plugin.gameFinished.remove(e.getPlayer().getUniqueId());
            return;
        }

        List<HumanEntity> players = e.getViewers();

        Player player1 = (Player) players.get(0);
        Player player2 = (Player) players.get(1);

        Player player = (Player) e.getPlayer();

        if (plugin.playerOne.containsKey(player.getUniqueId())) {

            if(plugin.player1C.get(player1.getUniqueId()) == 0 && plugin.player2C.get(player2.getUniqueId()) == 0){
                player1.sendMessage(plugin.getLanguageString("playerQuit"));
                player2.sendMessage(plugin.getLanguageString("pQuit"));
                player2.closeInventory();
                return;
            }

            player1.sendMessage(plugin.getLanguageString("game.lose"));
            player2.sendMessage(plugin.getLanguageString("game.win"));

            plugin.gameFinished.put(player2.getUniqueId(),true);
            player2.closeInventory();

        } else if(plugin.playerTwo.containsKey(player.getUniqueId())){

            if(plugin.player1C.get(player1.getUniqueId()) == 0 && plugin.player2C.get(player2.getUniqueId()) == 0){
                player1.sendMessage(plugin.getLanguageString("pQuit"));
                player2.sendMessage(plugin.getLanguageString("playerQuit"));
                player1.closeInventory();
                return;
            }

            player2.sendMessage(plugin.getLanguageString("game.lose"));
            player1.sendMessage(plugin.getLanguageString("game.win"));

            plugin.gameFinished.put(player1.getUniqueId(),true);
            player1.closeInventory();
        }

        endGame(player1,player2);
    }

    private boolean checkVictory(int[] cases,Player playerW,Player playerL,ItemStack counter,Inventory TicTacToe) {
        for (int slot:cases) {
            ItemMeta counterMeta = TicTacToe.getItem(slot).getItemMeta();
            if(counterMeta.getDisplayName().equals(counter.getItemMeta().getDisplayName())){
                if(slot == cases[2]){
                    playerW.sendMessage(plugin.getLanguageString("game.win"));
                    playerL.sendMessage(plugin.getLanguageString("game.lose"));
                    return true;
                }
            }else{
                break;
            }
        }
        return false;
    }

    private void endGame(Player player1,Player player2){
        plugin.player1C.remove(player1.getUniqueId());
        plugin.player2C.remove(player2.getUniqueId());
        plugin.playerOne.remove(player1.getUniqueId());
        plugin.playerTwo.remove(player2.getUniqueId());
    }
}
