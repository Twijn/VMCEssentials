package net.valoranmc.essentials.commands.tpo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPOCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				
				if (target != null) {
					player.sendMessage("§7§oTeleporting to §9" + target.getDisplayName());
					player.teleport(target);
				} else {
					sender.sendMessage("§c§oPlayer §c" + args[0] + "§c§l is not online!");
				}
			} else {
				sender.sendMessage("§c§o/tpo <Player>");
			}
		} else {
			sender.sendMessage("§c§oOnly players can use this command!");
		}
		
		return true;
	}
	
}