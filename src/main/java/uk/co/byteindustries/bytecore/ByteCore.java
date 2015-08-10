package uk.co.byteindustries.bytecore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.byteindustries.bytecore.event.ByteCoreInitializationEvent;

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
public class ByteCore {

    public static JavaPlugin PLUGIN;

    public static void initializeByteCore(JavaPlugin plugin) {
        PLUGIN = plugin;
        Bukkit.getServer().getPluginManager().callEvent(new ByteCoreInitializationEvent(PLUGIN));
    }
}