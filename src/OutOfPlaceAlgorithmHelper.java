import java.util.ArrayList;

import processing.core.PApplet;

class OutOfPlaceAlgorithmHelper extends AbstractAlgorithmHelper {

	enum ArrayType {
		MAIN, OUTPUT
	}

	private int[] output;
	private Visualizer valuesView;
	private Visualizer outputView;
	private int oViewX, oViewY, oViewWidth, oViewHeight;

	public OutOfPlaceAlgorithmHelper(PApplet theParent, int[] theArray, int theX, int theY, int theWidth,
			int theHeight) {
		super(theParent);
		this.initialize(theArray, theX, theY, theWidth, theHeight / 2);
	}

	protected void initialize(int[] theArray, int theX, int theY, int theWidth, int theHeight) {
		viewX = theX;
		viewY = theY;
		viewWidth = theWidth;
		viewHeight = theHeight + Visualizer.BORDER_THICKNESS;
		oViewX = theX;
		oViewY = theY + theHeight;
		oViewWidth = theWidth;
		oViewHeight = theHeight;

		values = new int[theArray.length];
		PApplet.arrayCopy(theArray, values);
		output = new int[values.length];

		valuesView = new Visualizer(processing, viewX, viewY, viewWidth, viewHeight, values.length, 1);
		outputView = new Visualizer(processing, oViewX, oViewY, oViewWidth, oViewHeight, values.length, 1);

		comparisons = 0;
		moves = 0;
		commands = new ArrayList<AlgorithmCommand>();
		ready = true;
		state = SortingBenchmark.State.WAIT;
	}

	protected void drawValues() {
		valuesView.drawArray(values);
		outputView.drawArray(output);
	}

	protected void processCommands() {
		if (commands.size() > 0) {
			ready = false;
			AlgorithmCommand command = commands.get(0);
			switch (command.type()) {
			case COMPARE:
				switch (command.direction()) {
				case IN_MAIN:
					valuesView.drawRect(command.first(), values, valuesView.COMPARE_COLOR);
					valuesView.drawRect(command.second(), values, valuesView.COMPARE_COLOR);
					break;
				case IN_OUTPUT:
					outputView.drawRect(command.first(), output, outputView.COMPARE_COLOR);
					outputView.drawRect(command.second(), output, outputView.COMPARE_COLOR);
					break;
				case MAIN_TO_OUTPUT:
					break;
				case OUTPUT_TO_MAIN:
					break;
				}
				break;
			case SWAP:
				valuesView.drawRect(command.first(), values, valuesView.MOVE_COLOR);
				valuesView.drawRect(command.second(), values, valuesView.MOVE_COLOR);
				int temp = values[command.first()];
				values[command.first()] = values[command.second()];
				values[command.second()] = temp;
				break;
			case MOVE:
				if (command.direction() == AlgorithmCommand.Direction.MAIN_TO_OUTPUT) {
					output[command.second()] = values[command.first()];
					values[command.first()] = 0;
				} else if (command.direction() == AlgorithmCommand.Direction.OUTPUT_TO_MAIN) {
					values[command.second()] = output[command.first()];
					output[command.first()] = 0;
				}
				break;
			}
			commands.remove(0);
		}
		if (commands.size() == 0)
			ready = true;
	}

	public synchronized int compare(int firstIndex, ArrayType fromArray, int secondIndex, ArrayType toArray) {
		AlgorithmCommand.Direction direction = calculateDirection(fromArray, toArray);

		state = SortingBenchmark.State.WAIT;
		comparisons++;
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.COMPARE, firstIndex, secondIndex, direction));
		try {
			while (state != SortingBenchmark.State.GO) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (direction) {
		case IN_MAIN:
			return (values[firstIndex] - values[secondIndex]);
		case IN_OUTPUT:
			return (output[firstIndex] - output[secondIndex]);
		default:
			return 0;
		}
	}

	public synchronized void move(int fromIndex, ArrayType fromArray, int toIndex, ArrayType toArray) {
		moves++;
		AlgorithmCommand.Direction direction = calculateDirection(fromArray, toArray);
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.MOVE, fromIndex, toIndex, direction));
		state = SortingBenchmark.State.WAIT;
		try {
			while (state != SortingBenchmark.State.GO) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setNewArray(int[] theArray) {
		initialize(theArray, viewX, viewY, viewWidth, (viewHeight - Visualizer.BORDER_THICKNESS));
	}

	@Override
	void drawInfo() {
		valuesView.drawName(algorithmName);
		valuesView.drawComparisons(comparisons);
		valuesView.drawMoves(moves);
	}

	protected AlgorithmCommand.Direction calculateDirection(ArrayType fromArray, ArrayType toArray) {
		if (fromArray == toArray) {
			if (fromArray == ArrayType.MAIN) {
				return AlgorithmCommand.Direction.IN_MAIN;
			} else {
				return AlgorithmCommand.Direction.IN_OUTPUT;
			}
		} else {
			if (fromArray == ArrayType.MAIN) {
				return AlgorithmCommand.Direction.MAIN_TO_OUTPUT;
			} else {
				return AlgorithmCommand.Direction.OUTPUT_TO_MAIN;
			}
		}
	}
}