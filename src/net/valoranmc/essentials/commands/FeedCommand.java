package net.valoranmc.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage("§7§oYou have been fed!");
				player.setSaturation(20);
				player.setFoodLevel(20);
			} else {
				sender.sendMessage("§c/feed <Player>");
			}
		} else if (args.length == 1) {
			if (sender.hasPermission("vmce.feed.others")) {
				Player player = Bukkit.getPlayer(args[0]);
				
				if (player != null) {
					player.sendMessage("§7§oYou have been fed!");
					player.setSaturation(20);
					player.setFoodLevel(20);
					sender.sendMessage("§7§oYou have just solved world hunger.");
				} else {
					sender.sendMessage("§c§oPlayer not found");
				}
			} else {
				sender.sendMessage("§c§oYou don't have permission for that");
			}
		} else {
			if (sender instanceof Player) {
				sender.sendMessage("§c§o/feed [Player]");
			} else {
				sender.sendMessage("§c/feed <Player>");
			}
		}
		
		return true;
	}
	
}
