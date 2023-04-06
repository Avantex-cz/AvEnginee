package cz.MrAlesyk.net.AvEngine.Auth.AuthListeners;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCore;
import cz.MrAlesyk.net.AvEngine.AvEngine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        AuthCore.setPlayerLogged(player, false);

        event.setQuitMessage(null);
    }
}
