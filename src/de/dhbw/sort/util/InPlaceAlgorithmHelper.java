package de.dhbw.sort.util;

import de.dhbw.sort.Visualizer;
import de.dhbw.sort.visualize.Graphics;

public class InPlaceAlgorithmHelper extends AbstractAlgorithmHelper {

    private Graphics valuesView;


    public InPlaceAlgorithmHelper(Graphics screen, int[] theArray) {
        super(screen, theArray);

    }

    @Override
    public void drawValues() {
        screen.fill(255, 255, 255);
        screen.drawBackground(0,0,0);
        for (int i = 0; i < values.length; i++)
            {

                        screen.fill(255,255,255);
                        screen.drawRect(i * width, (int) (screen.getHeight() - (height * values[i])), width,
                                        (int) (height * values[i]));


            }
    }


    public void processCommands() {


        if (commands.size() > 0)
            {
                ready = false;
                AlgorithmCommand command = commands.get(0);
                switch (command.type())
                    {
                        case COMPARE:
                            screen.fill(0, 255, 0);
                            screen.drawRect(command.first() * width,
                                            (int) (screen.getHeight() - (height * values[command.first()])), width,
                                            (int) (height * values[command.first()]));
                            screen.drawRect(command.second() * width,
                                            (int) (screen.getHeight() - (height * values[command.second()])), width,
                                            (int) (height * values[command.second()]));
                            break;
                        case SWAP:
                            screen.fill(255, 0, 0);
                            screen.drawRect(command.first() * width,
                                            (int) (screen.getHeight() - (height * values[command.first()])), width,
                                            (int) (height * values[command.first()]));
                            screen.drawRect(command.second() * width,
                                            (int) (screen.getHeight() - (height * values[command.second()])), width,
                                            (int) (height * values[command.second()]));
                            int temp = values[command.first()];
                            values[command.first()] = values[command.second()];
                            values[command.second()] = temp;
                            break;
                        case HIGHLIGHT:
                            //                            valuesView.drawRect(command.first(), values, command.second
                            // ());
                    }
                commands.remove(0);
            }
        if (commands.size() == 0)
            {
                screen.finished(true);
                ready = true;
            }
    }

    public void drawInfo() {
        screen.fill(255,255,255);
        screen.text(algorithmName, 0, 10);
        screen.text("Comparisons: " + comparisons, 0, 20);
        screen.text("Moves: " + moves, 0, 30);

    }

    public void setNewArray(int[] theArray) {
        initialize(screen, theArray);
    }
}