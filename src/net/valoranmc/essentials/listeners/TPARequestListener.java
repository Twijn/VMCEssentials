package net.valoranmc.essentials.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.valoranmc.essentials.commands.tpa.TPACommand;
import net.valoranmc.essentials.commands.tpa.TPRequest;

public class TPARequestListener implements Listener {
	private Map<UUID, Location> locationCache = new HashMap<UUID, Location>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		
		for (TPRequest request : TPACommand.teleportPending) {
			if (request.getFrom().getUniqueId() == player.getUniqueId()) {
				if (locationCache.containsKey(player.getUniqueId())) {
					Location loc = locationCache.get(player.getUniqueId());
					Location plrLoc = player.getLocation();
					
					if (loc.getBlockX() == plrLoc.getBlockX()
							&& loc.getBlockY() == plrLoc.getBlockY()
							&& loc.getBlockZ() == plrLoc.getBlockZ()) {
						locationCache.remove(player.getUniqueId());
						request.getTask().cancel();
						player.sendMessage("§c&oTeleportation cancelled!");
						request.getTo().sendMessage("§c&oTeleportation cancelled!");
					}
				} else {
					locationCache.put(player.getUniqueId(), player.getLocation());
				}
			}
		}
	}
	
}
