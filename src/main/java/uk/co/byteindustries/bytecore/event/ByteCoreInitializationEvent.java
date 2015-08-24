package uk.co.byteindustries.bytecore.event;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

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
public class ByteCoreInitializationEvent extends Event {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	private JavaPlugin plugin;

	public ByteCoreInitializationEvent(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
}
