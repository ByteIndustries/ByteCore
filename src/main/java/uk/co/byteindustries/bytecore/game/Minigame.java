package uk.co.byteindustries.bytecore.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.co.byteindustries.bytecore.ByteCore;
import uk.co.byteindustries.bytecore.utils.PluginUtils;

import java.util.ArrayList;
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

	private String          name;
	private List<Team>      teams;
	private List<GameStage> stages;
	private GameStage       currentStage;

	protected Minigame(String name, Team... teams) {
		this.name = name;
		this.teams = Arrays.asList(teams);
		this.stages = new ArrayList<GameStage>();
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
	 * Start the game.
	 */
	public abstract void start();

	/**
	 * Stop the game.
	 */
	public abstract void stop();

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

	/**
	 * Add the stage to the stages list.
	 *
	 * @param stage The stage to be added.
	 */
	public void addStage(GameStage stage) {
		stages.add(stage);
	}

	/**
	 * Remove a stage from the stages list.
	 *
	 * @param stage The stage to be removed.
	 */
	public void removeStage(GameStage stage) {
		if (stages.contains(stage)) {
			stages.remove(stage);
		}
	}

	/**
	 * Get the list of stages: this does not influence the game play (it is simply for API purpose).
	 *
	 * @return The list of stages.
	 */
	public List<GameStage> getStages() {
		return stages;
	}

	/**
	 * Get the current stage.
	 *
	 * @return The game's current stage.
	 */
	public GameStage getCurrentStage() {
		return currentStage;
	}

	/**
	 * Set the current stage.
	 *
	 * @param stage The stage to be set as current.
	 */
	public void setCurrentStage(GameStage stage) {
		if (currentStage != null) {
			PluginUtils.unregisterListener(currentStage);
		}

		Bukkit.getPluginManager().registerEvents(stage, ByteCore.PLUGIN);

		currentStage = stage;
	}

}