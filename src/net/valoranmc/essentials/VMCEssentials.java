package net.valoranmc.essentials;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.valoranmc.essentials.commands.FeedCommand;
import net.valoranmc.essentials.commands.FlyCommand;
import net.valoranmc.essentials.commands.HealCommand;
import net.valoranmc.essentials.commands.NickCommand;
import net.valoranmc.essentials.commands.gamemode.GMACommand;
import net.valoranmc.essentials.commands.gamemode.GMCCommand;
import net.valoranmc.essentials.commands.gamemode.GMSCommand;
import net.valoranmc.essentials.commands.gamemode.GMSPCommand;
import net.valoranmc.essentials.commands.gamemode.GamemodeCommand;
import net.valoranmc.essentials.commands.tpa.TPACommand;
import net.valoranmc.essentials.commands.tpa.TPAHereCommand;
import net.valoranmc.essentials.commands.tpa.TPAcceptCommand;
import net.valoranmc.essentials.commands.tpa.TPDenyCommand;
import net.valoranmc.essentials.commands.tpo.TPOCommand;
import net.valoranmc.essentials.commands.tpo.TPOHereCommand;
import net.valoranmc.essentials.listeners.DisableMobListener;
import net.valoranmc.essentials.listeners.FlyPersistenceListener;
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
		
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("feed").setExecutor(new FeedCommand());
		
		getCommand("tpo").setExecutor(new TPOCommand());
		getCommand("tpohere").setExecutor(new TPOHereCommand());
		
		getCommand("tpa").setExecutor(new TPACommand());
		getCommand("tpahere").setExecutor(new TPAHereCommand());
		getCommand("tpaccept").setExecutor(new TPAcceptCommand());
		getCommand("tpdeny").setExecutor(new TPDenyCommand());

		getCommand("gma").setExecutor(new GMACommand());
		getCommand("gmc").setExecutor(new GMCCommand());
		getCommand("gms").setExecutor(new GMSCommand());
		getCommand("gmsp").setExecutor(new GMSPCommand());
		
		GamemodeCommand gmCommand = new GamemodeCommand();
		getCommand("gamemode").setExecutor(gmCommand);
		getCommand("gamemode").setTabCompleter(gmCommand);
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new NickListener(), this);
		pm.registerEvents(new TPARequestListener(), this);
		pm.registerEvents(new DisableMobListener(), this);
		pm.registerEvents(new FlyPersistenceListener(), this);
		
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
