package net.valoranmc.essentials.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.valoranmc.essentials.commands.FlyCommand;

public class FlyPersistenceListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		if (FlyCommand.isFlying(player.getUniqueId())
				&& player.hasPermission("vmce.fly")) {
			player.setAllowFlight(true);
		}
		
		if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR
				&& player.hasPermission("vmce.fly")) {
			player.setAllowFlight(true);
			player.setFlying(true);
		}
	}
	
}
