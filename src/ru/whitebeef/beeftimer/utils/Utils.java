package ru.whitebeef.beeftimer.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Utils {
	public static boolean hasPermission(Player p, String permission) {
		if (p.hasPermission(permission))
			return true;
		sendMessage(p, "&У вас недостаточно прав!");
		return false;
	}

	public static boolean hasPermission(CommandSender s, String permission) {
		if (s.hasPermission(permission))
			return true;
		sendMessage(s, "&У вас недостаточно прав!");
		return false;
	}

	public static void sendMessage(Player p, String message) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public static void sendMessage(CommandSender s, String message) {
		s.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public static void sendMessages(Player p, String... messages) {
		for (int i = 0; i < messages.length; i++)
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages[i]));
	}

	public static void sendMessages(CommandSender s, String... messages) {
		for (int i = 0; i < messages.length; i++)
			s.sendMessage(ChatColor.translateAlternateColorCodes('&', messages[i]));
	}
}
