package tr.edu.gsu.mataws;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import tr.edu.gsu.sine.Batch;
import tr.edu.gsu.sine.Option;
import tr.edu.gsu.sine.Path;
import tr.edu.gsu.sine.SecondaryTask;
import tr.edu.gsu.sine.Sine;
import tr.edu.gsu.sine.ext.Profile;
import tr.edu.gsu.sine.ext.Trait;

/**
 * Provides support for processing command-line arguments.
 * TODO TODO to be adapted to mataws
 */
public class CommandLine {
	
	/**
	 * Parses arguments from the command-line. It also shuts the program down if
	 * arguments are wrong.
	 * 
	 * @param args
	 *            the arguments from the command-line
	 */
	public static void parseArguments(String[] args) {
		List<String> largs = Arrays.asList(args);
		Iterator<String> it = largs.iterator();
		String argument;
		while (it.hasNext()) {
			argument = it.next();
			try {
				parseArgument(argument, it);
			} catch(NoSuchElementException e) {
				exitWithError("Error: Missing argument to: " + argument);
			}
		}
	}

	/**
	 * Parses an argument from the command-line. It also retrieves the following
	 * argument if necessary. It shuts the program down if argument is unknown.
	 * 
	 * @param arg
	 *            the argument to be parsed
	 * @param argsIter
	 *            an iterator over the following arguments
	 * @throws NoSuchElementException
	 *             there is no other argument to retrieve
	 */
	private static void parseArgument(String arg, Iterator<String> argsIter)
			throws NoSuchElementException {
		String param;
		
		// Search for a path parameter.
		for (Path p : Path.values()) {
			if (arg.equals("-" + p.name().substring(0, 1).toLowerCase())
					|| arg.equals("--" + p.name().toLowerCase())) {
				param = argsIter.next();
				p.setValue(new File(param).getAbsolutePath());
				return;
			}
		}

		// Search for an option parameter.
		for (Option o : Option.values()) {
			if (arg.equals("-" + o.getShort())
					|| arg.equals("--" + o.getLong())) {
				param = argsIter.next();
				try {
					o.setValue(param);
				} catch (Exception e) {
					exitWithError("Option " + arg + ": " + e.getMessage());
				}
				return;
			}
		}

		// Search for a command parameter.
		for (Command c : Command.values()) {
			if (arg.equals("-" + c.getShort())
					|| arg.equals("--" + c.getLong())) {
				param = c.getParamType().equals("") ? "" : argsIter.next();
				c.execute(param);
				return;
			}
		}

		exitWithError("Error: Unknown argument: " + arg);
	}
	
	/**
	 * Prints message and usage to system error stream, then shuts the program
	 * down with an error status code.
	 * 
	 * @param msg
	 *            error message to be printed
	 */
	public static void exitWithError(String msg) {
		System.err.println(msg);
		printUsage(System.err);
		System.exit(1);
	}

