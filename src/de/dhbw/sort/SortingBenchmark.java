package de.dhbw.sort;

import java.util.ArrayList;

import de.dhbw.sort.algorithms.*;
import de.dhbw.sort.util.AbstractAlgorithmHelper;
import de.dhbw.sort.util.InPlaceAlgorithmHelper;
import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
import de.dhbw.sort.util.Statistics;
import de.dhbw.sort.visualize.*;
import processing.core.PGraphics;
import de.dhbw.sort.visualize.Visualizer;
import processing.core.PApplet;

public class SortingBenchmark extends PApplet {

    public static void main(String[] args) {
        de.dhbw.sort.visualize.Visualizer visualizer = new Visualizer(1200,600,3,3);
        PApplet.runSketch(new String[]{""},visualizer);
        try {
            // Es muss gewartet werden, bis PApplet die setup() Methode von
            // Visualizer aufgerufen hat.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final int AMOUNT = visualizer.getGridNumber();
        while (true) {
            for (int i = 0; i < AMOUNT; i++) {
                PGraphics s = visualizer.getScreen(i);

                s.beginDraw();
                //Wähle irgendeine schöne Farbe
                s.fill(i * (255 / AMOUNT), 255 - (255 / AMOUNT) * i, i / AMOUNT * 255);
                s.rect(0, 0, s.width, s.height);

                String text = Float.toString(visualizer.random(10));
                //Der Text soll schwarz sein
                s.fill(0);
                //Die Positionierung ist einfach Pi mal Daumen.
                s.text(text, s.width / 2 - text.length() * 3, s.height / 2);

                s.endDraw();
            }}
    }

    Statistics stats;
    ArrayList<SortingAlgorithm> sorters;

    final int AMOUNT_OF_VALUES = 80;
    final boolean ascending = true;
    final boolean frameByFrame = false;
    boolean advance = true;
    int run = 20;

    public enum State {
        WAIT, GO
    }


    public void setup() {
        frameRate(50);
        noStroke();

        int[] values;
        if (ascending)
            {
                values = randomIntArray(run);
            } else
            {
                run = AMOUNT_OF_VALUES;
                values = randomIntArray(run);
                //values = sortedIntArray(run);
                //values = invertedIntArray(run);
            }

        stats = new Statistics();

        sorters = new ArrayList<SortingAlgorithm>();

        AbstractAlgorithmHelper bubbleHelper = new InPlaceAlgorithmHelper(this, values, 0, 0, width / 3, height / 3);
        sorters.add(new BubbleSort(bubbleHelper));

        AbstractAlgorithmHelper cocktailHelper = new InPlaceAlgorithmHelper(this, values, width / 3, 0, width / 3,
                                                                            height / 3);
        sorters.add(new CocktailShaker(cocktailHelper));

        AbstractAlgorithmHelper insertionHelper = new InPlaceAlgorithmHelper(this, values, 2 * width / 3, 0, width / 3,
                                                                             height / 3);
        sorters.add(new InsertionSort(insertionHelper));

        AbstractAlgorithmHelper selectionHelper = new InPlaceAlgorithmHelper(this, values, 0, height / 3, width / 3,
                                                                             height / 3);
        sorters.add(new SelectionSort(selectionHelper));

        OutOfPlaceAlgorithmHelper mergeHelper = new OutOfPlaceAlgorithmHelper(this, values, 0, 2 * height / 3,
                                                                              width / 3, height / 3);
        sorters.add(new MergeSort(mergeHelper));

        AbstractAlgorithmHelper heapHelper = new InPlaceAlgorithmHelper(this, values, 2 * width / 3, height / 3,
                                                                        width / 3, height / 3);
        sorters.add(new HeapSort(heapHelper));

        AbstractAlgorithmHelper quickHelper = new InPlaceAlgorithmHelper(this, values, width / 3, 2 * height / 3,
                                                                         width / 3, height / 3);
        sorters.add(new QuickSort(quickHelper));

        for (SortingAlgorithm s : sorters)
            {
                s.start();
            }
    }

    public void keyPressed() {
        advance = true;
    }

    public void draw() {
        if (!frameByFrame || advance)
            {
                advance = false;
                background(0);
                boolean allReady = true;

                for (SortingAlgorithm s : sorters)
                    {

                        s.helper().processChange();
                        if (!s.helper().ready())
                            {
                                allReady = false;
                            }
                    }
                if (allReady)
                    {
                        for (SortingAlgorithm s : sorters)
                            {
                                s.helper().setState(State.GO);
                            }
                    }
                if (ascending && run < AMOUNT_OF_VALUES)
                    {
                        boolean allDone = true;
                        for (SortingAlgorithm s : sorters)
                            {
                                if (!s.done())
                                    {
                                        allDone = false;
                                        break;
                                    }
                            }
                        if (allDone)
                            {
                                run++;
                                int[] values = randomIntArray(run);
                                for (int i = 0; i < sorters.size(); i++)
                                    {
                                        AbstractAlgorithmHelper h = sorters.get(i).helper();
                                        int operations = h.getMoves() + h.getComparisons();
                                        stats.addData(run - 1, h.getName(), operations);
                                        h.setNewArray(values);
                                        sorters.get(i).reset();
                                    }
                                stats.printStatistic(run - 1);
                            }
                    }
            }
    }

    public int[] invertedIntArray(int amountOfInts) {
        int[] array = new int[amountOfInts];
        for (int i = 0; i < array.length; i++)
            {
                array[i] = array.length - i;
            }
        return array;
    }

    public int[] sortedIntArray(int amountOfInts) {
        int[] array = new int[amountOfInts];
        for (int i = 1; i <= array.length; i++)
            {
                array[i - 1] = i;
            }
        return array;
    }

    public int[] randomIntArray(int amountOfInts) {
        int[] array = new int[amountOfInts];
        for (int i = 1; i <= array.length; i++)
            {
                int index = (int) random(array.length);
                while (array[index] != 0)
                    {
                        index = (int) random(array.length);
                    }
                array[index] = i;
            }

        return array;
    }
}
