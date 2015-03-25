package com.widerun;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equalsIgnoreCase("race")) { 
			
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				World world = RaceWorldGenerator.createEmptyWorld();
				
				
				player.teleport(world.getSpawnLocation().clone().add(0.5, 0.5, 0.5));
				
			}
			
		}

			
		
		return super.onCommand(sender, command, label, args);
	}
	
}
