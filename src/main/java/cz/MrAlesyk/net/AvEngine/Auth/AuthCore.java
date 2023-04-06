package cz.MrAlesyk.net.AvEngine.Auth;

import cz.MrAlesyk.net.AvEngine.AvEngine;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthCore {

    public static void addPlayer(Player p, String password) throws SQLException {
        PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("SELECT * FROM `Auth_profiles` WHERE `username` = ?");
        ps.setString(1, p.getName());
        ResultSet rs = ps.executeQuery();
        if (rs.next() == false) {
            PreparedStatement ps1 = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement(
                    "INSERT INTO `Auth_profiles` VALUES (?, ?, ?, 1, 0, ?, ?, ?)");

            ps1.setString(1, p.getName()); // Jmeno
            ps1.setString(2, p.getUniqueId().toString()); // UUID
            ps1.setString(3, password); // HESLO
            ps1.setLong(4, 0); // CAS
            ps1.setString(5, p.getAddress().getHostString()); // REG IP
            ps1.setString(6, p.getAddress().getHostString()); // LAST IP
            ps1.executeUpdate();

        }
    }

    public static boolean isPlayerLogged(Player p) throws SQLException {
        PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("SELECT * FROM `Auth_profiles` WHERE `username` = ? AND `logged` = ?");
        ps.setString(1, p.getName());
        ps.setInt(2, 1);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static boolean isPlayerRegistered(Player p) throws SQLException {
        PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("SELECT * FROM `Auth_profiles` WHERE `username` = ?");
        ps.setString(1, p.getName());

        try {
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean checkPassword(Player p, String password) throws SQLException {
        PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("SELECT * FROM `Auth_profiles` WHERE `username` = ? AND `password` = ?");
        ps.setString(1, p.getName());
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static boolean setPlayerLogged(Player player, boolean isLogged) {
        try {
            PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("UPDATE `Auth_profiles` SET `logged` = ? WHERE `username` = ?");
            ps.setBoolean(1, isLogged);
            ps.setString(2, player.getName());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void unregister(Player p) throws SQLException {
        PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("DELETE FROM `Auth_profiles` WHERE `username` = ?");
        ps.setString(1, p.getName());
        ps.executeUpdate();
    }

    public static String getPlayerData(String data, Player p) throws SQLException {
        String dataResult = "0";

        PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("SELECT * FROM `Auth_profiles` WHERE `username` = ?");
        ps.setString(1, p.getName());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            dataResult = rs.getString(data);
        }

        return dataResult;
    }

    public static void setPlayerData(String data, Player p, String value) throws SQLException {

        try {
            PreparedStatement ps = AvEngine.getPlugin(AvEngine.class).getConnection().prepareStatement("UPDATE `Auth_profiles` SET " + data + " = ? WHERE `username` = ?");
            ps.setString(1, value);
            ps.setString(2, p.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
