package uk.co.byteindustries.bytecore.storage;

import org.bukkit.plugin.java.JavaPlugin;

public class MySQLDatabase extends Database {

    /**
     * Initialize a MySQL database connection.
     *
     * @param host Host to connect to.
     * @param port Port to connect with, default is 3306.
     * @param database Database to access with MySQL.
     * @param username Username to log into MySQL with.
     * @param password Password to authenticate user, blank is accepted.
     */
    public MySQLDatabase(String host, int port, String database, String username, String password) {
        super("com.mysql.jdbc.Driver", "jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
    }

}