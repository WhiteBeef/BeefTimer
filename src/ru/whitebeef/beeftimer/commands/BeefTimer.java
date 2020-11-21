package ru.whitebeef.beeftimer.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import ru.whitebeef.beeftimer.Main;
import ru.whitebeef.beeftimer.utils.Utils;

public class BeefTimer implements TabExecutor {
	Main plugin;

	public BeefTimer(Main plugin) {
		this.plugin = plugin;
	}

	static List<String> words = new ArrayList<String>();

	static {
		String[] array = { "help", "stop", "continue", "start" };
		words.addAll(Arrays.asList(array));
	}

	@Override
	public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s,
			@Nonnull String[] args) {
		switch (args.length) {
		case 1:
			return words;
		default:
			return new ArrayList<>();
		}
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			if (Utils.hasPermission(s, "beeftimer.help"))
				Utils.sendMessage(s, "&c����������� &6" + "/" + label + " &chelp ��� ��������� ��������� ������!");
		}
		if (args[0].equalsIgnoreCase("help")) {
			Utils.sendMessages(s, "&6&lBeefTimer", "&6/" + label + " start &a- ��������� ������",
					"&6/" + label + " stop &a- ���������� ������",
					"&6/" + label + " continue &a- ���������� ����� �������");
		} else if (args[0].equalsIgnoreCase("stop")) {
			if (Utils.hasPermission(s, "beeftimer.stop")) {
				if (plugin.stopTime == 0) {
					plugin.stopTime = System.currentTimeMillis();
					Utils.sendMessage(s, "&a������ ������� ����������!");
				} else
					Utils.sendMessages(s, "&c������ ��� ����������!",
							"&e����������� /" + label + " continue ��� �����������!",
							"&e����������� /" + label + " start ��� ������ �������!");
			}
		} else if (args[0].equalsIgnoreCase("start")) {
			if (Utils.hasPermission(s, "beeftimer.start")) {
				plugin.startTime = System.currentTimeMillis();
				plugin.stopTime = 0;
				Utils.sendMessage(s, "&a������ ������� �������!");
			}
		} else if (args[0].equalsIgnoreCase("continue")) {
			if (Utils.hasPermission(s, "beeftimer.continue")) {
				if (plugin.stopTime != 0) {
					plugin.startTime = System.currentTimeMillis() - plugin.stopTime + plugin.startTime;
					plugin.stopTime = 0;
					Utils.sendMessage(s, "&a������ ������� ���������!");
				} else
					Utils.sendMessages(s, "&c������ ��� �� ����������!",
							"&e����������� /" + label + " stop ��� ���������!");
			}
		}
		return true;
	}

}
