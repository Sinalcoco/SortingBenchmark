import java.util.ArrayList;

import processing.core.PApplet;

enum ArrayType {
	MAIN, OUTPUT
}

abstract class AbstractAlgorithmHelper {
	protected int[] values;
	protected Visualizer valuesView;

	protected int comparisons;
	protected int moves;

	protected ArrayList<AlgorithmCommand> commands;

	protected boolean ready;

	protected String algorithmName;

	protected int viewX, viewY, viewWidth, viewHeight;

	protected SortingBenchmark.State state;

	protected PApplet processing;

	protected AbstractAlgorithmHelper(PApplet theParent) {
		processing = theParent;
	};

	public AbstractAlgorithmHelper(PApplet theParent, int[] theArray, int theX, int theY, int theWidth, int theHeight) {
		initialize(theParent, theArray, theX, theY, theWidth, theHeight);
	}

	protected void initialize(PApplet theParent, int[] theArray, int theX, int theY, int theWidth, int theHeight) {
		processing = theParent;
		viewX = theX;
		viewY = theY;
		viewWidth = theWidth;
		viewHeight = theHeight;
		values = new int[theArray.length];
		PApplet.arrayCopy(theArray, values);

		valuesView = new Visualizer(processing, viewX, viewY, viewWidth, viewHeight, values.length);

		comparisons = 0;
		moves = 0;
		commands = new ArrayList<AlgorithmCommand>();
		ready = true;
		state = SortingBenchmark.State.WAIT;
	}

	public void processChange() {
		drawValues();

		processCommands();

		drawInfo();
	}

	protected void drawValues() {
		valuesView.drawArray(values);
	}

	protected void processCommands() {
		if (commands.size() > 0) {
			ready = false;
			AlgorithmCommand command = commands.get(0);
			switch (command.type()) {
			case COMPARE:
				valuesView.drawRect(command.first(), values, valuesView.COMPARE_COLOR);
				valuesView.drawRect(command.second(), values, valuesView.COMPARE_COLOR);
				break;
			case SWAP:
				valuesView.drawRect(command.first(), values, valuesView.MOVE_COLOR);
				valuesView.drawRect(command.second(), values, valuesView.MOVE_COLOR);
				int temp = values[command.first()];
				values[command.first()] = values[command.second()];
				values[command.second()] = temp;
				break;
			}
			commands.remove(0);
		}
		if (commands.size() == 0)
			ready = true;
	}

	protected void drawInfo() {
		valuesView.drawName(algorithmName);
		valuesView.drawComparisons(comparisons);
		valuesView.drawMoves(moves);
	}

	public synchronized int compare(int firstIndex, int secondIndex) {

		state = SortingBenchmark.State.WAIT;
		comparisons++;
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.COMPARE, firstIndex, secondIndex));
		try {
			while (state != SortingBenchmark.State.GO) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (values[firstIndex] - values[secondIndex]);
	}

	public void move(int fromIndex, int toIndex) {
		move(fromIndex, ArrayType.MAIN, toIndex, ArrayType.MAIN);
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

	public void swap(int firstIndex, int secondIndex) {
		swap(firstIndex, ArrayType.MAIN, secondIndex, ArrayType.MAIN);
	}

	public synchronized void swap(int firstIndex, ArrayType fromArray, int secondIndex, ArrayType toArray) {
		AlgorithmCommand.Direction direction = calculateDirection(fromArray, toArray);

		state = SortingBenchmark.State.WAIT;
		// store(firstIndex);
		// store(secondIndex);
		moves += 2;
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.SWAP, firstIndex, secondIndex, direction));
		try {
			while (state != SortingBenchmark.State.GO) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public int arrayLength() {
		return values.length;
	}

	public boolean ready() {
		return ready;
	}

	public synchronized void setState(SortingBenchmark.State theState) {
		state = theState;
		notify();
	}

	public void setName(String theName) {
		algorithmName = theName;
	}

	public void setNewArray(int[] theArray) {
		initialize(processing, theArray, viewX, viewY, viewWidth, viewHeight);
	}
}