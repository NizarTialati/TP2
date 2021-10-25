package coloration_ppc;

import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ColorationPPC_File {

	static int n;
	static int s;
	private static int instance;
	private static long timeout = 3600000; // one hour

	static IntVar[] regions;

	static Model model;

	public static void main(String[] args) throws ParseException, IOException {

		final Options options = configParameters();
		final CommandLineParser parser = new DefaultParser();
		final CommandLine line = parser.parse(options, args);

		boolean helpMode = line.hasOption("help"); // args.length == 0
		if (helpMode) {
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Coloration de region", options, true);
			System.exit(0);
		}

		// Check arguments and options
		for (Option opt : line.getOptions()) {
			checkOption(line, opt.getLongOpt());
		}

		System.out.println("Veuillez entrer le chemin de votre fichier contenant le graphe : ");
		Scanner sc = new Scanner(System.in);

		String fileName = sc.nextLine();

		System.out.println("Veuillez entrer le chemin du dossier dans lequel les résultats seront stockés : ");
		String dirName = sc.nextLine();

		BufferedReader file = new BufferedReader(new FileReader(fileName));

		String lineFile = file.readLine();

		// Construction du modèle
		model = new Model();
		
		while (lineFile != null) {

			// On vérifie qu'il existe un "@" dans la ligne
			if (lineFile.contains("@ ")) {

				// On retirer les espaces pour récupérer le nombre de sommet et le nombre de
				// couleurs
				String[] tab = lineFile.split(" ");

				n = Integer.parseInt(tab[1]);
				instance = Integer.parseInt(tab[2]);

				regions = new IntVar[n];

				for (int i = 0; i < n; i++) {
					regions[i] = model.intVar("Region_" + (i + 1), 0, instance, false);
				}
			}

			lineFile = file.readLine();

			if (lineFile == null)
				break;

			// On retirer les espaces pour récupérer les sommets
			String[] tab = lineFile.split(" ");

			// Ajout des contraintes
			if (tab.length == 2 && !tab[0].contains("c")) {
				model.arithm(regions[Integer.parseInt(tab[0]) - 1], "!=", regions[Integer.parseInt(tab[1]) - 1]).post();
			}

		}

		// Fermeture du fichier
		file.close();

		// Résolution du coloriage de graphe
		new ColorationPPC_File().solve(fileName, dirName);

	}

	public void solve(String filename, String dirName) throws IOException {

		// On ajoute le "/" ou "\\" à la fin du dossier chemin, si jamais l'utilisateur
		// ne l'a pas précisé
		if (dirName.charAt(dirName.length() - 1) != '/' && dirName.contains("/")) {
			dirName += "/";

		} else if (dirName.charAt(dirName.length() - 1) != '\\' && dirName.contains("\\")) {
			dirName += "\\";
		}

		filename = FilenameUtils.getBaseName(filename);

		FileWriter file = new FileWriter(dirName + filename + "_PPCgenerated.txt", false);

		model.getSolver().showStatistics();
		model.getSolver().solve();

		StringBuilder st = new StringBuilder(String.format("Coloration -- %s\n", instance, " X ", instance));
		st.append("\t");
		for (int i = 0; i < n; i++) {
			String s = "Sommet " + (i + 1) + "\t=====================> Couleur : " + (regions[i].getValue()+1);
			st.append(s);
			file.write(s + "\n");
			st.append("\n\t");
		}

		System.out.println(st.toString());
		System.out.println("Fichier '" + filename + "_PPCgenerated.txt' généré avec succès ! ");

		file.close();
	}

	// Check all parameters values
	public static void checkOption(CommandLine line, String option) {

		switch (option) {
		case "inst":
			instance = Integer.parseInt(line.getOptionValue(option));
			break;
		case "timeout":
			timeout = Long.parseLong(line.getOptionValue(option));
			break;
		default: {
			System.err.println("Bad parameter: " + option);
			System.exit(2);
		}

		}

	}

	// Add options here
	private static Options configParameters() {

		final Option helpFileOption = Option.builder("h").longOpt("help").desc("Display help message").build();

		final Option instOption = Option.builder("i").longOpt("instance").hasArg(true).argName("Coloration instance")
				.desc("sudoku instance size").required(false).build();

		final Option limitOption = Option.builder("t").longOpt("timeout").hasArg(true).argName("timeout in ms")
				.desc("Set the timeout limit to the specified time").required(false).build();

		// Create the options list
		final Options options = new Options();
		options.addOption(instOption);
		options.addOption(limitOption);
		options.addOption(helpFileOption);

		return options;
	}

	public void configureSearch() {
		model.getSolver().setSearch(minDomLBSearch(append(regions)));

	}

}