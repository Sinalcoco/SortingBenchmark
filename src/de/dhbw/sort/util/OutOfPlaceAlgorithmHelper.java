package de.dhbw.sort.util;

import java.util.ArrayList;

import de.dhbw.sort.SortingBenchmark;
import de.dhbw.sort.util.AlgorithmCommand.Direction;
import de.dhbw.sort.visualize.Graphics;
import de.dhbw.sort.visualize.SplitGraphics;
import processing.core.PApplet;

public class OutOfPlaceAlgorithmHelper extends AbstractAlgorithmHelper {

	public enum ArrayType {
		MAIN, OUTPUT
	}

	private int[] output;
	private int[] graphicsOutput;
	private int graphMoves = 0;
	private int graphComp = 0;
	private SplitGraphics splitScreen;

	public OutOfPlaceAlgorithmHelper(SplitGraphics splitGraphics, int[] theArray) {
		super(splitGraphics, theArray);
		reset();
		splitScreen = splitGraphics;
		this.drawValues();
		splitGraphics.addFrame();
	}

	public void drawValues() {
		splitScreen.drawBackground(0, 0, 0);
		splitScreen.fill(255, 255, 255);
		for (int i = 0; i < graphicsValues.length; i++) {

			splitScreen.drawRect(i * width,   (splitScreen.getTopHeight() - (height * graphicsValues[i])), width,
					  (height * graphicsValues[i]));

		}
		for (int i = 0; i < graphicsOutput.length; i++) {

			splitScreen.drawRectBottom(i * width,   (splitScreen.getBottomHeight() - (height * graphicsOutput[i])),
					width,   (height * graphicsOutput[i]));

		}
	}

	public void nextFrame() {
		drawValues();
		drawInfo();
		int firstIndex;
		int secondIndex;
		int directionOrdinal;
		if (mov.peek() != null) {
			switch (mov.poll()) {
			case COMPARE:
				splitScreen.fill(0, 255, 0);
				graphComp++;
				firstIndex = indexes.poll();
				secondIndex = indexes.poll();
				directionOrdinal = indexes.poll();

				switch (Direction.values()[directionOrdinal]) {
				case IN_MAIN:
					splitScreen.drawRect(firstIndex * width,
							  (splitScreen.getTopHeight() - (height * graphicsValues[firstIndex])), width,
							  (height * graphicsValues[firstIndex]));
					splitScreen.drawRect(secondIndex * width,
							  (splitScreen.getTopHeight() - (height * graphicsValues[secondIndex])), width,
							  (height * graphicsValues[secondIndex]));
					break;
				case IN_OUTPUT:// TODO split splitScreen in half
					splitScreen.drawRectBottom(firstIndex * width,
							(splitScreen.getBottomHeight() - (height * graphicsOutput[firstIndex])), width,
							(height * graphicsOutput[firstIndex]));
					splitScreen.drawRectBottom(secondIndex * width,
							(splitScreen.getBottomHeight() - (height * graphicsOutput[secondIndex])), width,
							(height * graphicsOutput[secondIndex]));
					break;
				case MAIN_TO_OUTPUT:
					break;
				case OUTPUT_TO_MAIN:
					break;
				}
				break;
			case SWAP:
				graphMoves += 3;
				firstIndex = indexes.poll();
				secondIndex = indexes.poll();
				directionOrdinal = indexes.poll();

				// TODO change switch include move logic
				// switch (Direction.values()[directionOrdinal]) {
				// case IN_MAIN:
				// splitScreen.fill(0, 255, 0);
				// splitScreen.drawRect(firstIndex * width,
				//   (splitScreen.getHeight() - (height *
				// graphicsValues[firstIndex])), width,
				//   (height * graphicsValues[firstIndex]));
				// splitScreen.drawRect(secondIndex * width,
				//   (splitScreen.getHeight() - (height *
				// graphicsValues[secondIndex])), width,
				//   (height * graphicsValues[secondIndex]));
				// break;
				// case IN_OUTPUT://TODO split splitScreen in half
				// splitScreen.fill(0, 255, 0);
				// splitScreen.drawRect(firstIndex * width,
				//   (splitScreen.getHeight() - (height *
				// graphicsOutput[firstIndex])), width,
				//   (height * graphicsValues[firstIndex]));
				// splitScreen.drawRect(secondIndex * width,
				//   (splitScreen.getHeight() - (height *
				// graphicsOutput[secondIndex])), width,
				//   (height * graphicsValues[secondIndex]));
				// break;
				// case MAIN_TO_OUTPUT:
				// break;
				// case OUTPUT_TO_MAIN:
				// break;
				// }
				// TODO include in switch
				int temp = graphicsValues[firstIndex];
				graphicsValues[firstIndex] = graphicsValues[secondIndex];
				graphicsValues[secondIndex] = temp;
				break;
			case MOVE:
				splitScreen.fill(255, 0, 0);
				graphMoves++;
				firstIndex = indexes.poll();
				secondIndex = indexes.poll();
				directionOrdinal = indexes.poll();
				// TODO change switch to to moves
				switch (Direction.values()[directionOrdinal]) {
				case IN_MAIN:
					splitScreen.drawRect(firstIndex * width,
							  (splitScreen.getTopHeight() - (height * graphicsValues[firstIndex])), width,
							  (height * graphicsValues[firstIndex]));
					splitScreen.drawRect(secondIndex * width,
							  (splitScreen.getTopHeight() - (height * graphicsValues[secondIndex])), width,
							  (height * graphicsValues[secondIndex]));
					break;
				case IN_OUTPUT:// TODO split splitScreen in half
					splitScreen.drawRect(firstIndex * width,
							  (splitScreen.getBottomHeight() - (height * graphicsOutput[firstIndex])), width,
							  (height * graphicsValues[firstIndex]));
					splitScreen.drawRect(secondIndex * width,
							  (splitScreen.getBottomHeight() - (height * graphicsOutput[secondIndex])), width,
							  (height * graphicsValues[secondIndex]));
					break;
				case MAIN_TO_OUTPUT:
					splitScreen.drawRect(firstIndex * width,
							  (splitScreen.getTopHeight() - (height * graphicsValues[firstIndex])), width,
							  (height * graphicsValues[firstIndex]));
					splitScreen.drawRectBottom(secondIndex * width,
							  (splitScreen.getBottomHeight() - (height * graphicsOutput[secondIndex])), width,
							  (height * graphicsOutput[secondIndex]));

					graphicsOutput[secondIndex] = graphicsValues[firstIndex];
					graphicsValues[firstIndex] = 0;
					break;
				case OUTPUT_TO_MAIN:
					splitScreen.drawRectBottom(firstIndex * width,
							  (splitScreen.getBottomHeight() - (height * graphicsOutput[firstIndex])), width,
							  (height * graphicsOutput[firstIndex]));
					splitScreen.drawRect(secondIndex * width,
							  (splitScreen.getTopHeight() - (height * graphicsValues[secondIndex])), width,
							  (height * graphicsValues[secondIndex]));
					
					graphicsValues[secondIndex] = graphicsOutput[firstIndex];
					graphicsOutput[firstIndex] = 0;
					break;
				}
				break;
			default:
				break;
			}
		}
		splitScreen.addFrame();
	}

