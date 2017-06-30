package de.dhbw.sort;

import java.util.ArrayList;

import de.dhbw.sort.algorithms.BubbleSort;
import de.dhbw.sort.algorithms.CocktailShaker;
import de.dhbw.sort.algorithms.HeapSort;
import de.dhbw.sort.algorithms.InsertionSort;
import de.dhbw.sort.algorithms.MergeSort;
import de.dhbw.sort.algorithms.QuickSort;
import de.dhbw.sort.algorithms.SelectionSort;
import de.dhbw.sort.algorithms.SortingAlgorithm;
import de.dhbw.sort.util.AbstractAlgorithmHelper;
import de.dhbw.sort.util.InPlaceAlgorithmHelper;
import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
//import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
import de.dhbw.sort.util.Statistics;
import de.dhbw.sort.visualize.Visualizer;
import processing.core.PApplet;

public class SortingBenchmark {
    private static de.dhbw.sort.visualize.Visualizer visualizer;

    private static Statistics stats;
    private static ArrayList<SortingAlgorithm> sorters;
    private static final int AMOUNT_OF_VALUES = 10;
    private static final boolean ascending = false;
    private static final boolean frameByFrame = false;
    private static boolean advance = true;
    private static int run = 20;

    public static void main(String[] args) {

        visualizer = new Visualizer(1200, 600, 3, 3);
        PApplet.runSketch(new String[]{""}, visualizer);
        try {
            // Es muss gewartet werden, bis PApplet die setup() Methode von
            // Visualizer aufgerufen hat.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final int AMOUNT = visualizer.getGridNumber();

//        final int STATS_ID = 4;
//        Statistics stats = new Statistics(visualizer.getScreen(STATS_ID));
        int counter = 0;


        int[] values;
        if (ascending) {
            values = randomIntArray(run);
        } else {
            run = AMOUNT_OF_VALUES;
            values = randomIntArray(run);
            // values = sortedIntArray(run);
            // values = invertedIntArray(run);
        }

        // stats = new Statistics();

        sorters = new ArrayList<SortingAlgorithm>();
        AbstractAlgorithmHelper bubbleHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(0), values);
        sorters.add(new BubbleSort(bubbleHelper));

        AbstractAlgorithmHelper insertionHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(1), values);
        sorters.add(new InsertionSort(insertionHelper));


        AbstractAlgorithmHelper cocktailHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(2), values);
        sorters.add(new CocktailShaker(cocktailHelper));


        AbstractAlgorithmHelper selektionHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(3), values);
        sorters.add(new SelectionSort(selektionHelper));

        AbstractAlgorithmHelper heapHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(5), values);
        sorters.add(new HeapSort(heapHelper));

        AbstractAlgorithmHelper quickHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(6), values);
        sorters.add(new QuickSort(quickHelper));

        AbstractAlgorithmHelper mergeHelper = new OutOfPlaceAlgorithmHelper(visualizer.getScreen(7), values);
        sorters.add(new MergeSort((OutOfPlaceAlgorithmHelper) mergeHelper));

        for (SortingAlgorithm s : sorters) {
            s.start();
                s.helper().start();
        }

//        long l;
//        while (true) {
//            l = System.currentTimeMillis();
//            for (SortingAlgorithm s : sorters){
////            for (int i = 0; i < 1; i++) {
//
//
////            sorters.get(i).helper().nextFrame();
//            s.helper().nextFrame();
//
//
//            }
//            System.out.println(System.currentTimeMillis() - l);
//        }

}


public enum State {
    WAIT, GO
}

    public void setup() {


    }

    public void keyPressed() {
        advance = true;
    }


    public int[] invertedIntArray(int amountOfInts) {
        int[] array = new int[amountOfInts];
        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i;
        }
        return array;
    }

    public int[] sortedIntArray(int amountOfInts) {
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
}
