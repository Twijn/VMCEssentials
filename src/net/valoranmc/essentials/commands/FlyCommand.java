package net.valoranmc.essentials.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
	private static List<UUID> flyingList = new ArrayList<UUID>();
	
	public static boolean isFlying(UUID uuid) {
		return flyingList.contains(uuid);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.getAllowFlight()) {
				player.sendMessage("§7§oYou are no longer flying!");
				flyingList.remove(player.getUniqueId());
			} else {
				player.sendMessage("§7§oYou are now flying!");
				flyingList.add(player.getUniqueId());
			}
			
			player.setAllowFlight(!player.getAllowFlight());
		} else {
			sender.sendMessage("*throws server up in the air*");
		}
		
		return true;
	}

	
	
}
