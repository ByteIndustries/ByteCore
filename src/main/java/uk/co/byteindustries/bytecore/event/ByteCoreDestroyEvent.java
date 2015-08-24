package uk.co.byteindustries.bytecore.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
public class ByteCoreDestroyEvent extends Event {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	public ByteCoreDestroyEvent() {

	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
}
