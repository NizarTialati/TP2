package coloration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.io.FilenameUtils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ColorationPPC {

	static int n;
	static int s;
	private static int instance;
	private static long timeout = 3600000; // one hour

	static IntVar[] regions;

	static Model model;

	public static void findSolutionPPC(String fileName) {

		try {
			
			String rootDir = System.getProperty("user.dir");
			
			String path = "";
			
			if(rootDir.contains("/") ) {
				path += rootDir + "/datasets/" + fileName;
			} else {
				path += rootDir + "\\datasets\\" + fileName;
			}
			BufferedReader file = new BufferedReader(new FileReader(path));

			String lineFile = file.readLine();

			// Construction du modèle
			model = new Model();
			int countConstraints = 0;
			
			while (lineFile != null) {

				// On vérifie qu'il existe un "@" dans la ligne
				if (lineFile.contains("@ ")) {

					// On retirer les espaces pour récupérer le nombre de sommet et le nombre de
					// couleurs
					String[] tab = lineFile.split(" ");

					n = Integer.parseInt(tab[1]);
					instance = Integer.parseInt(tab[2]);

					regions = new IntVar[n];
					
					System.out.println("Nombre de sommet  : " + n + ".");
					System.out.println("Nombre de couleurs  : " + instance + ".");
					
					for (int i = 0; i < n; i++) {
						regions[i] = model.intVar("Region_" + (i + 1), 1, instance, false);
					}
				}

				lineFile = file.readLine();

				if (lineFile == null)
					break;

				// On retirer les espaces pour récupérer les sommets
				String[] tab = lineFile.split(" ");

				// Ajout des contraintes
				if (tab.length == 2 && !tab[0].contains("c")) {
					model.arithm(regions[Integer.parseInt(tab[0]) - 1], "!=", regions[Integer.parseInt(tab[1]) - 1])
							.post();
					countConstraints++;
				}
			}

			// Fermeture du fichier
			file.close();
			
			System.out.println("Nombre de contraintes : " + countConstraints);
			
			// Résolution du coloriage de graphe
			new ColorationPPC().solve(fileName);

		} catch (Exception e) {
			System.out.println("Erreur d'ouverture du fichier : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void solve(String filename) {

		try {
//
//			String rootDir = System.getProperty("user.dir");
//			filename = FilenameUtils.getBaseName(filename);
//
//			FileWriter file = new FileWriter(rootDir + "\\Generated_Graph\\" + filename + "_PPCgenerated.txt", false);

			model.getSolver().solve();

//			StringBuilder st = new StringBuilder(String.format("Coloration -- %s\n", instance, " X ", instance));
//			st.append("\t");
//			for (int i = 0; i < n; i++) {
//				String s = "Sommet " + (i + 1) + "\t=====================> Couleur : " + (regions[i].getValue());
//				st.append(s);
//				file.write(s + "\n");
//				st.append("\n\t");
//			}
//
//			System.out.println(st.toString());
//			//System.out.println("Fichier '" + filename + "_PPCgenerated.txt' généré avec succès ! ");
//
//			file.close();
		} catch (Exception e) {
			System.out.println("Erreur d'enregistrement du résultat : " + e.getMessage());
			e.printStackTrace();
		}
	}
}