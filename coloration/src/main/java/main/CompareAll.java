package main;

import org.apache.commons.cli.ParseException;

import coloration.Coloration;

import coloration.ColorationPPC;

public class CompareAll {

	public static void main(String[] args) throws ParseException {
		
		for (int i = 1; i < 15; i++) {
			
			String fileName = "g" + i + ".col";
			
			System.out.println("Graphe : " + fileName);
			compare(fileName);
		}
	}
	
	private static void compare(String fileName) {
		
		// Algorithme choco
		long start = System.currentTimeMillis();
		ColorationPPC.findSolutionPPC(fileName);
		long end = System.currentTimeMillis();
		System.out.println("Temps d'exécution du solver choco : " + (end - start) + "ms");
		
		// Algorithme D-Sature
		start = System.currentTimeMillis();
		Coloration.findDSatureSolution(fileName);
		end = System.currentTimeMillis();
		System.out.println("Temps d'exécution de l'algorithme D-Sature : " + (end - start) + "ms");
		
		// Algorithme naïf
		start = System.currentTimeMillis();
		Coloration.findNaiveSolution(fileName);
		end = System.currentTimeMillis();
		System.out.println("Temps d'exécution de l'algorithme naïf : " + (end - start) + "ms");
	}
}
