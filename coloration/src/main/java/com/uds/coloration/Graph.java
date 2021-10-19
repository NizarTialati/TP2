package com.uds.coloration;

import java.util.ArrayList;

public class Graph {
	public ArrayList<Node> nodes;

	public Graph(ArrayList<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}

	public Graph(ArrayList<Node> nodes, String nodeToRemoveValue) {
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
}