	public synchronized void swap(int firstIndex, ArrayType fromArray, int secondIndex, ArrayType toArray) {
		moves += 3;
		AlgorithmCommand.Direction direction = calculateDirection(fromArray, toArray);
		mov.add(Moves.SWAP);
		indexes.add(firstIndex);
		indexes.add(secondIndex);
		indexes.add(direction.ordinal());

		int temp = values[firstIndex];
		values[firstIndex] = values[secondIndex];
		values[secondIndex] = temp;

	}

	public synchronized int compare(int firstIndex, ArrayType fromArray, int secondIndex, ArrayType toArray) {
		comparisons++;
		AlgorithmCommand.Direction direction = calculateDirection(fromArray, toArray);
		mov.add(Moves.COMPARE);
		indexes.add(firstIndex);
		indexes.add(secondIndex);
		indexes.add(direction.ordinal()); // Adding the direction information

		switch (direction) {
		case IN_MAIN:
			return (values[firstIndex] - values[secondIndex]);
		case IN_OUTPUT:
			return (output[firstIndex] - output[secondIndex]);
		// TODO add rest of the cases
		default:
			return 0;
		}
	}

	public synchronized void move(int fromIndex, ArrayType fromArray, int toIndex, ArrayType toArray) {
		moves++;
		AlgorithmCommand.Direction direction = calculateDirection(fromArray, toArray);

		mov.add(Moves.MOVE);
		indexes.add(fromIndex);
		indexes.add(toIndex);
		indexes.add(direction.ordinal()); // Adding the direction information

		switch (direction) {
		case MAIN_TO_OUTPUT:
			output[toIndex] = values[fromIndex];
			values[fromIndex] = 0;
			break;
		case OUTPUT_TO_MAIN:
			values[toIndex] = output[fromIndex];
			output[fromIndex] = 0;
			break;
		}
	}

	public void setNewArray(int[] theArray) {
		super.initialize(splitScreen, theArray);
		reset();
		splitScreen.addFrame();
	}

	private void reset() {
		graphicsOutput = new int[graphicsValues.length];
		output = new int[values.length];
		height /= 2;
	}

	@Override
	public void drawInfo() {
		splitScreen.fill(255, 255, 255);
		splitScreen.text(algorithmName, 0, 10);
		splitScreen.text("Comparisons: " + graphComp, 0, 20);
		splitScreen.text("Moves: " + graphMoves, 0, 30);
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