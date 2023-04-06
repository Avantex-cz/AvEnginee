package cz.MrAlesyk.net.AvEngine.Auth.AuthListeners;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCore;
import cz.MrAlesyk.net.AvEngine.Auth.AuthLang;
import cz.MrAlesyk.net.AvEngine.AvEngine;
import cz.MrAlesyk.net.AvEngine.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerJoin implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.teleport(new Location(Bukkit.getWorld("Auth"), -0.520, 64, -17.743, -0, -1));
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2);

        try {
            if(player.getAddress().getHostString().equalsIgnoreCase(AuthCore.getPlayerData("register-ip", player))){
                Utils.ClearChat(player);

                player.sendMessage(Utils.c(AuthLang.authcore_automatically_logged));
                Utils.redirectPlayer(player, "Lobby01");
                AuthCore.setPlayerLogged(player, true);
            } else {
                Utils.ClearChat(player);
                player.sendMessage(Utils.c(AuthLang.authcore_register_request));

                new BukkitRunnable() {
                    private int countdown = 60;

                    @Override
                    public void run() {
                        countdown--;

                            if (countdown <= 0) {
                                cancel();
                                player.kickPlayer(Utils.c("&8| &cAccount &8)) &7Čas na přihlášení vypršel! Zkuste to znovu."));
                                return;
                            }

                        try {
                            if (!AuthCore.isPlayerRegistered(player)) {
                                player.sendActionBar(Utils.c("&8&l| &c&lAccount &8&l)) &7&lZbývající čas na registrování: &c&l" + countdown + "s&7&l!"));
                            } else {
                                player.sendActionBar(Utils.c("&8&l| &c&lAccount &8&l)) &7&lZbývající čas na přihlášení: &c&l" + countdown + "s&7&l!"));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }.runTaskTimer(AvEngine.getPlugin(AvEngine.class), 0L, 20L);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        event.setJoinMessage(null);
    }
}
