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
        if (!ready && mov.peek() != null && (indexes.size() > 1 || mov.peek() == Moves.READY)) {

            switch (mov.poll()) {

                case COMPARE:
                    graphComparisons++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();

                    blackout(firstIndex);
                    blackout(secondIndex);

                    screen.fill(0, 255, 0);
                    screen.drawRect(firstIndex * width, (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                            (height * graphicsValues[firstIndex]));
                    screen.drawRect(secondIndex * width, (screen.getHeight() - (height * graphicsValues[secondIndex])),
                            width, (height * graphicsValues[secondIndex]));
                    break;
                case SWAP:
                    graphMoves += 3;
                    graphSwaps++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();

                    blackout(firstIndex);
                    blackout(secondIndex);

                    screen.fill(255, 0, 0);
                    screen.drawRect(firstIndex * width, (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                            (height * graphicsValues[firstIndex]));
                    screen.drawRect(secondIndex * width, (screen.getHeight() - (height * graphicsValues[secondIndex])),
                            width, (height * graphicsValues[secondIndex]));

                    int temp = graphicsValues[firstIndex];
                    graphicsValues[firstIndex] = graphicsValues[secondIndex];
                    graphicsValues[secondIndex] = temp;

                    this.screen.addFrame();
                    this.screen.addFrame();

                    break;
                case MOVE:
                    graphMoves++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();
                    blackout(firstIndex);
                    blackout(secondIndex);

                    screen.fill(255, 0, 0);

                    screen.drawRect(secondIndex * width, (screen.getHeight() - (height * graphicsValues[firstIndex])),
                            width, (height * graphicsValues[firstIndex]));

                    graphicsValues[secondIndex] = graphicsValues[firstIndex];
                    graphicsValues[firstIndex] = 0;

                    break;
                case SET:
                    graphMoves++;
                    firstIndex = secondIndex = indexes.poll();
                    int r = indexes.poll();

                    graphicsValues[firstIndex] = r;

                    break;
                case READY:
                    this.ready = true;
                    break;
                default:
                    break;
            }

        }
//        boolean g  =false;
//        int i = 0;
//        while (!g){
            this.screen.addFrame();
//            System.out.println("Status: "+g+"| retry count: "+ i);
//            i++;
//        }
//        i = 0;
        reDraw(firstIndex);
        reDraw(secondIndex);
        drawInfo();

    }

    public void drawInfo() {
        screen.fill(0, 0, 0);
        screen.drawRect(0, 0, screen.getWidth(), 40);
        screen.fill(255, 255, 255);
        screen.text(algorithmName + "                    ValueCount: " + this.graphicsValues.length, 0, 10);
        screen.text("Comparisons: " + graphComparisons, 0, 20);
        screen.text("Swaps: " + graphSwaps, 0, 30);
        screen.text("Moves: " + graphMoves, 0, 40);

    }

}