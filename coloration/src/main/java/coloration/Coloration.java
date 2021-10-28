package coloration;

import java.io.FileWriter;

import org.apache.commons.io.FilenameUtils;

import structure.Graph;
import structure.Node;

public class Coloration {

	/**
	 * Recherche une solution un algorithme naïf.
	 * 
	 * @param fileName Le nom du fichier.
	 */
	public static void findNaiveSolution(String fileName) {

		Graph graph = new Graph(fileName);

		// Coloration du graphe
		colorateNaive(graph, graph.expectedColorNumber);

		// Affichage du graphe et enregistrement du resultat dans un fichier
		// printGraph(graph, fileName);
	}

	/**
	 * Recherche une solution avec l'algorithme D-Sature.
	 * 
	 * @param fileName Le nom du fichier.
	 */
	public static void findDSatureSolution(String fileName) {

		Graph graph = new Graph(fileName);

		try {
			// Coloration du graphe
			colorate(graph, graph.expectedColorNumber);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Affichage du graphe et enregistrement du resultat dans un fichier
		// printGraph(graph, fileName);
	}

	/**
	 * Algrothme D-SATUR
	 * 
	 * @param g        Le graphe.
	 * @param nbColors Le nombre de couleurs.
	 */
	private static void colorate(Graph g, int nbColors) throws Exception {
		if (g.nodes.size() == 1) {
			g.nodes.get(0).color = 1;
		} else {
			for (Node node : g.nodes) {
				if (node.color == 0) {

					colorate(new Graph(g.nodes, node.value), nbColors);
					node.setColor(nbColors);

				}

				if (node.color == 0) {
					throw new Exception("Coloration impossible.");
				}
			}
		}
	}

	/**
	 * Algorithme naïf
	 * 
	 * @param g        Le graphe.
	 * @param nbColors Le nombre de couleurs.
	 */
	public static void colorateNaive(Graph g, int nbColors) {
		int i = 0, colorValue = 1;

		while (i < g.nodes.size() && i >= 0) {
			Node n = g.nodes.get(i);

			while (n.color == 0 && colorValue <= nbColors) {
				if (isValidColor(n, colorValue)) {
					n.color = colorValue;
					colorValue = 1;
				} else {
					colorValue++;
				}

			}
			// System.out.println("Sommet : " + n.value + " Couleur : " + n.color);

			if (n.color == 0) {
				i--;

				if (i >= 0) {
					colorValue = g.nodes.get(i).color + 1;
					g.nodes.get(i).color = 0;
				}

			} else {
				i++;
			}
		}
		
		System.out.println("Coloration impossible.");
	}

	/**
	 * Vérifie qu'une couleur est valide.
	 * 
	 * @param n          Le sommet.
	 * @param colorValue La couleur.
	 */
	private static boolean isValidColor(Node n, int colorValue) {
		for (Node neighbor : n.neighbors) {
			if (neighbor.color == colorValue) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Affiche le graphe et l'enregistre dans le dossier prévu.
	 * 
	 * @param g1       Le graphe.
	 * @param filename Le nom du fichier.
	 */
	private static void printGraph(Graph g1, String filename) {
		try {
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
		} catch (Exception e) {
			System.out.println("Erreur d'enregistrement du résultat : " + e.getMessage());
			e.printStackTrace();
		}
	}
}