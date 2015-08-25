package uk.co.byteindustries.bytecore.game;

import org.bukkit.entity.Player;
import uk.co.byteindustries.bytecore.ByteCore;

import java.util.ArrayList;
import java.util.List;

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
public class Team {

	private String       name;
	private List<String> players;

	public Team(String name) {
		this.name = name;
		this.players = new ArrayList<String>();
	}

	/**
	 * Get the name of the team.
	 *
	 * @return The name of the team.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the players in the team.
	 *
	 * @return The ArrayList of players in the team.
	 */
	public List<String> getPlayers() {
		return this.players;
	}

	/**
	 * Add a player to the team.
	 *
	 * @param player The Bukkit player to be added.
	 */
	public void addPlayer(Player player) {
		addPlayer(player.getName());
	}

	/**
	 * Add a player to the team.
	 *
	 * @param name The name of the player to be added.
	 */
	public void addPlayer(String name) {
		players.add(name);

		ByteCore.getDebugger().log("Added " + name + " to team " + name);
	}

	/**
	 * Remove a player from the team.
	 *
	 * @param player The Bukkit player to be removed.
	 */
	public void removePlayer(Player player) {
		removePlayer(player.getName());
	}

	/*
	 * Remove a player from the team.
	 *
	 * @param name The name of the player to be removed.
	 */
	public void removePlayer(String name) {
		if (players.contains(name)) {
			players.remove(name);
		}

		ByteCore.getDebugger().log("Removed " + name + " from team " + name);
	}

	/**
	 * Get if the player is in the team.
	 *
	 * @param name The name of the player.
	 * @return Whether the player is in the team or not.
	 */
	public boolean isPlayerInTeam(String name) {
		return players.contains(name);
	}

}