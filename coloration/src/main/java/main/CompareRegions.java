package main;

import org.apache.commons.cli.ParseException;

import coloration.Coloration;

import coloration.ColorationPPC;

public class CompareRegions {

	public static void main(String[] args) throws ParseException {
		
		String fileName = "regions.col";
		
		// Algorithme choco
		long start = System.currentTimeMillis();
		ColorationPPC.findSolutionPPC(fileName);
		long end = System.currentTimeMillis();
		System.out.println("Temps d'exécution du solver choco : " + (end - start) + "ms");
		
		// Algorithme D-Sature
		start = System.currentTimeMillis();
		Coloration.findNaiveSolution(fileName);
		end = System.currentTimeMillis();
		System.out.println("Temps d'exécution de l'algorithme D-Sature : " + (end - start) + "ms");
		
		// Algorithme naïf
		start = System.currentTimeMillis();
		Coloration.findNaiveSolution(fileName);
		end = System.currentTimeMillis();
		System.out.println("Temps d'exécution de l'algorithme naïf : " + (end - start) + "ms");
	}
}
