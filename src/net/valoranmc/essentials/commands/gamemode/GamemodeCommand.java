package net.valoranmc.essentials.commands.gamemode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor, TabCompleter {
	private List<String> gamemodeOptions = Arrays.asList("adventure","creative","survival","spectator");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 2) {
			if (sender.hasPermission("vmce.gamemode.command.others")) {
				GameMode gameMode = null;
				Player player = null;
				
				if (GamemodeUtilities.parseGameMode(args[0]) != null) {
					gameMode = GamemodeUtilities.parseGameMode(args[0]);
					player = Bukkit.getPlayer(args[1]);
				} else if (GamemodeUtilities.parseGameMode(args[1]) != null) {
					gameMode = GamemodeUtilities.parseGameMode(args[1]);
					player = Bukkit.getPlayer(args[0]);
				}
				
				if (gameMode != null) {
					if (player != null) {
						sender.sendMessage("§7§oSet game mode of §7" + player.getDisplayName() + "§7§o to §7" + gameMode.toString().toLowerCase());
						player.sendMessage("§7§oSet game mode to §7" + gameMode.toString().toLowerCase());
						player.setGameMode(gameMode);
					} else {
						sender.sendMessage("§c§oCould not find player with that name!");
					}
				} else {
					sender.sendMessage("§c§oGamemode does not exist!");
				}
			} else {
				sender.sendMessage("§c§oYou don't have permisson to set the gamemode of others!");
			}
		} else if (args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				GameMode gameMode = GamemodeUtilities.parseGameMode(args[0]);
				
				if (gameMode != null) {
					if (GamemodeUtilities.checkPermission(player, gameMode)) {
						player.sendMessage("§7§oSet own game mode to §7" + gameMode.toString().toLowerCase());
						player.setGameMode(gameMode);
					} else {
						player.sendMessage("§c§oYou don't have permission to change to §c" + gameMode.toString().toLowerCase() + "§c§o mode!");
					}
				} else {
					player.sendMessage("§c§oGamemode §c" + args[0] + "§c§o does not exist!");
				}
			}
		} else {
			if (sender instanceof Player) {
				sender.sendMessage("§c§o/gamemode " + (sender.hasPermission("vmce.gamemode.command.others") ? "[Player] " : "") + "<Gamemode>");
			} else {
				sender.sendMessage("§c§o/gamemode <Player> <Gamemode>");
			}
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> complete = new ArrayList<String>();
		if (args.length == 1) {
			for (String gamemodeOption : gamemodeOptions) {
				if (gamemodeOption.startsWith(args[0].toLowerCase())) {
					complete.add(gamemodeOption);
				}
			}
		} else if (args.length == 2) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				String name = player.getName();
				if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
					complete.add(name);
				}
			}
		}
		return complete;
	}
	
}
