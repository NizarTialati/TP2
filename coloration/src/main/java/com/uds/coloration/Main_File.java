package com.uds.coloration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;

public class Main_File {

	public static void colorate(Graph g, int nbColors) {
		if (g.nodes.size() == 1) {
			g.nodes.get(0).color = 1;
		} else {
			for (Node node : g.nodes) {
				if (node.color == 0 && node.neighbors.size() < nbColors) {
					colorate(new Graph(g.nodes, node.value), nbColors);
					node.setColor(nbColors);
				}
				
			}

		}
	}

	public static void main(String[] args) {

		try {

			System.out.println("Veuillez entrez le chemin de votre fichier contenant le graphe : ");
			Scanner sc = new Scanner(System.in);

			String fileName = sc.nextLine();

			System.out.println("Veuillez entrez le chemin du dossier dans lequel les résultats seront stockés : ");
			String dirName = sc.nextLine();

			BufferedReader file = new BufferedReader(new FileReader(fileName));

			String line = file.readLine();
			
			// /home/tialati/Master_2/IA_GL/coloration/datasets/g1.col

			// /home/tialati/Master_2/IA_GL/coloration/Generated_Graph

			int nbColors = 0, nbNodes;
			ArrayList<Node> nodes = new ArrayList<>();

			while (line != null) {

				// On vérifie qu'il existe un "@" dans la ligne
				if (line.contains("@ ")) {

					// On retirer les espaces pour récupérer le nombre de sommet et le nombre de
					// couleurs
					String[] tab = line.split(" ");

					nbNodes = Integer.parseInt(tab[1]);
					nbColors = Integer.parseInt(tab[2]);

					System.out.println(nbColors);
					// On crée la liste de sommets
					for (int i = 0; i < nbNodes; i++) {
						nodes.add(new Node(Integer.toString(i + 1)));
					}

				}

				line = file.readLine();

				if (line == null)
					break;

				// On retirer les espaces pour récupérer les sommets
				String[] tab = line.split(" ");
				
				if (tab.length == 2 && !tab[0].contains("c")) {
					
					for (Node n1 : nodes) {

						if (n1.value.equals(tab[0])) {
							
							for (Node n2 : nodes) {

								if (n2.value.equals(tab[1])) {
									// On ajoute le voisin du sommet en question
									n1.addNeighbor(n2);
									n2.addNeighbor(n1);
								}
							}
						}
					}
				}

			}

			// Fermeture du fichier
			file.close();

			// On crée le graphe avec la liste des sommets
			Graph graph = new Graph(nodes);

			// On colorie le graphe
			colorate(graph, nbColors);

			// On affiche le graphe et on enregistre le resultat dans un fichier
			printGraph(graph, fileName, dirName);

		} catch (Exception e) {
			System.out.println("Error : ");
			e.printStackTrace();
		}

	}

	private static void printGraph(Graph g1, String filename, String dirName) throws IOException {

		// On ajoute le "/" ou "\\" à la fin du dossier chemin si jamais l'utilisateur
		// ne l'a pas précisé
		if (dirName.charAt(dirName.length() - 1) != '/' && dirName.contains("/")) {
			dirName += "/";

		} else if (dirName.charAt(dirName.length() - 1) != '\\' && dirName.contains("\\")) {
			dirName += "\\";
		}

		filename = FilenameUtils.getBaseName(filename);

		FileWriter file = new FileWriter(dirName + filename + "_generated.txt", false);

		// On affiche le resultat dans la console et puis on l'écris dans un fichier
		for (Node node : g1.nodes) {
			String s = "Sommet " + node.value + "\t ======> Couleur : " + (node.color + 1);
			System.out.println(s);
			file.write(s + "\n");
		}

		System.out.println("Fichier '" + filename + "_generated.txt' généré avec succès ! ");
		file.close();
	}

}