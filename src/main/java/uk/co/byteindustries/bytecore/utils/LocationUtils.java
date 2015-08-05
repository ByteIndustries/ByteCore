package uk.co.byteindustries.bytecore.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
public class LocationUtils {

    /**
     * Calculates the center point of the given location/block.
     *
     * @param location The location/block that the center is being calculated.
     * @return The center of the specified location/block.
     */
    public static Location getBlockCenter(Location location) {
        double xCen = location.getX() / 2;
        double zCen = location.getZ() / 2;

        return new Location(location.getWorld(), xCen, location.getY(), zCen);
    }

    /**
     * Gets the block relative to the given location. Direction pointers are represented as x, y, z.
     *
     * @param location Starting location
     * @param x        Adds into the x coordinate
     * @param y        Adds into the y coordinate
     * @param z        Adds into the z coordinate
     * @return The block relative to the specified location
     */
    public Location getBlockRelativeTo(Location location, int x, int y, int z) {
        return location.add(x, y, z);
    }

    /**
     * Gets the block relative to the given location. Direction pointers are represented as x, y, z.
     * In this case this is the player's location.
     *
     * @param player Starting location
     * @param x      Adds into the x coordinate
     * @param y      Adds into the y coordinate
     * @param z      Adds into the z coordinate
     * @return The block relative to the player location
     */
    public Location getBlockRelativeTo(Player player, int x, int y, int z) {
        return player.getLocation().add(x, y, z);
    }

    /**
     * Converts the specified location into a String type in the format: (x : y : z) <= world
     *
     * @param location The location that is being converted to a String type.
     * @return The location in form of a String
     */
    public static String toString(Location location) {
        World world = location.getWorld();
        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();

        String worldString = world.getName();
        String xString = Integer.toString(x);
        String yString = Integer.toString(y);
        String zString = Integer.toString(z);

        return "(" + xString + " : " + yString + " : " + zString + ") <= " + worldString;
    }

    /**
     * Gets the location of the specified player.
     *
     * @param player The player you are getting the location from.
     * @return Player location.
     */
    public static Location getPlayerLocation(Player player) {
        return player.getLocation();
    }

}
