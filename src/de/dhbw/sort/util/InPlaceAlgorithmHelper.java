package de.dhbw.sort.util;

import de.dhbw.sort.algorithms.SortingAlgorithm;
import de.dhbw.sort.visualize.Graphics;

public class InPlaceAlgorithmHelper extends AbstractAlgorithmHelper {


    public InPlaceAlgorithmHelper(Graphics screen, int[] theArray, SortingAlgorithm sort) {
        super(screen, theArray, sort);
    }

    @Override
    public void drawValues() {
        screen.fill(255, 255, 255);
        screen.drawBackground(0, 0, 0);
        for (int i = 0; i < graphicsValues.length; i++) {

            screen.fill(255, 255, 255);
            screen.drawRect(i * width, screen.getHeight() - (height * graphicsValues[i]), width,
                    (height * graphicsValues[i]));


        }
    }


    public void nextFrame() {
        int firstIndex = 0;
        int secondIndex = 0;
        if (!ready && mov.peek() != null) {
            switch (mov.poll()) {

                case COMPARE:
                    graphComparisons++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();

                    blackout(firstIndex);
                    blackout(secondIndex);

                    screen.fill(0, 255, 0);
                    screen.drawRect(firstIndex * width,
                            (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                            (height * graphicsValues[firstIndex]));
                    screen.drawRect(secondIndex * width,
                            (screen.getHeight() - (height * graphicsValues[secondIndex])), width,
                            (height * graphicsValues[secondIndex]));
                    break;
                case SWAP:
                    graphMoves += 3;
                    graphSwaps++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();

                    blackout(firstIndex);
                    blackout(secondIndex);

                    screen.fill(255, 0, 0);
                    screen.drawRect(firstIndex * width,
                            (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                            (height * graphicsValues[firstIndex]));
                    screen.drawRect(secondIndex * width,
                            (screen.getHeight() - (height * graphicsValues[secondIndex])), width,
                            (height * graphicsValues[secondIndex]));

                    int temp = graphicsValues[firstIndex];
                    graphicsValues[firstIndex] = graphicsValues[secondIndex];
                    graphicsValues[secondIndex] = temp;


                    this.screen.addFrame();
                    this.screen.addFrame();

                    break;
                case MOVE:
                    break;
                case READY:
                    this.ready = true;

                    break;
                default:
                    break;
            }
        }

        this.screen.addFrame();
        reDraw(firstIndex);
        reDraw(secondIndex);
        drawInfo();

    }

    public void drawInfo() {
        screen.fill(0, 0, 0);
        screen.drawRect(0, 0, screen.getWidth(), 40);
        screen.fill(255, 255, 255);
        screen.text(algorithmName, 0, 10);
        screen.text("Comparisons: " + graphComparisons, 0, 20);
        screen.text("Swaps: " + graphSwaps, 0, 30);
        screen.text("Moves: " + graphMoves, 0, 40);

    }

    public void resetAlgorithm(int[] theArray) {
        super.resetAlgorithm(theArray);
    }

    @Override
    public void resetHelper(int[] peek) {

        graphSwaps = 0;
        graphMoves = 0;
        graphComparisons = 0;
        super.resetHelper(peek);
        ready = false;


    }
}