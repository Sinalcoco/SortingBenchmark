package de.dhbw.sort;

import de.dhbw.sort.algorithms.*;
import de.dhbw.sort.util.AbstractAlgorithmHelper;
import de.dhbw.sort.util.InPlaceAlgorithmHelper;
import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
import de.dhbw.sort.util.StaticStatistics;
import de.dhbw.sort.visualize.NoScreenAvailableError;
import de.dhbw.sort.visualize.Visualizer;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jbi on 04.07.2017.
 */
public class Main {
    private static int width = 1200;
    private static int heigth = 600;
    private static int rows = 3;
    private static int colums = 3;

    private static boolean assending = true;
    private static boolean threaded = true;

    private static int[] values;

    private static int amountOfValues = 3;

    private static ArrayList<AbstractAlgorithmHelper> helpers = new ArrayList<>();
    private static Visualizer visualizer;
    private static int stepsice = 1;
    private static int maxvalues = 50;

    public static void main(String[] args) {
        visualizer = new Visualizer(width, heigth, 1, rows, colums);
        PApplet.runSketch(new String[]{""}, visualizer);
        // TODO wait notify cunstrukt to remove Thread.sleep() in Visualizer
        // setup()
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Visualicer menue

        values = randomIntArray(amountOfValues);

        initStatistiks();
        initHelper();

        boolean allDone;
        boolean allGraphicsDone;

        if (threaded) {
            for (AbstractAlgorithmHelper helper : helpers) {
                helper.start();
            }
            while (assending) {
                allDone = true;
                allGraphicsDone = true;
                for (AbstractAlgorithmHelper h : helpers) {
                    if (!h.isAlgorithRady()) {
                        allDone = false;
                    }
                    if (!h.isReady()) {
                        allGraphicsDone = false;
                    }
                }
                if (allDone) {
                    if (!(amountOfValues == maxvalues)) {
                        amountOfValues += stepsice;
                        int[] ints = randomIntArray(amountOfValues);
                        writeArrayToFile(ints);
                        for (AbstractAlgorithmHelper h : helpers) {
                            h.resetAlgorithm(ints);
                        }
                    }

                }
                if (allGraphicsDone) {
                    if (readArrayFromFile() != null) {
                        for (AbstractAlgorithmHelper h : helpers) {
                            h.resetGraphics(readArrayFromFile());
                        }
                    }
                    renmoveArrayFromfile();
                }
            }


        } else {
            startSorters();
            startHelpers();
            while (true) {
                for (AbstractAlgorithmHelper helper : helpers) {
                    // TODO new controle methed in helper
                    helper.nextFrame();
                }
            }
        }

    }

    private static void startHelpers() {
        for (AbstractAlgorithmHelper helper : helpers) {
            // TODO new controle methed in helper
            helper.drawValues();
        }
    }

    private static void startSorters() {
        for (AbstractAlgorithmHelper h : helpers) {
            h.startSorter();

        }
    }

    private static void initStatistiks() {
        new StaticStatistics(visualizer.getScreen(4), amountOfValues, amountOfValues,
                (float) (1.4 * amountOfValues * amountOfValues));
    }

    private static void initHelper() {

        try {
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new BubbleSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new InsertionSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new CocktailShaker()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new SelectionSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new CombSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new HeapSort()));
//            helpers.add(
//                    new OutOfPlaceAlgorithmHelper(visualizer.getNextAvailableSplitScreen(2), values, new MergeSort()));
        } catch (NoScreenAvailableError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int[] invertedIntArray(int amountOfInts) {
        int[] array = new int[amountOfInts];
        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i;
        }
        return array;
    }

    public static int[] sortedIntArray(int amountOfInts) {
        int[] array = new int[amountOfInts];
        for (int i = 1; i <= array.length; i++) {
            array[i - 1] = i;
        }
        return array;
    }

    public static int[] randomIntArray(int amountOfInts) {
        int[] array = new int[amountOfInts];
        for (int i = 1; i <= array.length; i++) {
            int index = (int) (Math.random() * array.length);
            while (array[index] != 0) {
                index = (int) (Math.random() * array.length);
            }
            array[index] = i;
        }

        return array;
    }

    private static LinkedBlockingQueue<int[]> file = new LinkedBlockingQueue();

    private static void renmoveArrayFromfile() {
        file.poll();
    }

    private static int[] readArrayFromFile() {
        return file.peek();
    }

    private static void writeArrayToFile(int[] array) {
        try {
            file.put(array);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
