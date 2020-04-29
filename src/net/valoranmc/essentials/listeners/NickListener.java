package net.valoranmc.essentials.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.valoranmc.essentials.utils.DBUtils;

public class NickListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		Connection con = null;
		try {
			con = DBUtils.createConnection();
			
			PreparedStatement getNick = con.prepareStatement("SELECT nick from nicknames where uuid = ?;");
			getNick.setString(1, player.getUniqueId().toString());
			ResultSet nickSet = getNick.executeQuery();
			
			if (nickSet.next()) {
				player.setDisplayName(nickSet.getString(1));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (con != null && !con.isClosed()) con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
