package net.valoranmc.essentials.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.valoranmc.essentials.utils.DBUtils;

public class NickCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				String uuid = player.getUniqueId().toString();
				String nickname = ChatColor.translateAlternateColorCodes('&', args[0]);
				
				Connection con = null;
				try {
					con = DBUtils.createConnection();
					
					PreparedStatement updateUuid = con.prepareStatement("insert into nicknames(uuid, nick, set_by) values (?, ?, ?) on duplicate key update nick = ?, set_by = ?;");
					updateUuid.setString(1, uuid);
					updateUuid.setString(2, nickname);
					updateUuid.setString(3, uuid);
					updateUuid.setString(4, nickname);
					updateUuid.setString(5, uuid);
					updateUuid.execute();
					
					player.setDisplayName(nickname);
					
					player.sendMessage("§7§oYour nickname was set to §7" + nickname + "§7§o!");
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (con != null && !con.isClosed()) con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				sender.sendMessage("§c§o/nick <Player> <Nickname>");
			}
		} else if (args.length > 1) {
			if (sender.hasPermission("vmce.nick.others")) {
				Connection con = null;
				Connection bansCon = null;
				
				try {
					con = DBUtils.createConnection();
					bansCon = DBUtils.createBansConnection();

					String nickname = ChatColor.translateAlternateColorCodes('&', args[1]);
					String uuid = null;
					String setUuid = null;
					
					if (Bukkit.getPlayer(args[0]) != null) {
						Player player = Bukkit.getPlayer(args[0]);
						uuid = player.getUniqueId().toString();
						player.setDisplayName(args[0]);
						
						player.sendMessage("§7§oYour nickname was set to §7" + nickname + "§7§o!");
					}
					
					if (sender instanceof Player) {
						setUuid = ((Player) sender).getUniqueId().toString();
					}
					
					if (uuid == null) {
						PreparedStatement getUuid = bansCon.prepareStatement("SELECT uuid from names where name = ?;");
						getUuid.setString(1, args[0]);
						ResultSet uuidSet = getUuid.executeQuery();
						
						if (uuidSet.next()) {
							uuid = uuidSet.getString(1);
						}
					}
					
					PreparedStatement updateUuid = con.prepareStatement("insert into nicknames(uuid, nick, set_by) values (?, ?, ?) on duplicate key update nick = ?, set_by = ?;");
					updateUuid.setString(1, uuid);
					updateUuid.setString(2, nickname);
					updateUuid.setString(3, setUuid);
					updateUuid.setString(4, nickname);
					updateUuid.setString(5, setUuid);
					updateUuid.execute();

					
					sender.sendMessage("§7§oSet " + args[0] + "'s nickname to §7" + nickname + "§7§o!");
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (con != null && !con.isClosed()) con.close();
						if (bansCon != null && !bansCon.isClosed()) bansCon.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				sender.sendMessage("§c§oYou don't have permission!");
			}
		} else {
			sender.sendMessage("§c§o/nick [Player] <Nickname>");
		}
		
		return true;
	}
	
}
