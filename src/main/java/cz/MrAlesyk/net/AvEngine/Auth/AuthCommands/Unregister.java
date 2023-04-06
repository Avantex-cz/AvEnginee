package cz.MrAlesyk.net.AvEngine.Auth.AuthCommands;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCore;
import cz.MrAlesyk.net.AvEngine.Auth.AuthLang;
import cz.MrAlesyk.net.AvEngine.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class Unregister implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(AuthCore.isPlayerRegistered(player)){
                    if (args.length == 1) {
                        String password = args[0];
                        if(AuthCore.checkPassword(player, password)){
                            AuthCore.unregister(player);
                            player.kickPlayer(Utils.c(AuthLang.authcore_unregistered));
                        } else {
                            player.sendMessage(Utils.c(AuthLang.authcore_wrong_password));
                        }
                    } else {
                        player.sendMessage(Utils.c(AuthLang.authcore_unregister_usage));
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.c(AuthLang.authcore_account_notRegistered));
                }
            } else {
                sender.sendMessage("Only players can use this command.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
