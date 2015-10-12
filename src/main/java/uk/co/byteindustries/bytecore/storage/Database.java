package uk.co.byteindustries.bytecore.storage;

import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import uk.co.byteindustries.bytecore.ByteCore;

import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

public abstract class Database {

    private HikariDataSource dataSource;

    public DataSource getDatasource(){
        return dataSource;
    }

    /**
     * Initialize a Database connection.
     *
     * @param className Database type to connect to.
     * @param jdbcURL JDBC url to connect to.
     * @param username Username to log in with.
     * @param password Password to authenticate user.
     */
    protected Database(String className, String jdbcURL, String username, String password) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcURL);

        config.setUsername(username);
        config.setPassword(password);

        config.setLeakDetectionThreshold(10000);
        config.setMaxLifetime(25000);
        config.setIdleTimeout(20000);
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(10000);
        config.setInitializationFailFast(false);

        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection c = getConnection();
        if (c == null) {
            ByteCore.PLUGIN.getLogger().severe("[ByteCore] " + ByteCore.PLUGIN.getName() + " was unable to connect to the database");
        } else
        {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close all current connections and disconnect from the database.
     */
    public void disconnect() {
        dataSource.close();
    }

    /**
     * Query the database.
     *
     * @param preparedStatement Query to submit.
     * @return A CachedRowSet with data from query, or null when an error has occurred.
     */
    public CachedRowSet query(final PreparedStatement preparedStatement) {

        try {
            ResultSet resultSet = preparedStatement.executeQuery();

            CachedRowSet cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(resultSet);
            resultSet.close();
            preparedStatement.getConnection().close();
            if(cachedRowSet.size() > 0) return cachedRowSet;
        } catch (SQLException e) {

            e.printStackTrace();
            try {
                preparedStatement.getConnection().close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Execute a statement async in the database.
     *
     * @param preparedStatement The statement to execute.
     */
    public void execute(final PreparedStatement preparedStatement) {
        Bukkit.getScheduler().runTaskAsynchronously(ByteCore.PLUGIN, new Runnable() {
            public void run() {
                try {
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        preparedStatement.close();
                        preparedStatement.getConnection().close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Put a given string query into a PreparedStatement object.
     *
     * @param query Query/statement to put into object.
     * @param vars Variables to switch ? with in order.
     * @return A PreparedStatement object.
     */
    public PreparedStatement prepareStatement(String query, String... vars) {
        try {
            Connection c = getConnection();
            PreparedStatement preparedStatement = c.prepareStatement(query);

            int x = 0;

            if (query.contains("?") && vars.length != 0) {
                for (String var : vars) {
                    x++;
                    preparedStatement.setString(x, var);
                }
            }

            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get a connection from the datapool.
     *
     * @return A connection, or null when a connection isn't available.
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}