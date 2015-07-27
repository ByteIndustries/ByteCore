package uk.co.byteindustries.bytecore.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class Debugger {

    private File       file;
    public  JavaPlugin plugin;

    public Debugger(JavaPlugin plugin) {
        initialize(plugin);
    }

    /**
     * Initialize the debugger.
     *
     * @param plugin the plugin, used for grabbing the data folder to store the debug message in.
     */
    private void initialize(JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "debug.txt");
        this.plugin = plugin;
    }

    /**
     * Log a message in the debug file.
     *
     * @param message Message to be logged.
     */
    public void log(String message) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));

            output.write("[" + plugin.getName() + "] " + getDateAndTime() + " " + message);

            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Grab the date and time in a readable String with the format [dd/MM/yy] [HH:mm:ss].
     *
     * @return date and time in a format.
     */
    private String getDateAndTime() {
        DateFormat df   = new SimpleDateFormat("[dd/MM/yy] [HH:mm:ss]");
        Date       date = new Date();

        return df.format(date);
    }

}