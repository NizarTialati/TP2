package main;

import java.util.Scanner;

import org.apache.commons.cli.ParseException;

import coloration.Coloration;

import coloration.ColorationPPC;

public class Compare {

	public static void main(String[] args) throws ParseException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Choisissez un fichier dans le dossier \"datasets\" :");
		String fileName = sc.nextLine();
		sc.close();
		
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
