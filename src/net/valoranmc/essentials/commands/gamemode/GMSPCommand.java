package net.valoranmc.essentials.commands.gamemode;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMSPCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			GameMode gameMode = GameMode.SPECTATOR;
			
			if (GamemodeUtilities.checkPermission(player, gameMode)) {
				player.sendMessage("§7§oSet own game mode to §7" + gameMode.toString().toLowerCase());
				player.setGameMode(gameMode);
			} else {
				player.sendMessage("§c§oYou don't have permission to change to §c" + gameMode.toString().toLowerCase() + "§c§o mode!");
			}
		}
		
		return true;
	}
	
}
