package ru.whitebeef.beeftimer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.whitebeef.beeftimer.Main;
import ru.whitebeef.beeftimer.utils.Utils;

public class BeefTimer implements CommandExecutor {
	Main plugin;

	public BeefTimer(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			if (Utils.hasPermission(s, "beeftimer.help"))
				Utils.sendMessage(s, "&cИспользуйте &6" + "/" + label + " &chelp для просмотра доступных команд!");
		}
		if (args[0].equalsIgnoreCase("help")) {
			Utils.sendMessages(s, "&6&lBeefTimer", "&6/" + label + " start &a- запустить таймер",
					"&6/" + label + " stop &a- остановить таймер",
					"&6/" + label + " continue &a- продолжить время таймера");
		} else if (args[0].equalsIgnoreCase("stop")) {
			if (Utils.hasPermission(s, "beeftimer.stop")) {
				if (plugin.stopTime == 0) {
					plugin.stopTime = System.currentTimeMillis();
					Utils.sendMessage(s, "&aТаймер успешно остановлен!");
				} else
					Utils.sendMessages(s, "&cТаймер уже остановлен!",
							"&eИспользуйте /" + label + " continue для продолжения!",
							"&eИспользуйте /" + label + " start для старта сначала!");
			}
		} else if (args[0].equalsIgnoreCase("start")) {
			if (Utils.hasPermission(s, "beeftimer.start")) {
				plugin.startTime = System.currentTimeMillis();
				plugin.stopTime = 0;
				Utils.sendMessage(s, "&aТаймер успешно запущен!");
			}
		} else if (args[0].equalsIgnoreCase("continue")) {
			if (Utils.hasPermission(s, "beeftimer.continue")) {
				if (plugin.stopTime != 0) {
					plugin.startTime = System.currentTimeMillis() - plugin.stopTime + plugin.startTime;
					plugin.stopTime = 0;
					Utils.sendMessage(s, "&aТаймер успешно продолжен!");
				} else
					Utils.sendMessages(s, "&cТаймер еще не остановлен!",
							"&eИспользуйте /" + label + " stop для остановки!");
			}
		}
		return true;
	}

}
