package net.valoranmc.essentials.commands.tpo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPOHereCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				
				if (target != null) {
					player.sendMessage("§7§oTeleporting §9" + target.getDisplayName() + "§7§o to you");
					target.teleport(player);
				} else {
					sender.sendMessage("§c§oPlayer §c" + args[0] + "§c§o is not online!");
				}
			} else {
				sender.sendMessage("§c§o/tpohere <Player>");
			}
		} else {
			sender.sendMessage("§c§oOnly players can use this command!");
		}
		
		return true;
	}
	
}
