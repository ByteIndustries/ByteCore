package uk.co.byteindustries.bytecore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.byteindustries.bytecore.event.ByteCoreDestroyEvent;
import uk.co.byteindustries.bytecore.event.ByteCoreInitializationEvent;
import uk.co.byteindustries.bytecore.utils.Debugger;

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

	public static  JavaPlugin PLUGIN;
	private static Debugger   debugger;

	private static boolean enabled = false;

	/**
	 * Initializes ByteCore.
	 *
	 * @param plugin The plugin that is using ByteCore.
	 */
	public static void initializeByteCore(JavaPlugin plugin) {
		if (enabled) {
			return;
		}

		PLUGIN = plugin;
		Bukkit.getServer().getPluginManager().callEvent(new ByteCoreInitializationEvent(PLUGIN));
		enabled = true;
	}

	/**
	 * Destroys ByteCore.
	 */
	public static void destroy() {
		if (! enabled) {
			return;
		}

		PLUGIN = null;
		Bukkit.getServer().getPluginManager().callEvent(new ByteCoreDestroyEvent());
		enabled = false;
	}

	/**
	 * Get the debugger for ByteCore to use.
	 *
	 * @return The debugger used by the plugin.
	 */
	public static Debugger getDebugger() {
		return debugger;
	}

	/**
	 * Sets the plugin's debugger for ByteCore to access.
	 *
	 * @param debugger The debugger that belongs to the plugin using ByteCore.
	 */
	public static void setDebugger(Debugger debugger) {
		ByteCore.debugger = debugger;
	}

}