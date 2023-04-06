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

public class Register implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(!AuthCore.isPlayerRegistered(player)){
                    if (args.length == 1) {
                        String password = args[0];

                        player.sendMessage(Utils.c(AuthLang.authcore_register_proccesing));
                        AuthCore.addPlayer(player, password);
                        player.sendMessage(Utils.c(AuthLang.authcore_register_successfull));
                        Utils.redirectPlayer(player, "Lobby01");
                    } else {
                        player.sendMessage(Utils.c(AuthLang.authcore_register_usage));
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.c(AuthLang.authcore_register_already));
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
