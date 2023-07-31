# TicTacToe
Spigot plugin that adds TicTacToe
## Badges:
[![Latest Version](https://img.shields.io/badge/Latest%20Version-1.0.3-brightgreen)](https://github.com/IBMESP/TicTacToe/releases/latest)

![](https://i.imgur.com/zQXHvoK.png)

![](https://i.imgur.com/sC21XMr.png)
- **TableMaker** to create Game Tables
- **Configure** which blocks can be converted to a Game Table
- **[Multiple languages](https://github.com/IBMESP/TicTacToe/wiki)**

![](https://i.imgur.com/NiJ0vLF.png)
![](https://media0.giphy.com/media/ssfZvuYtWrcCoQrd40/giphy.gif?cid=790b7611b5ef57b923c37af2287dc75b1d7729ecb6cbb1b9&rid=giphy.gif&ct=g)

![](https://i.imgur.com/D6Dpq8w.png)
- **Permission:** ttt.delete →
    - **Commands:**
        - /ttt delete : Deletes the game table you are looking at
- **Permission:** ttt.reload →
    - **Commands:**
        - /ttt reload : Reloads tablesLoc.yml and config.yml
- **Permission:** ttt.give →
    - **Commands:**
        - /ttt give : Gives the Table Maker
        
![](https://i.imgur.com/x67FxK2.png)
<details>
  <summary>Default config.yml</summary>

  ```
  #Available languages
  #en_US
  #es_ES
  locale: en_US

  # This is the en_US.yml version for reference.
  # ONLY EDIT ONCE ALL LANGUAGE FILES HAVE BEEN UPDATED.
  languageFile: 3

  # Blocks that you can convert into a game table
  # If you want to add a block you can get the id from here https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
  gameTables:
    - EMERALD_BLOCK
    - CHISELED_STONE_BRICKS
  ```
</details>
<details>
  <summary>Default en_US.yml</summary>

  ```
  game:
    title: "TicTacToe"
    turn: "Is not your turn"
    tie: "Tie"
    win: "You win"
    lose: "You lose"
    invite: "Write the name of the player you want to invite"
    invitedBy : " has invited you to a game, /ttt accept to accept the invitation"
    invited: "You invited "
    60s: "You have 60 seconds to accept the invitation"
    expired: "The invitation has expired"
    noInvitation: "You don't have any invitation"
    table:
      title: "Game Table"
      subtitle: "Click to invite a player"
  notOnline: " is not online"
  autoInvite: "You can not invite yourself"
  config:
    reloaded: "[TicTacToe] Config reloaded!"
    perms: "You do not have permission to use this command"
    help: "Use /ttt help to see the commands"
    update: "TicTacToe has a new update"
    notUpdate: "TicTacToe is up to date"
    console: "Only a player can use this command"
    delete: "Use /ttt delete"
  ```
</details>
