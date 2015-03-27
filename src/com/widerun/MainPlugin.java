package com.widerun;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		super.onEnable();
		getServer().getPluginManager().registerEvents(new EventsHandler(), this);
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equalsIgnoreCase("race")) { 
			
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				World world = RaceWorldGenerator.createEmptyWorld();
				
				
				player.teleport(new Location(world, 0, 65, 0));
				
			}
			
		}

			
		
		return super.onCommand(sender, command, label, args);
	}
	
}
