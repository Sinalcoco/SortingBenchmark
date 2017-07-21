package de.dhbw.sort.util;

import de.dhbw.sort.algorithms.SortingAlgorithm;
import de.dhbw.sort.util.AlgorithmCommand.Direction;
import de.dhbw.sort.visualize.SplitGraphics;

import java.util.Arrays;

public class OutOfPlaceAlgorithmHelper extends AbstractAlgorithmHelper {

    private int[] output;
    private int[] graphicsOutput;

    private int graphMoves = 0;
    private int graphComp = 0;

    private SplitGraphics splitScreen;

    public OutOfPlaceAlgorithmHelper(SplitGraphics splitGraphics, int[] theArray, SortingAlgorithm sort) {
        super(splitGraphics, theArray);

        this.sort = sort;

        this.sort.setHelper(this);
        this.splitScreen = splitGraphics;
        this.output = new int[values.length];
        this.graphicsOutput = Arrays.copyOf(output, output.length);

        this.height = getHeight();

        this.drawValues();

        splitGraphics.addFrame();
    }

    private float getHeight() {
        return (splitScreen.getHeight() / splitScreen.getAmountOfScreens() - liableHeight) / big;
    }

    public void drawValues() {
        splitScreen.drawBackground(0, 0, 0);
        splitScreen.fill(255, 255, 255);
        for (int i = 0; i < graphicsValues.length; i++) {

            splitScreen.drawRect(i * width, (splitScreen.getHeight(0) - (height * graphicsValues[i])), width,
                    (height * graphicsValues[i]), 0);

        }
        for (int i = 0; i < graphicsOutput.length; i++) {

            splitScreen.drawRect(i * width, (splitScreen.getHeight(1) - (height * graphicsOutput[i])), width,
                    (height * graphicsOutput[i]), 1);

        }
    }

    private void blackout(int index, int i) {
        screen.fill(0, 0, 0);
        if (i == 0) {
            splitScreen.drawRect(index * width, liableHeight, width, splitScreen.getHeight(0) - liableHeight, 0);
        } else {
            splitScreen.drawRect(index * width, liableHeight, width, splitScreen.getHeight(0) - liableHeight, 1);
        }
        //This is not reset by the next draw operation?
        screen.fill(255, 255, 255);
    }

    protected void reDraw(int index, int i) {
        screen.fill(255, 255, 255);
        blackout(index, i);
        if (i == 0) {
            splitScreen.drawRect(index * width, (splitScreen.getHeight(0) - (height * graphicsValues[index])), width, height * graphicsValues[index], i);
        } else if (i == 1) {
            splitScreen.drawRect(index * width, (splitScreen.getHeight(1) - (height * graphicsOutput[index])), width, height * graphicsOutput[index], i);
        }
    }