	/**
	 * Prints the usage message to a print stream.
	 * 
	 * @param ps
	 *            the print stream to be used
	 */
	private static void printUsage(PrintStream ps) {
		ps.println("Usage: java -jar sine-" + Sine.VERSION + ".jar"
				+ " [OPTIONS] [COMMANDS]");

		ps.println();
		ps.println("If one or more COMMANDS are specified, " +
				"then SINE runs in non-interactive mode, " +
				"else it displays a graphical window.");

		ps.println();
		ps.println("Each OPTION can be set for once, "
				+ "whereas COMMANDS are cumulative.");

		// print options with description and default value
		ps.println();
		ps.println("OPTIONS:");
		for (Option o: Option.values()) {
			ps.println("  -" + o.getShort() + ", --" + o.getLong() + " "
					+ o.getType() + " \t" +	"(default: " + o.getValue()
					+ ") \t" + o.getDescription());
		}
		
		// print paths with description and default value
		for (Path p: Path.values()) {
			ps.println("  -" + p.name().substring(0, 1).toLowerCase()
					+ ", --" + p.name().toLowerCase()
					+ " DIR \t(default: " + p.getValue()
					+ ") \t" + p.getDescription());
		}
		
		// prints commands with description
		ps.println();
		ps.println("COMMANDS:");
		for (Command c: Command.values()) {
			ps.println("  -" + c.getShort() + ", --" + c.getLong() + " "
					+ c.getParamType() + " \t" + c.getDescription());
		}

		// print traits abbreviations with category name and trait name.
		ps.println();
		ps.println("TRAITS:");
		for (Trait t: Trait.values()) {
			ps.println("  " + t.getAbbreviation() + "  \t" +
					t.getCategory().getName() + ":  " + t.getName());
		}

		// print profiles abbreviations in two columns
		ps.println();
		ps.println("PROFILES:");
		Profile[] profiles = Profile.values();
		for (int i = 0; i < profiles.length; i++) {
			if (i % 2 == 0) {
				ps.print("  " + profiles[i].toAbbrev() + "\t\t");
			} else {
				ps.println(profiles[i].toAbbrev());
			}
		}
		if (profiles.length % 2 != 0) {
			ps.println();
		}
		
		// print secondary tasks abbreviations with description and defaults.
		ps.println();
		ps.println("SECONDARY TASKS:");
		for (SecondaryTask st : SecondaryTask.values()) {
			ps.println("  " + st.name().toLowerCase() + "  \t" + "(default: "
					+ (st.isEnabled() ? "on " : "off") + ")  \t"
					+ st.getDescription());
		}
		
		// print an example.
		ps.println();
		ps.println("EXAMPLE:");
		ps.println("  Parse a WSDL collection from default "
				+ "'collection' directory, name it 'My Example',");
		ps.println("  do default secondary tasks (list and count parameters), "
				+ "serialize the collection to an XML file, ");
		ps.println("  extract networks using equal or flexible matching to "
				+ "default 'extraction' directory:");
		ps.println();
		ps.println("  " + "java -jar sine-" + Sine.VERSION + ".jar "
				+ "-l WSDL -n 'My Example' -s serialize_xml -t equa -t flex");
		ps.println();
	}
	
	/**
	 * Provides commands names, descriptions and execution methods.
	 * <p>
	 * Class members are not documented, since it is only used in CmdLine class.
	 */
	private static enum Command {

		ALL("\t", "\textract all networks") {
			public void execute(String param) {
				// Select all supported networks for extraction.
				for (Profile p: Profile.values()) {
					Batch.TODO.put(p);
				}
			}
		},
		
		TRAIT("TRAIT", "\textract networks with PROFILES having TRAIT") {
			public void execute(String param) {
				Trait t = Trait.valueOfAbbrev(param);
				if (t == null) {
					System.err.println("Error: Not a known trait: " + param);
					printUsage(System.err);
					System.exit(1);
				}
				
				// Select networks which feature the specified trait.
				List<Profile> lp = Profile.valuesWith(t);
				for (Profile p: lp) {
					Batch.TODO.put(p);
				}
			}
		},
		
		PROFILE("PROFILE", "extract the network with PROFILE") {
			public void execute(String param) {
				Profile p = Profile.valueOfAbbrev(param);
				if (p == null) {
					System.err.println("Error: Not a known profile: " + param);
					printUsage(System.err);
					System.exit(1);
				}
				
				// Select the network of specified profile.
				Batch.TODO.put(p);
			}
		},

		SWITCH("SECONDARY TASK", "switch on/off the SECONDARY TASK") {
			@Override
			public void execute(String param) {
				// Toggle the corresponding secondary task, if known.
				try {
					SecondaryTask task;
					task = SecondaryTask.valueOf(param.toUpperCase());
					task.setEnabled(!task.isEnabled());
				} catch (Exception e) {
					System.err.println("Error: Not a known secondary task: "
							+ param);
					printUsage(System.err);
					System.exit(1);
				}
			}
		},
		
		HELP("", "\t\tprint this help") {
			public void execute(String param) {
				// Print the usage message and shuts the program down.
				printUsage(System.out);
				System.exit(0);
			}
		},
		
		VERSION("", "\tprint version") {
			public void execute(String param) {
				// Print the version of SINE and shuts the program down.
				System.out.println("SINE " + Sine.VERSION);
				System.exit(0);
			}
		};
		
		private final String paramType;
		private final String description;

		private Command(String paramType, String description) {
			this.paramType = paramType;
			this.description = description;
		}

		public char getShort() {
			return Character.toLowerCase(name().charAt(0));
		}

		public String getLong() {
			return name().toLowerCase();
		}

		public String getParamType() {
			return paramType;
		}

		public String getDescription() {
			return description;
		}

		public abstract void execute(String param);
	}
}