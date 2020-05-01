package net.valoranmc.essentials.commands.tpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPACommand implements CommandExecutor {
	public static final int REQUEST_TIMEOUT = 60000;
	public static final int TELEPORT_WAIT = 3000;
	public static List<TPRequest> teleportPending = new ArrayList<TPRequest>();
	public static Map<Player, List<TPRequest>> teleportList = new HashMap<Player, List<TPRequest>>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				
				TPRequest genRequest = new TPRequest(player, target);
				List<TPRequest> requestList = teleportList.get(target);
				
				if (requestList == null) {
					requestList = new ArrayList<TPRequest>();
				} else {
					List<TPRequest> deletionQueue = new ArrayList<TPRequest>();
					for (TPRequest request : requestList) {
						if (request.isExpired()) {
							deletionQueue.add(request);
							continue;
						}
						if (request.getFrom().getUniqueId().equals(genRequest.getFrom().getUniqueId())
								&& request.getTo().getUniqueId().equals(genRequest.getTo().getUniqueId())) {
							player.sendMessage("§c§oPreexisting request already exists!");
							return true;
						}
					}
					for (TPRequest delete : deletionQueue) {
						requestList.remove(delete);
					}
				}
				
				requestList.add(genRequest);
				
				teleportList.put(target, requestList);
				
				player.sendMessage("§6Sent request to §e" + target.getDisplayName());
				
				target.sendMessage("§e" + player.getDisplayName() + " §6has requested to teleport to you!");
				target.sendMessage("§6Type §e/tpaccept§6 to accept, or §e/tpdeny §6to deny");
				target.sendMessage("§6You have §e" + REQUEST_TIMEOUT / 1000 + " seconds §6to accept");
			} else {
				sender.sendMessage("§c§o/tpa <Player>");
			}
		} else {
			sender.sendMessage("§c§oOnly players can use this command!");
		}
		
		return true;
	}
	
}
