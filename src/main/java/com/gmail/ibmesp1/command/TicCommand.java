package com.gmail.ibmesp1.command;

import com.gmail.ibmesp1.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TicCommand implements CommandExecutor {

    private final TicTacToe plugin;
    private final int[] glass_slots;
    private int[] game_slots;

    public TicCommand(TicTacToe plugin) {
        this.plugin = plugin;
        glass_slots = new int[]{0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20, 24, 25, 26};
        game_slots = new int[]{3, 4, 5, 12, 13, 14, 21, 22, 23};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){return false;}

        Player player1 = (Player) sender;

        if(args.length == 1){
            Player player2 = Bukkit.getPlayer(args[0]);

            Inventory TicTacToe = Bukkit.createInventory(null,3*9,plugin.getLanguageString("table.title"));

            for (int glass_slot : glass_slots) {
                TicTacToe.setItem(glass_slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            }

            for (int game_slot: game_slots) {
                TicTacToe.setItem(game_slot, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
            }

            player1.openInventory(TicTacToe);
            player2.openInventory(TicTacToe);

            plugin.playerOne.put(player1.getUniqueId(),true);
            plugin.playerTwo.put(player2.getUniqueId(),false);
        }

        return false;
    }
}
