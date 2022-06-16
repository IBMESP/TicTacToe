package com.gmail.ibmesp1.command;

import com.gmail.ibmesp1.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TicCommand implements CommandExecutor {

    private final TicTacToe plugin;
    private final int[] glass_slots;
    private final int[] game_slots;

    public TicCommand(TicTacToe plugin) {
        this.plugin = plugin;
        glass_slots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 20, 24, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
        game_slots = new int[]{12, 13, 14, 21, 22, 23, 30, 31, 32};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){return false;}

        Player player1 = (Player) sender;

        if(args.length == 1){
            Player player2 = Bukkit.getPlayer(args[0]);

            if(player2 == null){
                player1.sendMessage(ChatColor.RED + args[0] + plugin.getLanguageString("notOnline"));
                return false;
            }

            if(player1 == player2){
                player1.sendMessage(ChatColor.RED + plugin.getLanguageString("autoInvite"));
                return false;
            }

            Inventory TicTacToe = Bukkit.createInventory(null,5*9,plugin.getLanguageString("game.title"));

            for (int glass_slot : glass_slots) {
                TicTacToe.setItem(glass_slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            }

            for (int game_slot: game_slots) {
                TicTacToe.setItem(game_slot, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
            }

            ItemStack head1 = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta1 = (SkullMeta) head1.getItemMeta();
            skullMeta1.setOwningPlayer(player1);
            head1.setItemMeta(skullMeta1);

            ItemStack head2 = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta2 = (SkullMeta) head2.getItemMeta();
            skullMeta2.setOwningPlayer(player2);
            head2.setItemMeta(skullMeta2);

            TicTacToe.setItem(19,head1);
            TicTacToe.setItem(25,head2);

            player1.openInventory(TicTacToe);
            player2.openInventory(TicTacToe);

            plugin.playerOne.put(player1.getUniqueId(),true);
            plugin.playerTwo.put(player2.getUniqueId(),false);
        }

        return false;
    }
}
