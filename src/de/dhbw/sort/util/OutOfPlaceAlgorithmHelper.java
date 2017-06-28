package de.dhbw.sort.util;

import java.util.ArrayList;

import de.dhbw.sort.SortingBenchmark;
import de.dhbw.sort.util.AlgorithmCommand.Direction;
import de.dhbw.sort.visualize.Graphics;
import processing.core.PApplet;

public class OutOfPlaceAlgorithmHelper extends AbstractAlgorithmHelper {

	public enum ArrayType {
		MAIN, OUTPUT
	}

	private int[] output;
	private int[] graphicsOutput;
    private int graphMoves = 0;
    private int graphComp = 0;

	public OutOfPlaceAlgorithmHelper(Graphics theScreen, int[] theArray) {
		super(theScreen, theArray);
		reset();
		screen.setHelper(this);
	}

	public void drawValues() {
		 screen.fill(255, 255, 255);
	        screen.drawBackground(0, 0, 0);
	        for (int i = 0; i < graphicsValues.length; i++)
            {

                screen.fill(255, 255, 255);
                screen.drawRect(i * width, (int) (screen.getHeight() - (height * graphicsValues[i])), width,
                                (int) (height * graphicsValues[i]));


            }
	        for (int i = 0; i < graphicsOutput.length; i++)
            {

                screen.fill(255, 255, 255);
                screen.drawRect(i * width, (int) (screen.getHeight() - (height * graphicsOutput[i])), width,
                                (int) (height * graphicsOutput[i]));


            }
	}
	public void nextFrame() {
        drawValues();
        drawInfo();
        int firstIndex;
        int secondIndex;
        int directionOrdinal;
        if ( mov.peek() != null )
            {
                switch (mov.poll())
                    {

                        case COMPARE:
                            graphComp++;
                            firstIndex = indexes.poll();
                            secondIndex = indexes.poll();
                            directionOrdinal = indexes.poll();
                            
                            switch (Direction.values()[directionOrdinal]) {
            				case IN_MAIN:
            					screen.fill(0, 255, 0);
                                screen.drawRect(firstIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                                                (int) (height * graphicsValues[firstIndex]));
                                screen.drawRect(secondIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsValues[secondIndex])), width,
                                                (int) (height * graphicsValues[secondIndex]));
            					break;
            				case IN_OUTPUT://TODO split screen in half
            					screen.fill(0, 255, 0);
                                screen.drawRect(firstIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsOutput[firstIndex])), width,
                                                (int) (height * graphicsValues[firstIndex]));
                                screen.drawRect(secondIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsOutput[secondIndex])), width,
                                                (int) (height * graphicsValues[secondIndex]));
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
                            
                            //TODO change switch include move logic
                            switch (Direction.values()[directionOrdinal]) {
            				case IN_MAIN:
            					screen.fill(0, 255, 0);
                                screen.drawRect(firstIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                                                (int) (height * graphicsValues[firstIndex]));
                                screen.drawRect(secondIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsValues[secondIndex])), width,
                                                (int) (height * graphicsValues[secondIndex]));
            					break;
            				case IN_OUTPUT://TODO split screen in half
            					screen.fill(0, 255, 0);
                                screen.drawRect(firstIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsOutput[firstIndex])), width,
                                                (int) (height * graphicsValues[firstIndex]));
                                screen.drawRect(secondIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsOutput[secondIndex])), width,
                                                (int) (height * graphicsValues[secondIndex]));
            					break;
            				case MAIN_TO_OUTPUT:
            					break;
            				case OUTPUT_TO_MAIN:
            					break;
            				}
                            //TODO include in switch
                            int temp = graphicsValues[firstIndex];
                            graphicsValues[firstIndex] = graphicsValues[secondIndex];
                            graphicsValues[secondIndex] = temp;
                            break;
                        case MOVE:
                        	graphMoves++;
                        	firstIndex = indexes.poll();
                            secondIndex = indexes.poll();
                            directionOrdinal = indexes.poll();
                            //TODO change switch to to moves
                            switch (Direction.values()[directionOrdinal]) {
            				case IN_MAIN:
            					screen.fill(0, 255, 0);
                                screen.drawRect(firstIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                                                (int) (height * graphicsValues[firstIndex]));
                                screen.drawRect(secondIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsValues[secondIndex])), width,
                                                (int) (height * graphicsValues[secondIndex]));
            					break;
            				case IN_OUTPUT://TODO split screen in half
            					screen.fill(0, 255, 0);
                                screen.drawRect(firstIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsOutput[firstIndex])), width,
                                                (int) (height * graphicsValues[firstIndex]));
                                screen.drawRect(secondIndex * width,
                                                (int) (screen.getHeight() - (height * graphicsOutput[secondIndex])), width,
                                                (int) (height * graphicsValues[secondIndex]));
            					break;
            				case MAIN_TO_OUTPUT:
            					break;
            				case OUTPUT_TO_MAIN:
            					break;
            				}
                            break;
                        default:
                            break;
                    }
            }
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
		indexes.add(direction.ordinal());	//Adding the direction information
		
		switch (direction) {
		case IN_MAIN:
			return (values[firstIndex] - values[secondIndex]);
		case IN_OUTPUT:
			return (output[firstIndex] - output[secondIndex]);
		//TODO add rest of the cases
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
		indexes.add(direction.ordinal());	//Adding the direction information
	}

	public void setNewArray(int[] theArray) {
		super.initialize(screen, theArray);
		reset();
	}

	private void reset() {
		graphicsOutput = new int [graphicsValues.length];
		output = new int [values.length];
	}

	@Override
	public void drawInfo() {
        screen.fill(255, 255, 255);
        screen.text(algorithmName, 0, 10);
        screen.text("Comparisons: " + graphComp, 0, 20);
        screen.text("Moves: " + graphMoves, 0, 30);
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