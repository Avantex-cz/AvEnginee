package cz.MrAlesyk.net.AvEngine.Auth.AuthRunnable;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCore;
import cz.MrAlesyk.net.AvEngine.Auth.AuthLang;
import cz.MrAlesyk.net.AvEngine.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class NotRegisteredMessager extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                if(!AuthCore.isPlayerRegistered(player))
                    player.sendMessage(Utils.c(AuthLang.authcore_register_request));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
