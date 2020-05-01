package net.valoranmc.essentials.commands.tpa;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPAHereCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				
				TPRequest genRequest = new TPRequest(target, player);
				List<TPRequest> requestList = TPACommand.teleportList.get(target);
				
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
				
				TPACommand.teleportList.put(target, requestList);
				
				player.sendMessage("§6Sent request to §e" + target.getDisplayName());
				
				target.sendMessage("§e" + player.getDisplayName() + " §6has requested that you teleport to them!");
				target.sendMessage("§6Type §e/tpaccept§6 to accept, or §e/tpdeny §6to deny");
				target.sendMessage("§6You have §e" + TPACommand.REQUEST_TIMEOUT / 1000 + " seconds§6 to accept");
			} else {
				sender.sendMessage("§c§o/tpahere <Player>");
			}
		} else {
			sender.sendMessage("§c§oOnly players can use this command!");
		}
		
		return true;
	}
	
}
