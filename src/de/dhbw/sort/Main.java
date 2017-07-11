package de.dhbw.sort;

import de.dhbw.sort.algorithms.*;
import de.dhbw.sort.util.AbstractAlgorithmHelper;
import de.dhbw.sort.util.InPlaceAlgorithmHelper;
import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
import de.dhbw.sort.visualize.Visualizer;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by jbi on 04.07.2017.
 */
public class Main {
    private static int width = 1200;
    private static int heigth = 600;
    private static int rows = 3;
    private static int colums = 3;
    private static boolean threaded = true;
    private static int[] values;

    private static int amountOfValues = 20;

    private static ArrayList<AbstractAlgorithmHelper> helpers = new ArrayList<>();

    private static Visualizer visualizer;


    public static void main(String[] args) {
        visualizer = new Visualizer(width, heigth, 1.5, rows, colums);
        PApplet.runSketch(new String[]{""}, visualizer);
        //TODO wait notify cunstrukt to remove Thread.sleep() in Visualizer setup()
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //TODO Visualicer menue

        values = randomIntArray(amountOfValues);

        initStatistiks();
        initHelper();

        boolean allDone = true;

        if (threaded) {
            for (AbstractAlgorithmHelper helper : helpers) {
                helper.start();
            }

            if(allDone){
                for (AbstractAlgorithmHelper h:helpers) {

                }
            }

        } else {
            startSorters();
            startHelpers();
            while (true) {
                for (AbstractAlgorithmHelper helper : helpers) {
                    //TODO new controle methed in helper
                    helper.nextFrame();
                }
            }
        }


    }

    private static void startHelpers() {
        for (AbstractAlgorithmHelper helper : helpers) {
            //TODO new controle methed in helper
            helper.drawValues();
        }
    }

    private static void startSorters() {
        for (AbstractAlgorithmHelper h : helpers) {
            h.startSorter();

        }
    }

    private static void initStatistiks() {
    }

    private static void initHelper() {

        helpers.add(new InPlaceAlgorithmHelper(visualizer.getScreen(helpers.size()), values, new BubbleSort()));
        helpers.add(new InPlaceAlgorithmHelper(visualizer.getScreen(helpers.size()), values, new InsertionSort()));
        helpers.add(new InPlaceAlgorithmHelper(visualizer.getScreen(helpers.size()), values, new CocktailShaker()));
        helpers.add(new InPlaceAlgorithmHelper(visualizer.getScreen(helpers.size()), values, new SelectionSort()));
        helpers.add(new InPlaceAlgorithmHelper(visualizer.getScreen(helpers.size()), values, new CombSort()));
        helpers.add(new InPlaceAlgorithmHelper(visualizer.getScreen(helpers.size()), values, new HeapSort()));
        helpers.add(new OutOfPlaceAlgorithmHelper(visualizer.getSplitScreen(helpers.size()),values,new MergeSort()));


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
}
