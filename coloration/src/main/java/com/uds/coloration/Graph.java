package com.uds.coloration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Graph
{
	public ArrayList<Node> nodes;
	
	public int expectedColorNumber;

	public Graph(ArrayList<Node> nodes)
	{
		this.nodes = new ArrayList<Node>(nodes);
	}

	public Graph(ArrayList<Node> nodes, String nodeToRemoveValue)
	{
		this.nodes = new ArrayList<Node>(nodes);

		Node nodeToRemove = null;

		for (Node node : this.nodes) {
			if (nodeToRemoveValue.equals(node.value)) {
				nodeToRemove = node;

				for (Node neighbor : node.neighbors) {
					neighbor.neighbors.remove(node);
				}
			}
		}

		this.nodes.remove(nodeToRemove);
	}
	
	public Graph(String fileName)
	{
		try
		{
			String rootDir = System.getProperty("user.dir");
			BufferedReader file = new BufferedReader(new FileReader(rootDir + "\\datasets\\" + fileName));
	
			String line = file.readLine();
	
			int nbNodes;
			ArrayList<Node> nodes = new ArrayList<>();
	
			while (line != null)
			{
				// Gestion d'une ligne de définition du domaine
				if (line.contains("@ "))
				{
					// Récupération du nombre de sommets et du nombre de couleurs
					String[] tab = line.split(" ");
	
					nbNodes = Integer.parseInt(tab[1]);
					this.expectedColorNumber = Integer.parseInt(tab[2]);
	
					System.out.println("Nombre de sommet  : " + nbNodes + ".");
					System.out.println("Nombre de couleurs  : " + this.expectedColorNumber + ".");

					// Création de la liste des sommets
					for (int i = 0; i < nbNodes; i++)
					{
						nodes.add(new Node(Integer.toString(i + 1)));
					}
				}
				else
				{
					// Récupération des sommets
					String[] summits = line.split(" ");
					boolean isHeader = summits[0].contains("c");
					
					if (summits.length == 2 && !isHeader)
					{
						for (Node n1 : nodes)
						{
							if (n1.value.equals(summits[0]))
							{
								for (Node n2 : nodes) {
		
									if (n2.value.equals(summits[1]))
									{
										// Ajout du voisinage
										n1.addNeighbor(n2);
										n2.addNeighbor(n1);
									}
								}
							}
						}
					}
				}
	
				line = file.readLine();
			}
	
			// Fermeture du fichier
			file.close();
	
			// Ajout des sommets au graphe
			this.nodes = nodes;

		} 
		catch (Exception e)
		{
			System.out.println("Erreur de création du graphe : " + e.getMessage());
			e.printStackTrace();
		}
	}
}