package main;

import java.util.Scanner;

import org.apache.commons.cli.ParseException;

import coloration.Coloration;

public class RunNaive {

	public static void main(String[] args) throws ParseException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Choisissez un fichier dans le dossier \"datasets\" :");
		String fileName = sc.nextLine();
		sc.close();
		// Algorithme naïf
		long start = System.currentTimeMillis();
		Coloration.findNaiveSolution(fileName);
		long end = System.currentTimeMillis();
		System.out.println("Temps d'exécution de l'algorithme naïf : " + (end - start) + "ms");
	}
}
