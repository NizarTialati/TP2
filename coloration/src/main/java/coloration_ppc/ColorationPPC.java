package coloration_ppc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import com.uds.coloration.ColorsEnum;

import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class ColorationPPC {

	static int n;
	static int s;
	private static int instance;
	private static long timeout = 3600000; // one hour

	IntVar[] regions;

	Model model;

	public static void main(String[] args) throws ParseException {

		final Options options = configParameters();
		final CommandLineParser parser = new DefaultParser();
		final CommandLine line = parser.parse(options, args);

		boolean helpMode = line.hasOption("help"); // args.length == 0
		if (helpMode) {
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Coloration de region", options, true);
			System.exit(0);
		}
		instance = 4;
		// Check arguments and options
		for (Option opt : line.getOptions()) {
			checkOption(line, opt.getLongOpt());
		}

		n = 13;

		new ColorationPPC().solve();
	}

	public void solve() {

		buildModel();
		model.getSolver().showStatistics();
		model.getSolver().solve();

		StringBuilder st = new StringBuilder(String.format("Coloration -- %s\n", instance, " X ", instance));
		st.append("\t");
		for (int i = 0; i < n; i++) {
			st.append("Region " + (i+1) + "\t" + ColorsEnum.values()[regions[i].getValue()]);
			st.append("\n\t");
		}

		System.out.println(st.toString());
	}

	public void buildModel() {
		model = new Model();

		regions = new IntVar[n];

		for (int i = 0; i < n; i++) {
			regions[i] = model.intVar("Region_" + (i + 1), 0, instance, false);
		}

		// Contraintes
		model.arithm(regions[0], "!=", regions[1]).post();
		model.arithm(regions[0], "!=", regions[7]).post();

		model.arithm(regions[1], "!=", regions[2]).post();
		model.arithm(regions[1], "!=", regions[3]).post();
		model.arithm(regions[1], "!=", regions[6]).post();
		model.arithm(regions[1], "!=", regions[7]).post();

		model.arithm(regions[2], "!=", regions[3]).post();
		model.arithm(regions[2], "!=", regions[4]).post();

		model.arithm(regions[3], "!=", regions[5]).post();
		model.arithm(regions[3], "!=", regions[6]).post();
		model.arithm(regions[3], "!=", regions[7]).post();

		model.arithm(regions[4], "!=", regions[5]).post();

		model.arithm(regions[5], "!=", regions[6]).post();
		model.arithm(regions[5], "!=", regions[10]).post();

		model.arithm(regions[6], "!=", regions[7]).post();
		model.arithm(regions[6], "!=", regions[8]).post();
		model.arithm(regions[6], "!=", regions[9]).post();

		model.arithm(regions[7], "!=", regions[8]).post();

		model.arithm(regions[8], "!=", regions[9]).post();
		model.arithm(regions[8], "!=", regions[10]).post();

		model.arithm(regions[9], "!=", regions[10]).post();
		model.arithm(regions[9], "!=", regions[11]).post();

		model.arithm(regions[10], "!=", regions[11]).post();

		model.arithm(regions[11], "!=", regions[12]).post();
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