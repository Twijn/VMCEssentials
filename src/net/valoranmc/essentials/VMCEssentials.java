package net.valoranmc.essentials;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.valoranmc.essentials.commands.FlyCommand;
import net.valoranmc.essentials.commands.NickCommand;
import net.valoranmc.essentials.listeners.NickListener;
import net.valoranmc.essentials.utils.DBUtils;

public class VMCEssentials extends JavaPlugin {

	public void onEnable() {
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("nick").setExecutor(new NickCommand());
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new NickListener(), this);
		
		Connection con = null;
		try {
			con = DBUtils.createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null && !con.isClosed()) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
