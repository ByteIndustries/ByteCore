package uk.co.byteindustries.bytecore.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

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
public class Chat {

	//Place holder TODO: Change
	public static final String HEADER = "=====================================================";
	//Place holder TODO: Change
	public static final String FOOTER = "=====================================================";

	/**
	 * Sends a message to the entire server.
	 *
	 * @param message The message that is being sent.
	 */
	public static void broadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers())
			sendMessage(player, message);

	}

	/**
	 * Sends a message to the specified player.
	 *
	 * @param receiver The player that the message is being sent to.
	 * @param message The message that is being sent to the player.
	 */
	public static void sendMessage(Player receiver, String message) {
		receiver.sendMessage(message);
	}

	/**
	 * Sends a message to another player if he's not on the same server as the sender.
	 *
	 * @param plugin The plugin that is using the method. Usually your main plugin class.
	 * @param sender The player that is sending the message.
	 * @param receiver The player that is receiving the message.
	 * @param message The message that is being sent.
	 */
	public static void sendCrossServer(Plugin plugin, Player sender, Player receiver, String message) {
		sendCrossServer(plugin, sender, receiver.getName(), message);
	}

	/**
	 * Sends a message to another player if he's not on the same server as the sender.
	 *
	 * @param plugin The plugin that is using the method. Usually your main plugin class.
	 * @param sender The player that is sending the message.
	 * @param receiver The player that is receiving the message.
	 * @param message The message that is being sent.
	 */
	public static void sendCrossServer(Plugin plugin, Player sender, String receiver, String message) {
		ByteArrayOutputStream b      = new ByteArrayOutputStream();
		DataOutputStream      stream = new DataOutputStream(b);

		try {
			stream.writeUTF("Message");
			stream.writeUTF(receiver);
			stream.writeUTF(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		sender.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}

}
