package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * An implementation of {@code LSystemBuilder}. It represents a model of
 * an object capable of building {@code LSystem} object from the given text
 * configuration or by setting parameters through provided methods.
 * 
 * @author Matija Frandolić
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Origin in the coordinate system.
	 */
	private Vector2D origin;
	
	/**
	 * Angle in respect to x-axis.
	 */
	private double angle;
	
	/**
	 * Initial string to which to apply productions.
	 */
	private String axiom;
	
	/**
	 * Unit length of a line.
	 */
	private double unitLength;
	
	/**
	 * Factor by which to scale length of a line in the next generation.
	 */
	private double unitLengthDegreeScaler;
	
	/**
	 * Productions that define which symbol is replaced by which string.
	 */
	private Dictionary<Character, String> productions;
	
	/**
	 * Actions that define which command is executed for which symbol.
	 */
	private Dictionary<Character, Command> actions;

	/**
	 * Constructs a new {@code LSystemBuilderImpl}.
	 */
	public LSystemBuilderImpl() {
		unitLength = 0.1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
		unitLengthDegreeScaler = 1;
		productions = new Dictionary<Character, String>();
		actions = new Dictionary<Character, Command>();
	}
	
	/**
	 * Sets the origin of the coordinate system to the given coordinates and 
	 * returns a reference to this builder.
	 * 
	 * @param x x-coordinate of the origin
	 * @param y y-coordinate of the origin
	 * @return  reference to this builder
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}
	
	/**
	 * Sets the angle in respect to x-axis to the given angle and returns a 
	 * reference to this builder.
	 * 
	 * @param angle angle in respect to x-axis
	 * @return      reference to this builder
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets the axiom to the given string and returns a reference to this builder.
	 * 
	 * @param axiom the string to be set as axiom
	 * @return      reference to this builder
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	
	/**
	 * Sets the unit length and returns a reference to this builder.
	 * 
	 * @param unitLength length to be set as unit length
	 * @return           reference to this builder
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets the unit length degree scaler and returns a reference to this builder.
	 * 
	 * @param unitLengthDegreeScaler length to be set as unit length degree scaler
	 * @return                       reference to this builder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	/**
	 * Registers a new production for the given symbol and string and returns
	 * a reference to this builder.
	 * 
	 * @param symbol     the symbol on the left side of the production
	 * @param production the string on the right side of the production
	 * @return           reference to this builder
	 * @throws IllegalArgumentException if multiple productions are defined for
	 *                                  the same symbol
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		if (productions.get(symbol) != null) {
			throw new IllegalArgumentException("Cannot define multiple productions for the same symbol.");
		}
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Registers a new action for the given symbol from the given string that
	 * represents an action and returns a reference to this builder.
	 * 
	 * @param symbol the symbol to which to register an action 
	 * @param action the string that represents an action
	 * @return       reference to this builder
	 * @throws IllegalArgumentException if multiple actions are defined for the same 
	 *                                  symbol or if the command is unknown or in
	 *                                  invalid format
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		if (actions.get(symbol) != null) {
			throw new IllegalArgumentException("Cannot define multiple actions for the same symbol.");
		}
		actions.put(symbol, parseCommand(action));
		return this;
	}
	
	/**
	 * Configures and sets this builder's parameters through text configuration
	 * in form of a string array and returns a reference to this builder. Each 
	 * string represents one instruction.
	 * 
	 * @param lines the array of strings from which to read configuration
	 * @return      reference to this builder
	 * @throws IllegalArgumentException if instruction is unknown or instruction
	 *                                  format or values are invalid
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (String line : lines) {
			if (line.isBlank()) {
				continue;
			}
			line = line.trim();
			String[] lineSplit = line.split("\\s+");
			switch (lineSplit[0]) {
			case "origin":
				parseOriginInstruction(line);
				break;
			case "angle":
				parseAngleInstruction(line);
				break;
			case "unitLength":
				parseUnitLengthInstruction(line);
				break;
			case "unitLengthDegreeScaler":
				parseUnitLengthDegreeScalerInstruction(line);
				break;
			case "command":
				parseCommandInstruction(line);
				break;
			case "axiom":
				parseAxiomInstruction(line);
				break;
			case "production":
				parseProductionInstruction(line);
				break;
			default:
				throw new IllegalArgumentException("Unknown instruction.");
			}
		}
		
		return this;
	}

	/**
	 * Returns {@code LSystem} object created from configuration of this builder. 
	 * 
	 * @return {@code LSystem} object created from configuration of this builder
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}
	
	/**
	 * Used to check if the given array that represents instruction components
	 * is of the demanded length.
	 * 
	 * @param instruction    the array that represents instruction components
	 * @param demandedLength demanded length of the given array
	 * @throws IllegalArgumentException if the array is not of the demanded length
	 */
	private void demandInstructionLength(String[] instruction, int demandedLength) {
		if (instruction.length != demandedLength) {
			throw new IllegalArgumentException("Invalid instruction format");
		}
	}
	
	/**
	 * Parses the "origin" instruction from the given string that represents
	 * a line from the configuration.
	 * 
	 * @param  line the string that represents a line from the configuration
	 * @throws IllegalArgumentException if instruction value is invalid or
	 *                                  instruction format is invalid
	 */
	private void parseOriginInstruction(String line) {
		String lineSplit[] = line.split("\\s+");
		demandInstructionLength(lineSplit, 3);
		try {
			double x = Double.parseDouble(lineSplit[1]);
			double y = Double.parseDouble(lineSplit[2]);
			origin = new Vector2D(x, y);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid instruction value.");
		}
	}
	
	/**
	 * Parses the "angle" instruction from the given string that represents
	 * a line from the configuration.
	 * 
	 * @param  line the string that represents a line from the configuration
	 * @throws IllegalArgumentException if instruction value is invalid or
	 *                                  instruction format is invalid
	 */
	private void parseAngleInstruction(String line) {
		String lineSplit[] = line.split("\\s+");
		demandInstructionLength(lineSplit, 2);
		try {
			angle = Double.parseDouble(lineSplit[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid instruction value.");
		}
	}
	
	/**
	 * Parses the "unitLength" instruction from the given string that represents
	 * a line from the configuration.
	 * 
	 * @param  line the string that represents a line from the configuration
	 * @throws IllegalArgumentException if instruction value is invalid or
	 *                                  instruction format is invalid
	 */
	private void parseUnitLengthInstruction(String line) {
		String lineSplit[] = line.split("\\s+");
		demandInstructionLength(lineSplit, 2);
		try {
			unitLength = Double.parseDouble(lineSplit[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid instruction value.");
		}
	}
	
	/**
	 * Parses the "unitLengthDegreeScaler" instruction from the given string 
	 * that represents a line from the configuration.
	 * 
	 * @param  line the string that represents a line from the configuration
	 * @throws IllegalArgumentException if instruction value is invalid or
	 *                                  instruction format is invalid
	 */
	private void parseUnitLengthDegreeScalerInstruction(String line) {
		if (!line.contains("/")) {
			String[] lineSplit = line.split("\\s+");
			demandInstructionLength(lineSplit, 2);
			try {
				unitLengthDegreeScaler = Double.parseDouble(lineSplit[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid instruction value.");
			}
		} else {
			String[] lineSplit = line.replace("/", " ").split("\\s+");
			demandInstructionLength(lineSplit, 3);
			try {
				double value1 = Double.parseDouble(lineSplit[1]);
				double value2 = Double.parseDouble(lineSplit[2]);
				unitLengthDegreeScaler = value1 / value2;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid instruction value.");
			}
		}
	}
	
	/**
	 * Parses the "command" instruction from the given string that represents
	 * a line from the configuration.
	 * 
	 * @param  line the string that represents a line from the configuration
	 * @throws IllegalArgumentException if instruction value is invalid or
	 *                                  instruction format is invalid
	 */
	private void parseCommandInstruction(String line) {
		String lineSplit[] = line.split("\\s+");
		if (lineSplit.length > 1 && lineSplit[1].length() != 1) {
			throw new IllegalArgumentException("Action must be defined for a single symbol.");
		}
		if (lineSplit.length == 3) {
			registerCommand(lineSplit[1].charAt(0), lineSplit[2]);
		} else if (lineSplit.length == 4) {
			registerCommand(lineSplit[1].charAt(0), lineSplit[2] + " " + lineSplit[3]);
		} else {
			throw new IllegalArgumentException("Invalid instruction format.");
		}
	}
	
	/**
	 * Parses the "axiom" instruction from the given string that represents
	 * a line from the configuration.
	 * 
	 * @param  line the string that represents a line from the configuration
	 * @throws IllegalArgumentException if instruction format is invalid
	 */
	private void parseAxiomInstruction(String line) {
		String lineSplit[] = line.split("\\s+");
		demandInstructionLength(lineSplit, 2);
		axiom = lineSplit[1];
	}
	
	/**
	 * Parses the "production" instruction from the given string that represents
	 * a line from the configuration.
	 * 
	 * @param  line the string that represents a line from the configuration
	 * @throws IllegalArgumentException if instruction value is invalid or
	 *                                  instruction format is invalid
	 */
	private void parseProductionInstruction(String line) {
		String lineSplit[] = line.split("\\s+");
		demandInstructionLength(lineSplit, 3);
		if (lineSplit[1].length() != 1) {
			throw new IllegalArgumentException("Production must be defined for a single symbol.");
		}
		registerProduction(lineSplit[1].charAt(0), lineSplit[2]);
	}
	
	/**
	 * Parses the given string that represents an action and returns {@code Command} 
	 * representation of that action.
	 * 
	 * @param action the string that represents an action to be parsed
	 * @return       {@code Command} representation of the action
	 * @throws IllegalArgumentException if the command is unknown or it is in
	 *                                  invalid format
	 */
	private Command parseCommand(String action) {
		action = action.trim();
		String[] actionSplit = action.split("\\s+");
		
		if (actionSplit.length == 2) {
			if (actionSplit[0].equals("color")) {
				if (actionSplit[1].length() != 6) {
					throw new IllegalArgumentException("Invalid color format.");
				}
				int r, g, b;
				try {
					r = Integer.parseInt(actionSplit[1].substring(0, 2), 16);
					g = Integer.parseInt(actionSplit[1].substring(2, 4), 16);
					b = Integer.parseInt(actionSplit[1].substring(4, 6), 16);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Invalid color format.");
				}
				return new ColorCommand(new Color(r, g, b));
			}
			
			double commandValue;
			try {
				commandValue = Double.parseDouble(actionSplit[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid action value.");
			}
			
			switch (actionSplit[0]) {
			case "draw":
				return new DrawCommand(commandValue);
			case "skip":
				return new SkipCommand(commandValue);
			case "scale":
				return new ScaleCommand(commandValue);
			case "rotate":
				return new RotateCommand(commandValue);
			default:
				throw new IllegalArgumentException("Unknown action.");
			}
		}

		if (actionSplit.length == 1) {
			switch (actionSplit[0]) {
			case "push":
				return new PushCommand();
			case "pop":
				return new PopCommand();
			default:
				throw new IllegalArgumentException("Unknown action.");
			}
		}

		throw new IllegalArgumentException("Invalid action format.");
	}
	
	/**
	 * An implementation of {@code LSystem}. It represents a model of a 
	 * "Lindermayer system".
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Returns the string that represents a sequence that is generated from
		 * the axiom after applying productions until the given level.
		 *
		 * @param level the level until which to generate a sequence
		 * @return      the string that represent the resulting generated sequence
		 */
		@Override
		public String generate(int level) {
			String previousString = axiom;
			
			for (int i = 0; i < level; i++) {
				StringBuilder sb = new StringBuilder();
				
				for (char c : previousString.toCharArray()) {
					String production = productions.get(c);
					if (production != null) {
						sb.append(production);
					} else {
						sb.append(c);
					}
				}
				
				previousString = sb.toString();
			}
			
			return previousString;
		}
		
		/**
		 * Draws the resulting fractal of the given level to the given 
		 * {@code Painter} object.
		 *
		 * @param level   the level for which to generate the fractal
		 * @param painter the {@code Painter} object to which to draw the
		 *                resulting fractal
		 */
		@Override
		public void draw(int level, Painter painter) {
			double effectiveLength = unitLength * Math.pow(unitLengthDegreeScaler, level);
			Vector2D directionVector = new Vector2D(1, 0);
			directionVector.rotate(angle * Math.PI / 180);
			
			TurtleState initialState = 
				new TurtleState(origin.copy(), directionVector, Color.BLACK, effectiveLength);
			Context ctx = new Context();
			ctx.pushState(initialState);
			
			String generatedString = generate(level);
			for (char c : generatedString.toCharArray()) {
				Command command = actions.get(c);
				if (command == null) {
					continue;
				}
				command.execute(ctx, painter);
			}
		}
		
	}

}
