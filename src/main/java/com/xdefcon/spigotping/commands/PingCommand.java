package com.xdefcon.spigotping.commands;

import com.xdefcon.spigotping.utils.PingUtil;
import com.xdefcon.spigotping.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class PingCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public PingCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (args.length == 0) {
                if (!player.hasPermission("spigotping.ping")) {
                    String noPerm = plugin.getConfig().getString("permission-system.no-perm-message");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
                    return true;
                }
                String ping = "" + PingUtil.getPing(player);
                String customMex = plugin.getConfig().getString("ping-command.ping-message").replaceAll("%ping%", ping);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', customMex));
            } else {
                if (!player.hasPermission("spigotping.ping.others")) {
                    String noPerm = plugin.getConfig().getString("others-ping.not-allowed-message");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
                    return true;
                }
                Player targetP = Bukkit.getPlayer(args[0]);
                if (targetP == null) {
                    String noPlayer = plugin.getConfig().getString("others-ping.player-not-found");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',noPlayer));
                    return true;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("ping-command.ping-target-message")
                                .replace("%ping%", "" + PingUtil.getPing(targetP))
                                .replace("%target%", targetP.getName())));
            }
            if (plugin.getConfig().getBoolean("sound-manager.enabled")) {
                SoundUtil.playSound(player, plugin.getConfig().getString("sound-manager.sound-type"));
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command is only executable as a Player.");
        }
        return true;
    }
}

