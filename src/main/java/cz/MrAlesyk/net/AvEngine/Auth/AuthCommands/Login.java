package cz.MrAlesyk.net.AvEngine.Auth.AuthCommands;

import cz.MrAlesyk.net.AvEngine.Auth.AuthCore;
import cz.MrAlesyk.net.AvEngine.Auth.AuthLang;
import cz.MrAlesyk.net.AvEngine.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Login implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                if(AuthCore.isPlayerRegistered(player)){
                        if(!AuthCore.isPlayerLogged(player)){
                            if (args.length == 1) {
                                String password = args[0];
                                if (AuthCore.checkPassword(player, password)) {
                                    player.sendMessage(Utils.c(AuthLang.authcore_login_successfull));
                                    Utils.redirectPlayer(player, "Lobby01");
                                    AuthCore.setPlayerLogged(player, true);
                                    return true;
                                } else {
                                    player.sendMessage(Utils.c(AuthLang.authcore_wrong_password));
                                    return true;
                                }
                            } else {
                                player.sendMessage(Utils.c(AuthLang.authcore_login_syntax));
                                return true;
                            }
                        } else {
                            player.sendMessage(Utils.c(AuthLang.authcore_allready_logged));
                        }

                } else {
                    player.sendMessage(Utils.c(AuthLang.authcore_account_notRegistered));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