    public void nextFrame() {
        int firstIndex = 0;
        int secondIndex = 0;
        drawInfo();
        int directionOrdinal;
        if (mov.peek() != null) {
            switch (mov.poll()) {
                case COMPARE:
                    graphComp++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();
                    directionOrdinal = indexes.poll();

                    switch (Direction.values()[directionOrdinal]) {
                        case IN_MAIN:
                            blackout(firstIndex, 0);
                            blackout(secondIndex, 0);
                            splitScreen.fill(0, 255, 0);
                            splitScreen.drawRect(firstIndex * width,
                                    (splitScreen.getHeight(0) - (height * graphicsValues[firstIndex])), width,
                                    (height * graphicsValues[firstIndex]), 0);
                            splitScreen.drawRect(secondIndex * width,
                                    (splitScreen.getHeight(0) - (height * graphicsValues[secondIndex])), width,
                                    (height * graphicsValues[secondIndex]), 0);
                            break;
                        case IN_OUTPUT:// TODO split splitScreen in half
                            blackout(firstIndex, 1);
                            blackout(secondIndex, 1);
                            splitScreen.fill(0, 255, 0);
                            splitScreen.drawRect(firstIndex * width,
                                    (splitScreen.getHeight(1) - (height * graphicsOutput[firstIndex])), width,
                                    (height * graphicsOutput[firstIndex]), 1);
                            splitScreen.drawRect(secondIndex * width,
                                    (splitScreen.getHeight(1) - (height * graphicsOutput[secondIndex])), width,
                                    (height * graphicsOutput[secondIndex]), 1);
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
                    // (splitScreen.getHeight() - (height *
                    // graphicsValues[firstIndex])), width,
                    // (height * graphicsValues[firstIndex]));
                    // splitScreen.drawRect(secondIndex * width,
                    // (splitScreen.getHeight() - (height *
                    // graphicsValues[secondIndex])), width,
                    // (height * graphicsValues[secondIndex]));
                    // break;
                    // case IN_OUTPUT://TODO split splitScreen in half
                    // splitScreen.fill(0, 255, 0);
                    // splitScreen.drawRect(firstIndex * width,
                    // (splitScreen.getHeight() - (height *
                    // graphicsOutput[firstIndex])), width,
                    // (height * graphicsValues[firstIndex]));
                    // splitScreen.drawRect(secondIndex * width,
                    // (splitScreen.getHeight() - (height *
                    // graphicsOutput[secondIndex])), width,
                    // (height * graphicsValues[secondIndex]));
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
                    graphMoves++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();
                    directionOrdinal = indexes.poll();
                    // TODO change switch to to moves
                    switch (Direction.values()[directionOrdinal]) {
                        case IN_MAIN:
                            blackout(firstIndex, 0);
                            blackout(secondIndex, 0);
                            splitScreen.fill(255, 0, 0);
                            splitScreen.drawRect(firstIndex * width,
                                    (splitScreen.getHeight(0) - (height * graphicsValues[firstIndex])), width,
                                    (height * graphicsValues[firstIndex]), 0);
                            splitScreen.drawRect(secondIndex * width,
                                    (splitScreen.getHeight(0) - (height * graphicsValues[secondIndex])), width,
                                    (height * graphicsValues[secondIndex]), 0);
                            break;
                        case IN_OUTPUT:
                            blackout(firstIndex, 1);
                            blackout(secondIndex, 1);
                            splitScreen.fill(255, 0, 0);
                            splitScreen.drawRect(firstIndex * width,
                                    (splitScreen.getHeight(1) - (height * graphicsOutput[firstIndex])), width,
                                    (height * graphicsValues[firstIndex]), 0);
                            splitScreen.drawRect(secondIndex * width,
                                    (splitScreen.getHeight(1) - (height * graphicsOutput[secondIndex])), width,
                                    (height * graphicsValues[secondIndex]), 0);
                            break;
                        case MAIN_TO_OUTPUT:
                            // blackout(firstIndex,0);
                            // blackout(secondIndex,1);
                            splitScreen.fill(255, 0, 0);
                            splitScreen.drawRect(firstIndex * width,
                                    (splitScreen.getHeight(0) - (height * graphicsValues[firstIndex])), width,
                                    (height * graphicsValues[firstIndex]), 0);
                            splitScreen.drawRect(secondIndex * width,
                                    (splitScreen.getHeight(1) - (height * graphicsOutput[secondIndex])), width,
                                    (height * graphicsOutput[secondIndex]), 1);

                            graphicsOutput[secondIndex] = graphicsValues[firstIndex];
                            graphicsValues[firstIndex] = 0;
                            break;
                        case OUTPUT_TO_MAIN:
                            blackout(secondIndex, 0);
                            blackout(firstIndex, 1);
                            splitScreen.fill(255, 0, 0);
                            splitScreen.drawRect(firstIndex * width,
                                    (splitScreen.getHeight(1) - (height * graphicsOutput[firstIndex])), width,
                                    (height * graphicsOutput[firstIndex]), 1);
                            splitScreen.drawRect(secondIndex * width,
                                    (splitScreen.getHeight(0) - (height * graphicsValues[secondIndex])), width,
                                    (height * graphicsValues[secondIndex]), 0);

                            graphicsValues[secondIndex] = graphicsOutput[firstIndex];
                            graphicsOutput[firstIndex] = 0;
                            break;
                    }
                    break;
                case READY:
                    this.ready = true;
                default:
                    break;
            }
            while (!this.splitScreen.addFrame())
    			;
            reDraw(firstIndex, 0);
            reDraw(firstIndex, 1);
            reDraw(secondIndex, 0);
            reDraw(secondIndex, 1);
        }
    }

    public void swap(int firstIndex, ArrayType fromArray, int secondIndex, ArrayType toArray) {
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

    public int compare(int firstIndex, ArrayType fromArray, int secondIndex, ArrayType toArray) {
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

    public void move(int fromIndex, ArrayType fromArray, int toIndex, ArrayType toArray) {
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

    public void resetAlgorithm(int[] theArray) {
        output = new int[theArray.length];
        super.resetAlgorithm(theArray);
    }

    @Override
    public void drawInfo() {
        screen.fill(0, 0, 0);
        screen.drawRect(0, 0, splitScreen.getWidth(), 40);
        splitScreen.fill(255, 255, 255);
        screen.text(algorithmName + "                    ValueCount: " + this.graphicsValues.length, 0, 10);
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

    public synchronized void resetGraphics(int[] peek) {
//        super.resetHelper(peek);
        graphMoves = 0;
        graphComp = 0;
        graphSwaps = 0;

        this.graphicsValues = Arrays.copyOf(peek, peek.length);
        this.graphicsOutput = new int[peek.length];

        this.big = 0;
        for (int a : graphicsValues) {
            if (a > this.big) {
                this.big = a;
            }
        }

        width = screen.getWidth() / graphicsValues.length;
        height= getHeight();
        this.drawValues();
        this.ready = false;
        this.notify();

    }

    public enum ArrayType {
        MAIN, OUTPUT
    }

}