package net.valoranmc.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.getAllowFlight()) {
				player.sendMessage("§c§oYou are no longer flying!");
			} else {
				player.sendMessage("§9§oYou are now flying!");
			}
			
			player.setAllowFlight(!player.getAllowFlight());
		} else {
			sender.sendMessage("*throws server up in the air*");
		}
		
		return true;
	}

	
	
}
