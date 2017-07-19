package de.dhbw.sort;

import de.dhbw.sort.algorithms.*;
import de.dhbw.sort.util.AbstractAlgorithmHelper;
import de.dhbw.sort.util.InPlaceAlgorithmHelper;
import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
import de.dhbw.sort.util.StaticStatistics;
import de.dhbw.sort.visualize.NoScreenAvailableError;
import de.dhbw.sort.visualize.Visualizer;
import processing.core.PApplet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;

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
    private static boolean visualice = true;

    private static int[] values;

    private static int amountOfValues = 3;
    private static int amountOfGraphicsValues = 3;

    private static ArrayList<AbstractAlgorithmHelper> helpers = new ArrayList<>();
    private static Visualizer visualizer;
    private static int stepsice = 1;
    private static int maxvalues = 10;
    private static final Path PATH = Paths.get("C:\\Users\\Public\\temp");

    private static StaticStatistics stats;
    private static boolean running = true;


    public static void main(String[] args) {
        if (Files.exists(PATH)) {
            try {
                Files.delete(PATH);
                Files.createFile(PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                Files.createFile(PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
            while (running) {
                if (assending) {
                    accendLogic();
                    if(amountOfValues == maxvalues){
                        for (AbstractAlgorithmHelper h:helpers) {
                            if(h.sortState() == Thread.State.WAITING)
                                h.endSorter();
                        }
                    }

                    if(amountOfGraphicsValues == maxvalues){
                        for (AbstractAlgorithmHelper h:helpers) {
                            if(h.getState() == Thread.State.WAITING)
                                h.end();
                        }
                    }

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
                if (assending) {
                    accendLogic();
                }
            }
        }

    }

    private static void accendLogic() {
        boolean allDone;
        boolean allGraphicsDone;
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
                    stats.addData(h.getAlgorithmName(), h.getComparisons() + h.getMoves());
                    h.resetAlgorithm(ints);
                }
                stats.updateScreen();
            }

        }
        if (allGraphicsDone) {
            if (visualice && readArrayFromFile() != null) {
                amountOfGraphicsValues += stepsice;
                for (AbstractAlgorithmHelper h : helpers) {
                    h.resetGraphics(readArrayFromFile());
                }
            }
            renmoveArrayFromfile();
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
        stats = new StaticStatistics(visualizer.getScreen(4), amountOfValues, maxvalues,
                (float) (1.4 * maxvalues * maxvalues));
    }

    private static void initHelper() {

        try {
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new BubbleSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new InsertionSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new QuickSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new CocktailShaker()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new SelectionSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new CombSort()));
            helpers.add(new InPlaceAlgorithmHelper(visualizer.getNextAvailableScreen(), values, new HeapSort()));
            helpers.add(
                    new OutOfPlaceAlgorithmHelper(visualizer.getNextAvailableSplitScreen(2), values, new MergeSort()));
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


    private static void renmoveArrayFromfile() {
        try {
            BufferedReader r = Files.newBufferedReader(PATH);

            String s;
            r.readLine();
            String ges = "";
            while ((s = r.readLine()) != null) {
                String[] b = s.split(",");
                for (String d : b) {
                    ges += d + ",";
                }

                ges += "\n";

            }

            BufferedWriter writer = Files.newBufferedWriter(PATH, UTF_8);
            writer.append(ges + "\n");
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] readArrayFromFile() {
        try (BufferedReader r = Files.newBufferedReader(PATH)) {
            String[] s;

            s = r.readLine().split(",");
            if (s.length > 2) {
                int[] a = new int[s.length];

                for (int i = 0; i < a.length; ++i) {
                    if (!s[i].equals("")) {
                        a[i] = Integer.valueOf(s[i]);
                    }
                }
                return a;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeArrayToFile(int[] array) {
        String s = "" + array[0];
        for (int i = 1; i < array.length; ++i) {
            s = s + "," + array[i];
        }

        try (BufferedWriter writer = Files.newBufferedWriter(PATH, UTF_8, APPEND)) {
            writer.append(s + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
