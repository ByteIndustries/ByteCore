package uk.co.byteindustries.bytecore.game;

import org.bukkit.DyeColor;
import org.bukkit.event.Listener;

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
public abstract class GameStage implements Listener {

	protected GameStage() {

	}

	/**
	 * Get the name of the stage.
	 *
	 * @return The name of the stage.
	 */
	public abstract String getName();

	/**
	 * Get whether the stage is joinable or not.
	 *
	 * @return Whether the game can be joined during the stage.
	 */
	public abstract boolean getJoinable();

	/**
	 * Get the dye color of the stage.
	 * Intended usage was for blocks to show game status.
	 *
	 * @return The dye color.
	 */
	public abstract DyeColor getDyeColour(); // it hurts to use color instead of Colour

}