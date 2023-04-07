package cz.MrAlesyk.net.AvEngine;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCommands.Login;
import cz.MrAlesyk.net.AvEngine.Auth.AuthCommands.Register;
import cz.MrAlesyk.net.AvEngine.Auth.AuthCommands.Unregister;
import cz.MrAlesyk.net.AvEngine.Auth.AuthListeners.DontDoStuff;
import cz.MrAlesyk.net.AvEngine.Auth.AuthListeners.PlayerJoin;
import cz.MrAlesyk.net.AvEngine.Auth.AuthListeners.PlayerQuit;
import cz.MrAlesyk.net.AvEngine.Auth.AuthRunnable.NotLoggedMessager;
import cz.MrAlesyk.net.AvEngine.Auth.AuthRunnable.NotRegisteredMessager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AvEngine extends JavaPlugin {

    public AvEngine plugin;

    private String host;
    private String port;
    String user;
    private String db;
    private String pass;
    public Connection connection;
    public PluginDescriptionFile pdf = getDescription();

    public AvEngine() {
        this.host = "77.242.89.4";
        this.port = "3306";
        this.user = "u2_YFif9C2cQ2";
        this.db = "s2_AvEngine";
        this.pass = "uxFY2NfXgDzSusFlJOUk+dm@";
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!this.isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.db + "?autoReconnect=true&useSSL=true", this.user, this.pass);
        }
    }

    public void disconnect() {
        if (this.isConnected()) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(Bukkit.getWorlds().contains(Bukkit.getWorld("Auth"))){
            new NotLoggedMessager().runTaskTimer(this, 0, 5*20);
            new NotRegisteredMessager().runTaskTimer(this, 0, 5*20);

            Bukkit.getPluginManager().registerEvents(new DontDoStuff(), this);
            Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
            Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);

            this.getCommand("login").setExecutor(new Login());
            this.getCommand("register").setExecutor(new Register());
            this.getCommand("unregister").setExecutor(new Unregister());
        } else if (Bukkit.getWorlds().contains(Bukkit.getWorld("Lobby"))){
            Bukkit.getCommandMap().getKnownCommands().remove("login");
            Bukkit.getCommandMap().getKnownCommands().remove("register");

            this.getCommand("unregister").setExecutor(new Unregister());
        }
    }

    @Override
    public void onDisable() {
        disconnect();
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
