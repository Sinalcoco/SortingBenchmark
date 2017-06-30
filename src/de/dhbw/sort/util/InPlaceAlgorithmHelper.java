package de.dhbw.sort.util;

import de.dhbw.sort.visualize.Graphics;

import java.util.Arrays;

public class InPlaceAlgorithmHelper extends AbstractAlgorithmHelper {

    private Graphics valuesView;
    private int grafMoves = 0;
    private int gravComp = 0;


    public InPlaceAlgorithmHelper(Graphics screen, int[] theArray) {
        super(screen, theArray);
        screen.setHelper(this);
    }

    @Override
    public void drawValues() {
        screen.fill(255, 255, 255);
        screen.drawBackground(0, 0, 0);
        for (int i = 0; i < graphicsValues.length; i++) {

            screen.fill(255, 255, 255);
            screen.drawRect(i * width, (int) (screen.getHeight() - (height * graphicsValues[i])), width,
                    (int) (height * graphicsValues[i]));


        }
    }


    public void nextFrame() {
        drawValues();
        drawInfo();
        int firstIndex;
        int secondIndex;
        if (mov.peek() != null) {
            switch (mov.poll()) {

                case COMPARE:
                    gravComp++;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();

                    screen.fill(0, 255, 0);
                    screen.drawRect(firstIndex * width,
                            (int) (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                            (int) (height * graphicsValues[firstIndex]));
                    screen.drawRect(secondIndex * width,
                            (int) (screen.getHeight() - (height * graphicsValues[secondIndex])), width,
                            (int) (height * graphicsValues[secondIndex]));
                    break;
                case SWAP:
                    grafMoves += 3;
                    firstIndex = indexes.poll();
                    secondIndex = indexes.poll();

                    screen.fill(255, 0, 0);
                    screen.drawRect(firstIndex * width,
                            (int) (screen.getHeight() - (height * graphicsValues[firstIndex])), width,
                            (int) (height * graphicsValues[firstIndex]));
                    screen.drawRect(secondIndex * width,
                            (int) (screen.getHeight() - (height * graphicsValues[secondIndex])), width,
                            (int) (height * graphicsValues[secondIndex]));

                    int temp = graphicsValues[firstIndex];
                    graphicsValues[firstIndex] = graphicsValues[secondIndex];
                    graphicsValues[secondIndex] = temp;
                    break;
                case MOVE:
                    break;
                default:
                    break;


            }

        }
        this.screen.addFrame();

    }

    public void drawInfo() {
        screen.fill(255, 255, 255);
        screen.text(algorithmName, 0, 10);
        screen.text("Comparisons: " + gravComp, 0, 20);
        screen.text("Moves: " + grafMoves, 0, 30);

    }

    public void setNewArray(int[] theArray) {
        initialize(screen, theArray);
    }
}