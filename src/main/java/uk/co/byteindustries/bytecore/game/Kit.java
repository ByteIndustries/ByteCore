package uk.co.byteindustries.bytecore.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/************************************************************
 *   ______     _________ ______ _____ ____  _____  ______  *
 *  |  _ \ \   / /__   __|  ____/ ____/ __ \|  __ \|  ____| *
 *  | |_) \ \_/ /   | |  | |__ | |   | |  | | |__) | |__    *
 *  |  _ < \   /    | |  |  __|| |   | |  | |  _  /|  __|   *
 *  | |_) | | |     | |  | |___| |___| |__| | | \ \| |____  *
 *  |____/  |_|     |_|  |______\_____\____/|_|  \_\______| *
 *                                                          *
 ************************************************************
 * Author: Byte Industries      License: Apache License 2.0 *
 ************************************************************/
public class Kit {

    private String name;
    private boolean unlocked = false;
    private ItemStack[] contents;

    public Kit(String name, ItemStack... contents) {
        this.name = name;
        this.contents = contents;
    }

    /**
     * @return The name of the kit.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The contents of the kit.
     */
    public ItemStack[] getContents() {
        return contents;
    }

    /**
     * @return If the kit is unlocked or not.
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Sets if the kit is unlocked or not.
     *
     * @param unlocked Switch
     */
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    /**
     * Gives the kit to the player.
     *
     * @param player The player that the kit is given to.
     */
    public void give(Player player) {
        if (unlocked) {
            for (ItemStack content : contents) {
                player.getInventory().setItem(player.getInventory().firstEmpty(), content);
            }
            player.sendMessage(ChatColor.GREEN + "Received kit " + ChatColor.UNDERLINE + ChatColor.DARK_GREEN + name + ChatColor.GREEN + "!");
        } else {
            player.sendMessage(ChatColor.RED + "Kit " + ChatColor.UNDERLINE +  ChatColor.DARK_RED+ name + ChatColor.RED + " is not unlocked!");
        }
    }
}
