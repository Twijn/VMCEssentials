package net.valoranmc.essentials;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.valoranmc.essentials.commands.FlyCommand;
import net.valoranmc.essentials.commands.NickCommand;
import net.valoranmc.essentials.commands.tpa.TPACommand;
import net.valoranmc.essentials.commands.tpa.TPAHereCommand;
import net.valoranmc.essentials.commands.tpa.TPAcceptCommand;
import net.valoranmc.essentials.commands.tpa.TPDenyCommand;
import net.valoranmc.essentials.commands.tpo.TPOCommand;
import net.valoranmc.essentials.commands.tpo.TPOHereCommand;
import net.valoranmc.essentials.listeners.NickListener;
import net.valoranmc.essentials.listeners.TPARequestListener;
import net.valoranmc.essentials.utils.DBUtils;

public class VMCEssentials extends JavaPlugin {
	public static VMCEssentials instance;
	
	public static VMCEssentials getInstance() {
		return instance;
	}
	
	public void onEnable() {
		instance = this;
		
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("nick").setExecutor(new NickCommand());
		
		getCommand("tpo").setExecutor(new TPOCommand());
		getCommand("tpohere").setExecutor(new TPOHereCommand());
		
		getCommand("tpa").setExecutor(new TPACommand());
		getCommand("tpahere").setExecutor(new TPAHereCommand());
		getCommand("tpaccept").setExecutor(new TPAcceptCommand());
		getCommand("tpdeny").setExecutor(new TPDenyCommand());
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new NickListener(), this);
		pm.registerEvents(new TPARequestListener(), this);
		
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
