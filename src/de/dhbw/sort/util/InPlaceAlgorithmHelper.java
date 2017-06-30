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
        this.drawValues();
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

    private void blackout(int index) {
        screen.fill(0, 0, 0);
        screen.drawRect(index*width,0,width,screen.getHeight());
    }

    private void reDraw(int index){
        blackout(index);
        screen.fill(255, 255, 255);
        screen.drawRect(index * width,
             (screen.getHeight() - (height * graphicsValues[index])), width,
               (height * graphicsValues[index]));

    }
    public void nextFrame() {




        int firstIndex =0;
        int secondIndex=0;
        if (mov.peek() != null) {
            switch (mov.poll()) {

                case COMPARE:
                    gravComp++;
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
                    grafMoves += 3;
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
                    break;
                case MOVE:
                    break;
                default:
                    break;


            }

        }
        drawInfo();
        this.screen.addFrame();
        reDraw(firstIndex);
        reDraw(secondIndex);

    }

    public void drawInfo() {
        screen.fill(0,0,0);
        screen.drawRect(0,0,screen.getWidth(),30);
        screen.fill(255, 255, 255);
        screen.text(algorithmName, 0, 10);
        screen.text("Comparisons: " + gravComp, 0, 20);
        screen.text("Moves: " + grafMoves, 0, 30);

    }

    public void setNewArray(int[] theArray) {
        initialize(screen, theArray);
    }
}