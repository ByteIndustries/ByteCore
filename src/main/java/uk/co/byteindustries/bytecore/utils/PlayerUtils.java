package uk.co.byteindustries.bytecore.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import uk.co.byteindustries.bytecore.ByteCore;

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
public class PlayerUtils {

    /**
     * Clears the inventory and the armour of the player.
     *
     * @param player The player for the inventory to be cleared with.
     */
    public static void clearInventory(Player player) {
        clearInventory(player, true);
    }

    /**
     * Clears the inventory of the player (and armour if specified).
     *
     * @param player The player for the inventory to be cleared with.
     * @param armour If true, player's armour will also be cleared.
     */
    public static void clearInventory(Player player, boolean armour) {
        player.getInventory().clear();

        new Debugger(ByteCore.PLUGIN).log("Cleared inventory of %p (%u%)"
                .replace("%p", player.getName())
                .replace("%u", player.getUniqueId().toString()));

        clearArmour(player);
    }

    /**
     * Clears the armour of the player.
     *
     * @param player The player for the armour to be cleared with.
     */
    public static void clearArmour(Player player) {
        player.getInventory().setArmorContents(new ItemStack[4]);

        new Debugger(ByteCore.PLUGIN).log("Cleared armour of %p (%u%)"
                .replace("%p", player.getName())
                .replace("%u", player.getUniqueId().toString()));
    }

    /**
     * Heal the player by setting health and food level to maximum, whilst removing potion effects.
     *
     * @param player Represents the player that is healed.
     */
    public static void heal(Player player) {
        heal(player, player.getMaxHealth(), 20, true);
    }

    /**
     * @param player Represents the player that is healed.
     * @param health Represents a double value to which the player's health level is being set.
     */
    public static void heal(Player player, double health) {
        heal(player, health, 20, true);
    }

    /**
     * @param player Represents the player that is healed.
     * @param health Represents a double value to which the player's health level is being set.
     * @param food Represents an integer value to which the player's food level is being set.
     */
    public static void heal(Player player, double health, int food) {
        heal(player, health, food, true);
    }

    /**
     * @param player Represents the player that is healed.
     * @param health Represents a double value to which the player's health level is being set.
     * @param food Represents an integer value to which the player's food level is being set.
     * @param removeEffects If <code>true</code> all of the active potion effects on the player get removed.
     */
    public static void heal(Player player, double health, int food, boolean removeEffects) {
        player.setHealth(health);
        player.setFoodLevel(food);
        
        if (removeEffects) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }

    /**
     * Teleport the player to a specified location.
     * This is used in order to support WorldGuard.
     *
     * @param player the player to be teleported.
     * @param location where the player should be teleported.
     */
    public static void teleport(Player player, Location location) {
        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * Teleport the player to another player.
     * This is used in order to support WorldGuard.
     *
     * @param player the player to be teleported.
     * @param player2 the player to be teleported to.
     */
    public static void teleport(Player player, Player player2) {
        teleport(player, player2.getLocation());
    }

}
