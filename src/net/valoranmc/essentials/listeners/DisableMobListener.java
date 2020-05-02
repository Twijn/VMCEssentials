package net.valoranmc.essentials.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class DisableMobListener implements Listener {
	private List<EntityType> disabledMobs = Arrays.asList(EntityType.WITHER, EntityType.PHANTOM);
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		if (disabledMobs.contains(e.getEntityType())) {
			e.setCancelled(true);
			
			if (e.getEntityType().equals(EntityType.WITHER)) {
				Location loc = e.getLocation();
				
				int witherSkeletonCount = 0;
				int soulSandCount = 0;
				
				for (int x = loc.getBlockX() - 2; x <= loc.getBlockX() + 2; x++) {
					for (int y = loc.getBlockY(); y <= loc.getBlockY() + 2; y++) {
						for (int z = loc.getBlockZ() - 2; z <= loc.getBlockZ() + 2; z++) {
							Block block = loc.getWorld().getBlockAt(x, y, z);
							
							if (block.getType() == Material.WITHER_SKELETON_SKULL) {
								witherSkeletonCount++;
								block.setType(Material.AIR);
							} else if (block.getType() == Material.SOUL_SAND) {
								soulSandCount++;
								block.setType(Material.AIR);
							}
						}
					}
				}
				
				if (witherSkeletonCount > 0)
					loc.getWorld().dropItem(loc, new ItemStack(Material.WITHER_SKELETON_SKULL, witherSkeletonCount));
				if (soulSandCount > 0)
					loc.getWorld().dropItem(loc, new ItemStack(Material.SOUL_SAND, soulSandCount));
				
				loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 1, 1);
			}
		}
	}
	
}
