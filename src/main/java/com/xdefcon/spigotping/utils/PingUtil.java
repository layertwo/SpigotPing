package com.xdefcon.spigotping.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PingUtil {
    public static int getPing(Player p) {
        p = Bukkit.getPlayer(p.getUniqueId());
        if (p != null) {
            try {
                return p.getPing();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        return 0;
    }
}
