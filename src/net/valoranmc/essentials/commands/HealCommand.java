package net.valoranmc.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage("§7§oYour health has been regenerated!");
				player.setHealth(20.0);
			} else {
				sender.sendMessage("§c/heal <Player>");
			}
		} else if (args.length == 1) {
			if (sender.hasPermission("vmce.heal.others")) {
				Player player = Bukkit.getPlayer(args[0]);
				
				if (player != null) {
					player.sendMessage("§7§oYour health has been regenerated!");
					player.setHealth(20.0);
					sender.sendMessage("§7§oPlayer has been healed");
				} else {
					sender.sendMessage("§c§oPlayer not found");
				}
			} else {
				sender.sendMessage("§c§oYou don't have permission for that");
			}
		} else {
			if (sender instanceof Player) {
				sender.sendMessage("§c§o/heal [Player]");
			} else {
				sender.sendMessage("§c/heal <Player>");
			}
		}
		
		return true;
	}
	
}
