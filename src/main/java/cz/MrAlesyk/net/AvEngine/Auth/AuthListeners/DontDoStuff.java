package cz.MrAlesyk.net.AvEngine.Auth.AuthListeners;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.sql.SQLException;

public class DontDoStuff implements Listener {

    @EventHandler
    public void on(PlayerMoveEvent event){
        Player player = event.getPlayer();

        try {
            if(!AuthCore.isPlayerLogged(player) || !AuthCore.isPlayerRegistered(player)){
                event.setCancelled(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent event){
        Player player = event.getPlayer();

        try {
            if(!AuthCore.isPlayerLogged(player) || !AuthCore.isPlayerRegistered(player)){
                event.setCancelled(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void on(BlockBreakEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void on(BlockPlaceEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void on(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void on(WeatherChangeEvent event){
        event.setCancelled(true);
    }
}
