package uk.co.byteindustries.bytecore.titlemanager;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static uk.co.byteindustries.bytecore.packet.PacketHandler.getNMSClass;
import static uk.co.byteindustries.bytecore.packet.PacketHandler.sendPacket;

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
public class SubtitleObject {

	public  int    fadeIn;
	public  int    stay;
	public  int    fadeOut;
	private String subtitle;

	public SubtitleObject(String subtitle, int fadeIn, int stay, int fadeOut) {
		this.subtitle = subtitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	/**
	 * Sends a subtitle packet to the player.
	 *
	 * @param player The player which the subtitle is being sent to.
	 */
	public void sendSubtitle(Player player) {
		try {
			Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
			Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
			Constructor<?> constructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
			Object packet = constructor.newInstance(enumTitle, chat, fadeIn, stay, fadeOut);
			sendPacket(player, packet);
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | NoSuchFieldException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the subtitle text.
	 *
	 * @return subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}
}
