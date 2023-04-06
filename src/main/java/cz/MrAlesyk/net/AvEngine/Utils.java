package cz.MrAlesyk.net.AvEngine;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.bukkit.Bukkit.getServer;

public class Utils {

    public static String clrs(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String c(String message) {
        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 32);

        while(matcher.find()) {
            String group = matcher.group(1);
            char var10002 = group.charAt(0);
            matcher.appendReplacement(buffer, "§x§" + var10002 + "§" + group.charAt(1) + "§" + group.charAt(2) + "§" + group.charAt(3) + "§" + group.charAt(4) + "§" + group.charAt(5));
        }

        return clrs(matcher.appendTail(buffer).toString());
    }

    public static void ClearChat(Player player){
        for (int i = 0; i < 25; i++) {
            player.sendMessage(c("&b "));
        }

    }

    public static void redirectPlayer(@NotNull Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(AvEngine.getPlugin(AvEngine.class), "BungeeCord", out.toByteArray());
    }

    public static int randInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
