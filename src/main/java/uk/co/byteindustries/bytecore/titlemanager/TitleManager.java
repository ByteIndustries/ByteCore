package uk.co.byteindustries.bytecore.titlemanager;


import org.bukkit.entity.Player;

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
public class TitleManager {

    /**
     * @param type Title type: TITLE, SUBTITLE, BOTH
     * @param player The player which the title is being sent to.
     * @param text Text that is being displayed as the title.
     * @param fadeIn Title fade in time.
     * @param stay Title stay time.
     * @param fadeOut Title fade out time.
     */
    public static void sendTitle(TitleType type, Player player, String text, int fadeIn, int stay, int fadeOut) {
        switch (type) {
            case TITLE:
                new TitleObject(text, fadeIn, stay, fadeOut).sendTitle(player);
                break;
            case SUBTITLE:
                new SubtitleObject(text, fadeIn, stay, fadeOut).sendSubtitle(player);
                break;
        }
    }
}
