package ru.whitebeef.beeftimer;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import ru.whitebeef.beeftimer.commands.BeefTimer;

public class Main extends JavaPlugin implements Runnable, Listener {

	public boolean isStart = false;
	public long startTime;
	public long stopTime = 0;
	private ArrayList<Player> deathPlayers = new ArrayList<Player>();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		deathPlayers.add(e.getEntity().getPlayer());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (isStart)
			return;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this, 0, 1);
		isStart = true;
		startTime = System.currentTimeMillis();

	}

	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent e) {
		if (!deathPlayers.contains(e.getPlayer()))
			stopTime = System.currentTimeMillis();
		else
			deathPlayers.remove(e.getPlayer());

	}

	@Override
	public void onEnable() {
		Bukkit.getPluginCommand("beeftimer").setExecutor(new BeefTimer(this));
		Bukkit.getPluginManager().registerEvents(this, this);
		if (!Bukkit.getOnlinePlayers().isEmpty()) {
			isStart = true;
			startTime = System.currentTimeMillis();
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this, 0, 1);
		}
	}

	@Override
	public void run() {
		long timeLeft;
		if (stopTime == 0)
			timeLeft = System.currentTimeMillis() - startTime;
		else
			timeLeft = stopTime - startTime;
		long milis = timeLeft % 1000;
		long secs = (timeLeft / 1000) % 60;
		long mins = (timeLeft / 60000) % 60;
		long hours = (timeLeft / 3600000) % 60;
		if (stopTime == 0)
			Bukkit.getOnlinePlayers()
					.forEach(player -> sendAction(player, ChatColor.translateAlternateColorCodes('&', "&f&l⏵ &c&l[&f&l"
							+ hours + " &c&l: &f&l" + mins + " &c&l: &f&l" + secs + " &c&l: &f&l" + milis + "&c&l]")));
		else
			Bukkit.getOnlinePlayers()
					.forEach(player -> sendAction(player, ChatColor.translateAlternateColorCodes('&', "&f&l|| &e&l[&f&l"
							+ hours + " &e&l: &f&l" + mins + " &e&l: &f&l" + secs + " &e&l: &f&l" + milis + "&e&l]")));

	}

	public void sendAction(Player player, String msg) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(msg).create());
	}
}
