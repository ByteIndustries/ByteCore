package uk.co.byteindustries.bytecore.game;

import org.bukkit.entity.Player;

import java.util.Arrays;
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
public abstract class Minigame {

	private String     name;
	private List<Team> teams;

	protected Minigame(String name, Team... teams) {
		this.name = name;
		this.teams = Arrays.asList(teams);
	}

	/**
	 * Get the name of the minigame.
	 *
	 * @return The name of the minigame.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the teams in the minigame.
	 *
	 * @return The teams.
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * Get the amount of players in the game.
	 *
	 * @return The amount of players.
	 */
	public int getPlayerAmount() {
		int amount = 0;

		for (Team team : teams) {
			amount += teams.size();
		}

		return amount;
	}

	/**
	 * Get the minimum amount of players required to start the game.
	 *
	 * @return The minimum playes required.
	 */
	public abstract int getMinimumPlayers();

	/**
	 * Get the maximum amount of players allowed in the game.
	 *
	 * @return The maximuma amount of supported players.
	 */
	public abstract int getMaximumPlayers();

	/**
	 * Get whether the game is allowed to start or not.
	 *
	 * @return
	 */
	public abstract boolean canGameStart();

	/**
	 * Get whether the player is in the game.
	 *
	 * @param player The Bukkit player to be checked.
	 * @return Whether the player is in the game.
	 */
	public boolean isPlayerInGame(Player player) {
		return isPlayerInGame(player.getName());
	}

	/**
	 * Get whether the player is in the game.
	 *
	 * @param name The Bukkit player to be checked.
	 * @return Whether the player is in the game.
	 */
	public boolean isPlayerInGame(String name) {
		for (Team team : teams) {
			if (team.isPlayerInTeam(name)) {
				return true;
			}
		}

		return false;
	}

}