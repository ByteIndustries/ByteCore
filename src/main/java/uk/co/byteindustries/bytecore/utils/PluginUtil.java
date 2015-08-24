package uk.co.byteindustries.bytecore.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

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
public class PluginUtil {

	/**
	 * Enables the specified plugin if it exists or it's not already enabled.
	 *
	 * @param plugin Plugin that is being enabled.
	 */
	public static void enablePlugin(Plugin plugin) {
		if (plugin == null) {
			return;
		}

		if (plugin.isEnabled()) {
			return;
		}

		Bukkit.getServer().getPluginManager().enablePlugin(plugin);
	}

	/**
	 * Disables the specified plugin if it exists or it's not already disabled.
	 *
	 * @param plugin Plugin that is being disabled
	 */
	public static void disablePlugin(Plugin plugin) {
		if (plugin == null) {
			return;
		}

		if (! plugin.isEnabled()) {
			return;
		}

		Bukkit.getServer().getPluginManager().disablePlugin(plugin);
	}

	/**
	 * Enables all disabled plugins on the server.
	 */
	public static void enableAll() {
		for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
			enablePlugin(plugin);
		}
	}

	/**
	 * Disables all enabled plugins on the server.
	 */
	public static void disableAll() {
		for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
			disablePlugin(plugin);
		}
	}
}
