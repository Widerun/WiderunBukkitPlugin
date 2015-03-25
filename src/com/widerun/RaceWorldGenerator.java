package com.widerun;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

public class RaceWorldGenerator {

	public static void clean() throws IOException {
		File workingDirectory = new File(".");
		File[] contents = workingDirectory.listFiles();

		if (contents != null) {
			for (File file : contents) {
				if (!file.isDirectory() || !file.getName().matches("maze-\\d+")) {
					continue;
				}
				FileUtils.deleteDirectory(file);
			}
		}
	}

	// Timer freezeTimeTimer = new Timer();

	// private static int index = 0;
	public static World createEmptyWorld() {
		WorldCreator worldCreator = new WorldCreator("maze-"
				+ UUID.randomUUID().toString());
		worldCreator.environment(World.Environment.NORMAL);
		worldCreator.generateStructures(false);
		worldCreator.generator(new ChunkGenerator() {

			@Override
			public byte[] generate(World world, Random random, int x, int z) {
				byte[] c = new byte[32768];

				for (int i = 0; i < c.length; i = i + 128) {
					c[62+ i] = 7;
					c[63 + i] = 43;
				}

				return c;
			}

			@Override
			public Location getFixedSpawnLocation(World world, Random random) {
				if (!world.isChunkLoaded(0, 0)) {
					world.loadChunk(0, 0);
				}

				return new Location(world, 0.0D, 64.0D, 0.0D);
			}
		});

		World world = worldCreator.createWorld();
		world.setDifficulty(Difficulty.NORMAL);
		world.setSpawnFlags(false, false);
		world.setPVP(true);
		world.setStorm(false);
		world.setThundering(false);
		world.setWeatherDuration(Integer.MAX_VALUE);
		world.setAutoSave(false);
		world.setKeepSpawnInMemory(false);
		world.setTicksPerAnimalSpawns(0);

		world.setTime(0);

		return world;
	}
}