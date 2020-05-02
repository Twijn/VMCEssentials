package net.valoranmc.essentials.commands.gamemode;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

public class GamemodeUtilities {

	public static boolean checkPermission(CommandSender sender, GameMode gamemode) {
		return sender.hasPermission("vmce.gamemode." + gamemode.toString().toLowerCase());
	}
	
	public static GameMode parseGameMode(String gamemode) {
		gamemode = gamemode.toLowerCase();
		
		if (("adventure").startsWith(gamemode)
				|| gamemode.equals("2")) {
			return GameMode.ADVENTURE;
		} else if (("creative").startsWith(gamemode)
				|| gamemode.equals("1")) {
			return GameMode.CREATIVE;
		} else if ((("survival").startsWith(gamemode) && !gamemode.equals("s"))
				|| gamemode.equals("0")) {
			return GameMode.SURVIVAL;
		} else if ((("spectator").startsWith(gamemode) && !gamemode.equals("s"))
				|| gamemode.equals("3")) {
			return GameMode.SPECTATOR;
		}
		
		return null;
	}
	
}
