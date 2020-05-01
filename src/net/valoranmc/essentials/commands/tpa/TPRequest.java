package net.valoranmc.essentials.commands.tpa;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TPRequest {
	private Player from;
	private Player to;
	private long createTime;
	
	private BukkitTask task;
	
	public TPRequest(Player from, Player to) {
		this.from = from;
		this.to = to;
		this.createTime = System.currentTimeMillis();
	}
	
	public Player getFrom() {
		return from;
	}
	
	public Player getTo() {
		return to;
	}
	
	public BukkitTask getTask() {
		return task;
	}
	
	public void setTask(BukkitTask task) {
		this.task = task;
	}
	
	public boolean isExpired() {
		return System.currentTimeMillis() - createTime >= TPACommand.REQUEST_TIMEOUT;
	}
	
}
