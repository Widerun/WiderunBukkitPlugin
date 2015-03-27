package com.widerun.tests;

import org.bukkit.util.noise.SimplexNoiseGenerator;

public class Tests {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			
			System.out.println(SimplexNoiseGenerator.getNoise(i/100f));
		}
	}
	
}
