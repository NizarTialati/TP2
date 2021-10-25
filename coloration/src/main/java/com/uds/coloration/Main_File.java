package com.uds.coloration;

import java.io.FileWriter;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;

public class Main_File {
	
	public static void main(String[] args)
	{
		System.out.println("Veuillez entrer le nom du fichier contenant le graphe : ");
		Scanner sc = new Scanner(System.in);

		String fileName = sc.nextLine();
		sc.close();
		
		Graph graph = new Graph(fileName);

		// Coloration du graphe
		//colorate(graph, graph.expectedColorNumber);
		colorateNaive(graph, graph.expectedColorNumber);

		// Affichage du graphe et enregistrement du resultat dans un fichier
		printGraph(graph, fileName);
	}
	
	/**
	 * Algrothme D-SATUR
	 * @param g Le graphe.
	 * @param nbColors Le nombre de couleurs.
	 */
	public static void colorate(Graph g, int nbColors)
	{
		if (g.nodes.size() == 1)
		{
			g.nodes.get(0).color = 1;
		} 
		else
		{
			for (Node node : g.nodes)
			{
				if (node.color == 0)
				{
					colorate(new Graph(g.nodes, node.value), nbColors);
					node.setColor(nbColors);
				}
			}
		}
	}
	
	/**
	 * Algorithme naïf
	 * @param g Le graphe.
	 * @param nbColors Le nombre de couleurs.
	 */
	public static void colorateNaive(Graph g, int nbColors) 
	{
		int i = 0, colorValue = 1;

		while (i < g.nodes.size())
		{
			Node n = g.nodes.get(i);

			while (n.color == 0 && colorValue <= nbColors)
			{
				if (isValidColor(n, colorValue))
				{
					n.color = colorValue;
					colorValue = 1;
				} 
				else 
				{
					colorValue++;
				}
			}

			if (n.color == 0)
			{
				if (i > 0)
				{
					i--;
				}

				colorValue = g.nodes.get(i).color + 1;
				g.nodes.get(i).color = 0;
			} 
			else
			{
				i++;
			}
		}
	}

	/**
	 * Vérifie qu'une couleur est valide.
	 * @param n Le sommet.
	 * @param colorValue La couleur.
	 */
	public static boolean isValidColor(Node n, int colorValue)
	{
		for (Node neighbor : n.neighbors)
		{
			if (neighbor.color == colorValue)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Affiche le graphe et l'enregistre dans le dossier prévu.
	 * @param g1 Le graphe.
	 * @param filename Le nom du fichier.
	 */
	private static void printGraph(Graph g1, String filename)
	{
		try
		{
			String rootDir = System.getProperty("user.dir");
			filename = FilenameUtils.getBaseName(filename);

			FileWriter file = new FileWriter(rootDir + "\\Generated_Graph\\" + filename + "_generated.txt", false);

			// Affichage du resultat dans la console et écriture dans un fichier
			for (Node node : g1.nodes) {
				String s = "Sommet " + node.value + "\t ======> Couleur : " + (node.color);
				System.out.println(s);
				file.write(s + "\n");
			}

			System.out.println("Fichier '" + filename + "_generated.txt' généré avec succès ! ");
			file.close();
		}
		catch(Exception e) 
		{
			System.out.println("Erreur d'enregistrement du résultat : " + e.getMessage());
			e.printStackTrace();
		}
	}
}