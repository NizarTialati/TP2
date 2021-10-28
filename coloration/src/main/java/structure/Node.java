package structure;

import java.util.ArrayList;

public class Node {
	public ArrayList<Node> neighbors;
	public int color;
	public String value;

	public Node(String name) {
		this.color = 0;
		this.value = name;
		this.neighbors = new ArrayList<Node>();
	}

	
	public void addNeighbor(Node node) {
		this.neighbors.add(node);
	}

	/**
	 * Affecte une couleur valide au sommet
	 * 
	 * @param nbColor Le nombre de couleurs.
	 */
	public void setColor(int nbColor)
	{
		boolean isValid = true;
		int i = 1;
		
		while (i <= nbColor)
		{
			for (Node neighbor : this.neighbors)
			{
				if (neighbor.color == i)
				{
					isValid = false;
				}
			}

			if (isValid)
			{
				this.color = i;
				break;
			}

			i++;
			isValid = true;
		}
	}
}