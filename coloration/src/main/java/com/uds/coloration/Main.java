package com.uds.coloration;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void colorate(Graph g, int nbColors) {
		if (g.nodes.size() == 1) {
			g.nodes.get(0).color = ColorsEnum.values()[1];
		} else {
			for (Node node : g.nodes) {
				if (node.color == ColorsEnum.values()[0] && node.neighbors.size() < nbColors) {
					colorate(new Graph(g.nodes, node.value), nbColors);
					node.setColor(nbColors);

				}

			}

		}
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		File f = new File(sc.next());
		
		
		
		
		Node r1 = new Node("Bretagne");
		Node r2 = new Node("Normandie");
		Node r3 = new Node("Nord-Pas-de-Calais et Picardie");
		Node r5 = new Node("Alsace, Champagne-Ardenne et Lorraine");
		Node r4 = new Node("Ile-de-France");
		Node r7 = new Node("Centre-Val-de-Loire");
		Node r8 = new Node("Pays-de-la-Loire");
		Node r6 = new Node("Bourgogne et Franche-Comté");
		Node r9 = new Node("Aquitaine, Limousin et Poitou-Charentes");
		Node r10 = new Node("Auvergne et Rhônes-Alpes");
		Node r11 = new Node("Languedoc-Roussillon et Midi-Pyrénées");
		Node r12 = new Node("Provence-Alpes-Côte-D'azur");
		Node r13 = new Node("Corse");

		r1.addNeighbor(r2);
		r1.addNeighbor(r8);

		r2.addNeighbor(r1);
		r2.addNeighbor(r3);
		r2.addNeighbor(r4);
		r2.addNeighbor(r7);
		r2.addNeighbor(r8);

		r3.addNeighbor(r2);
		r3.addNeighbor(r4);
		r3.addNeighbor(r5);

		r4.addNeighbor(r2);
		r4.addNeighbor(r3);
		r4.addNeighbor(r5);
		r4.addNeighbor(r6);
		r4.addNeighbor(r7);

		r5.addNeighbor(r3);
		r5.addNeighbor(r4);
		r5.addNeighbor(r6);

		r6.addNeighbor(r4);
		r6.addNeighbor(r5);
		r6.addNeighbor(r7);
		r6.addNeighbor(r10);

		r7.addNeighbor(r2);
		r7.addNeighbor(r4);
		r7.addNeighbor(r6);
		r7.addNeighbor(r8);
		r7.addNeighbor(r9);
		r7.addNeighbor(r10);

		r8.addNeighbor(r1);
		r8.addNeighbor(r2);
		r8.addNeighbor(r7);
		r8.addNeighbor(r9);

		r9.addNeighbor(r7);
		r9.addNeighbor(r8);
		r9.addNeighbor(r10);
		r9.addNeighbor(r11);

		r10.addNeighbor(r6);
		r10.addNeighbor(r7);
		r10.addNeighbor(r9);
		r10.addNeighbor(r11);
		r10.addNeighbor(r12);

		r11.addNeighbor(r9);
		r11.addNeighbor(r10);
		r11.addNeighbor(r12);

		r12.addNeighbor(r10);
		r12.addNeighbor(r11);
		r12.addNeighbor(r13);

		r13.addNeighbor(r12);

		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(r1);
		nodes.add(r2);
		nodes.add(r3);
		nodes.add(r4);
		nodes.add(r5);
		nodes.add(r6);
		nodes.add(r7);
		nodes.add(r8);
		nodes.add(r9);
		nodes.add(r10);
		nodes.add(r11);
		nodes.add(r12);
		nodes.add(r13);

		Graph g1 = new Graph(nodes);

		colorate(g1, 4);

		for (Node node : g1.nodes) {
			System.out.println("Région " + (g1.nodes.indexOf(node) + 1) + " : " + node.value + " \t Couleur : "
					+ node.color.toString());
		}
	}

}