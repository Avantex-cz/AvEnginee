package cz.MrAlesyk.net.AvEngine.Auth.AuthRunnable;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCore;
import cz.MrAlesyk.net.AvEngine.Auth.AuthLang;
import cz.MrAlesyk.net.AvEngine.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class NotLoggedMessager extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                if(AuthCore.isPlayerRegistered(player)){
                    if(!AuthCore.isPlayerLogged(player)) {
                        player.sendMessage(Utils.c(AuthLang.authcore_login_request));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
