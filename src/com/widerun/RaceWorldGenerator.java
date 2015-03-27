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
import org.bukkit.command.defaults.SetWorldSpawnCommand;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class RaceWorldGenerator {

	public static void clean() throws IOException {
		File workingDirectory = new File(".");
		File[] contents = workingDirectory.listFiles();

		if (contents != null) {
			for (File file : contents) {
				if (!file.isDirectory()
						|| !file.getName().matches("widerun-\\d+")) {
					continue;
				}
				FileUtils.deleteDirectory(file);
			}
		}
	}

	public static final int offset = 62;
	public static final int width = 10;
	
	public static final double noiseScale = 300;

	public static final int WALLS_MATERIAL = 45;

	public static World createEmptyWorld() {
		
		


		WorldCreator worldCreator = new WorldCreator("widerun-"
				+ UUID.randomUUID().toString());
		worldCreator.environment(World.Environment.NORMAL);
		worldCreator.generateStructures(false);
		worldCreator.generator(new ChunkGenerator() {
			
			private byte[] setWalls(byte[] c, double value, int relativeZ,
					int chunckX) {
				int scaledUp = (int) (value * width * 16);

				if (scaledUp / 16 == chunckX) {

					int relativeX = scaledUp % 16;

					// c[offset + 2 + relativeZ * 128+relativeX] = 45;

					int index = offset + 2 + relativeZ * 128 + 16 * 128
							* relativeX;
					c[index] = WALLS_MATERIAL;
					c[index + 1] = WALLS_MATERIAL;
					c[index + 2] = WALLS_MATERIAL;
				}
				return c;
			}

			@Override
			public byte[] generate(World world, Random random, int chunckX,
					int chunckZ) {
				
				
				// System.out.println(chunckX);
				byte[] c = new byte[32768];

				for (int i = 0; i < c.length; i = i + 128) {
					c[offset + i] = 7;
					c[offset + 1 + i] = 43;

					if (chunckX < width && chunckX >= 0) {
						c[offset + 1 + i] = 49;
					}

					// c[offset + 6 + i] = 42;

				}

				if (chunckX < width && chunckX >= 0) {

					for (int relativeZ = 0; relativeZ < 16; relativeZ++) {

						long absoluteZ = chunckZ * 16 + relativeZ;
						//System.out.println(absoluteZ);
						double value = (
								SimplexNoiseGenerator.getNoise((absoluteZ / noiseScale)/*+(world.getSeed()/noiseScale)*/)
								+ 1
								) / 2f;
						value *= 9.0 / 10;
						setWalls(c, value, relativeZ, chunckX);

						value += 1.0 / 10;
						setWalls(c, value, relativeZ, chunckX);

					}
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