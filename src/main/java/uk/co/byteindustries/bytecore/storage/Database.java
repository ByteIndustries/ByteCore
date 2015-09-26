package uk.co.byteindustries.bytecore.storage;

import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import uk.co.byteindustries.bytecore.ByteCore;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

public abstract class Database {

    private HikariDataSource dataSource;

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

        if (getConnection() == null) {
            ByteCore.PLUGIN.getLogger().severe("[ByteCore] " + ByteCore.PLUGIN.getName() + " was unable to connect to the database");
        }
    }

    /**
     * Close all current connections and disconnect from the database.
     */
    public void disconnect() {
        dataSource.shutdown();
    }

    /**
     * Query the database.
     *
     * @param preparedStatement Query to submit.
     * @return A CachedRowSet with data from query, or null when an error has occurred.
     */
    public CachedRowSet query(final PreparedStatement preparedStatement) {
        CachedRowSet rowSet = null;

        try {
            ExecutorService exe = Executors.newCachedThreadPool();

            Future<CachedRowSet> future = exe.submit(new Callable<CachedRowSet>() {
                public CachedRowSet call() {
                    try {
                        ResultSet resultSet = preparedStatement.executeQuery();

                        CachedRowSet cachedRowSet = new CachedRowSetImpl();
                        cachedRowSet.populate(resultSet);
                        resultSet.close();

                        if (cachedRowSet.next()) {
                            return cachedRowSet;
                        }
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

                    return null;
                }
            });

            if (future.get() != null) {
                rowSet = future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return rowSet;
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
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);

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