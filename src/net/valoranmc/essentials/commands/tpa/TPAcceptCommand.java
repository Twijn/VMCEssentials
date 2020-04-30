package net.valoranmc.essentials.commands.tpa;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.valoranmc.essentials.VMCEssentials;

public class TPAcceptCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			List<TPRequest> tpRequests = TPACommand.teleportList.get(player);
			List<TPRequest> deletionQueue = new ArrayList<TPRequest>();
			for (TPRequest request : tpRequests) {
				if (request.isExpired()) deletionQueue.add(request);
			}
			for (TPRequest delRequest : deletionQueue) {
				tpRequests.remove(delRequest);
			}
			
			TPRequest fulfill = null;
			
			if (tpRequests.size() == 0) {
				if (deletionQueue.size() > 0) {
					player.sendMessage("§c§oAll teleport requests have expired!");
				} else
					player.sendMessage("§c§oNo teleport requests exist!");
			} else if (tpRequests.size() == 1) {
				fulfill = tpRequests.get(0);
			} else {
				if (args.length == 1) {
					for (TPRequest request : tpRequests) {
						if (request.getFrom().getUniqueId() == player.getUniqueId()) {
							if (request.getTo().getName().equalsIgnoreCase(args[0])
									|| request.getFrom().getName().equalsIgnoreCase(args[0])) {
								fulfill = request;
							}
						}
					}
				}
				
				if (fulfill == null) {
					StringBuilder builder = new StringBuilder();
					
					boolean first = true;
					for (TPRequest request : tpRequests) { 
						if (first) {
							first = false;
						} else {
							builder.append("/");
						}
						
						if (request.getFrom().getUniqueId() == player.getUniqueId()) {
							builder.append(request.getTo().getName());
						} else {
							builder.append(request.getFrom().getName());
						}
					}
					
					player.sendMessage("§c§oNarrow down your search! §c/tpaccept <" + builder.toString() + ">");
				}
			}
			
			if (fulfill != null) {
				fulfill.getFrom().sendMessage("§6Request to §e" + player.getDisplayName() + " §6has been accepted! You will teleport in §e" + TPACommand.TELEPORT_WAIT / 1000 + " seconds");
				fulfill.getTo().sendMessage("§6Request from §e" + player.getDisplayName() + " §6to §eyou §6has been accepted! They will teleport in §e" + TPACommand.TELEPORT_WAIT / 1000 + " seconds");
				fulfill.getFrom().sendMessage("§cYou will teleport in " + TPACommand.TELEPORT_WAIT / 1000 + " seconds. Do not move");
				
				final TPRequest fulfillFinal = fulfill;
				
				TPACommand.teleportPending.add(fulfillFinal);
				
				TPACommand.teleportList.get(player).remove(fulfill);
				
				fulfill.setTask(Bukkit.getScheduler().runTaskLater(VMCEssentials.getInstance(), new Runnable() {

					@Override
					public void run() {
						fulfillFinal.getFrom().teleport(fulfillFinal.getTo());
						fulfillFinal.getFrom().sendMessage("§6§oTeleporting...");
						fulfillFinal.getTo().sendMessage("§6§oTeleporting §e§o" + fulfillFinal.getFrom().getName() + "§6§o...");
						
						TPACommand.teleportPending.remove(fulfillFinal);
					}
					
				}, TPACommand.TELEPORT_WAIT / 1000 * 20));
			}
		} else {
			sender.sendMessage("§c§oOnly players can use this command!");
		}
		
		return true;
	}
	
}
