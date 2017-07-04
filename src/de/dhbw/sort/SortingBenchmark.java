//package de.dhbw.sort;
//
//import java.util.ArrayList;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import de.dhbw.sort.algorithms.*;
//import de.dhbw.sort.util.AbstractAlgorithmHelper;
//import de.dhbw.sort.util.InPlaceAlgorithmHelper;
//import de.dhbw.sort.util.StaticStatistics;
////import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
//import de.dhbw.sort.visualize.Visualizer;
//import processing.core.PApplet;
//
//public class SortingBenchmark {
//    private static de.dhbw.sort.visualize.Visualizer visualizer;
//
//    private static ArrayList<SortingAlgorithm> sorters;
//    private static ArrayList<SortingAlgorithm> helper;
//    private static final int AMOUNT_OF_VALUES = 10;
//
//    private static final boolean ascending = false;
//    private static int start = 5;
//    private static int run = start;
//    private static int end = 100;
//    private static boolean FAST = true;
//    private static String[] algorithmNames;
//    private static LinkedBlockingQueue<int[]> arrays = new LinkedBlockingQueue();
//
//    public static void main(String[] args) {
//
//        visualizer = new Visualizer(1200, 600, 1.5, 3, 3);
//        PApplet.runSketch(new String[]{""}, visualizer);
//        try {
//            // Es muss gewartet werden, bis PApplet die setup() Methode von
//            // Visualizer aufgerufen hat.
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        final int AMOUNT = visualizer.getGridNumber();
//        algorithmNames = new String[AMOUNT];
//
//        final int STATS_ID = 4;
//        StaticStatistics stats = new StaticStatistics(visualizer.getScreen(STATS_ID), start, end,
//                (float) (1.4 * end * end));
//        algorithmNames[STATS_ID] = "Statistic";
//        int counter = 0;
//
//        int[] values;
//        if (ascending) {
//            values = randomIntArray(run);
//        } else {
//            run = AMOUNT_OF_VALUES;
//            values = randomIntArray(run);
//            // values = sortedIntArray(run);
////            values = invertedIntArray(run);
//        }
//
//        // stats = new Statistics();
//
//        sorters = new ArrayList<SortingAlgorithm>();
//        helper = new ArrayList<SortingAlgorithm>();
//
//        AbstractAlgorithmHelper bubbleHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(0), values, BubbleSort.class);
//        sorters.add(new BubbleSort(bubbleHelper));
//        algorithmNames[0] = bubbleHelper.getAlgorithmName();
//
//        AbstractAlgorithmHelper insertionHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(1), values, BubbleSort.class);
//        sorters.add(new InsertionSort(insertionHelper));
//        algorithmNames[1] = insertionHelper.getAlgorithmName();
//
//        AbstractAlgorithmHelper cocktailHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(2), values, BubbleSort.class);
//        sorters.add(new CocktailShaker(cocktailHelper));
//        algorithmNames[2] = cocktailHelper.getAlgorithmName();
//
//        AbstractAlgorithmHelper selektionHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(3), values, BubbleSort.class);
//        sorters.add(new SelectionSort(selektionHelper));
//        algorithmNames[3] = selektionHelper.getAlgorithmName();
//
//        AbstractAlgorithmHelper heapHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(5), values, BubbleSort.class);
//        sorters.add(new HeapSort(heapHelper));
//        algorithmNames[5] = heapHelper.getAlgorithmName();
//
//        AbstractAlgorithmHelper quickHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(6), values, BubbleSort.class);
//        sorters.add(new QuickSort(quickHelper));
//        algorithmNames[6] = quickHelper.getAlgorithmName();
//
////        AbstractAlgorithmHelper mergeHelper = new OutOfPlaceAlgorithmHelper(visualizer.getSplitScreen(7), values);
////        sorters.add(new MergeSort((OutOfPlaceAlgorithmHelper) mergeHelper));
////        algorithmNames[7] = mergeHelper.getAlgorithmName();
//
//        AbstractAlgorithmHelper CombHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(8), values, BubbleSort.class);
//        sorters.add(new CombSort(CombHelper));
//        algorithmNames[8] = CombHelper.getAlgorithmName();
//
//        for (SortingAlgorithm s : sorters) {
//            s.start();
//            s.helper().start();
//        }
//
//        // long l;
//        boolean allDone;
//        boolean allHelperDone;
//        int oldIndex = -1;
//        long timing = System.currentTimeMillis();
//        while (true) {
//            allDone = true;
//            allHelperDone = true;
//            // l = System.currentTimeMillis();
////            if (!FAST || run > end) {
////                for (SortingAlgorithm s : sorters) {
////                    // for (int i = 0; i < 1; i++) {
////
////                    // sorters.get(i).helpers().nextFrame();
//////                    s.helpers().nextFrame();
////                }
////            }
//            if (run > end) {
//                int mouseOverIndex = visualizer.getMouseOverIndex();
//                if (mouseOverIndex != oldIndex && mouseOverIndex >= 0 && mouseOverIndex != STATS_ID) {
//                    oldIndex = mouseOverIndex;
//                    stats.highlight(algorithmNames[mouseOverIndex]);
//                } else if (mouseOverIndex == oldIndex) {
//
//                } else {
//                    //stats.addFrame();
//                }
//            }
//            if (ascending && run <= end) {
//                for (SortingAlgorithm s : sorters) {
//                    if (FAST && !s.done()) {
//                        // When fast forwarding it only depends on the algorithm
//                        // to finish
//                        // When slow mode is active then the helpers has to be
//                        // reade i.e. all the actions have been converted to
//                        // frames
//                        allDone = false;
//                    }
//                    if (!s.helper().isReady()) {
//                        // When fast forwarding it only depends on the algorithm
//                        // to finish
//                        // When slow mode is active then the helpers has to be
//                        // reade i.e. all the actions have been converted to
//                        // frames
//                        allHelperDone = false;
//                    }
//                }
//                if (!ascending && allHelperDone) {
//                    for (SortingAlgorithm s : sorters) {
//                        s.helper().destroy();
//                    }
//                }
//                if (allDone) {
//                    stats.addData("nlogn-curve", (int) (8 * run * Math.log(run)));
//                    stats.addData("0.5n2-curve", (int) (0.5 * run * run));
//                    stats.addData("n2-curve", (int) (1.4 * run * run));
//                    int[] a = randomIntArray(run);
//                    try {
//                        arrays.put(a);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    int[] newValues = a;
//                    for (SortingAlgorithm s : sorters) {
//                        stats.addData(s.helper().getAlgorithmName(),
//                                s.helper().getMoves() + s.helper().getComparisons());
//                        s.helper().resetAlgorithm(newValues);
//                        s.reset();
//                    }
//                    stats.updateScreen();
//                    run++;
//
//                }
//                if (allHelperDone) {
//
//                    for (SortingAlgorithm s : sorters) {
//                        s.helper().resetHelper(arrays.peek());
//                    }
//                    arrays.poll();
//                }
//            }
//            // System.out.println(System.currentTimeMillis() - l);
//        }
//
//    }
//
//    public static int[] invertedIntArray(int amountOfInts) {
//        int[] array = new int[amountOfInts];
//        for (int i = 0; i < array.length; i++) {
//            array[i] = array.length - i;
//        }
//        return array;
//    }
//
//    public static int[] sortedIntArray(int amountOfInts) {
//        int[] array = new int[amountOfInts];
//        for (int i = 1; i <= array.length; i++) {
//            array[i - 1] = i;
//        }
//        return array;
//    }
//
//    public static int[] randomIntArray(int amountOfInts) {
//        int[] array = new int[amountOfInts];
//        for (int i = 1; i <= array.length; i++) {
//            int index = (int) (Math.random() * array.length);
//            while (array[index] != 0) {
//                index = (int) (Math.random() * array.length);
//            }
//            array[index] = i;
//        }
//
//        return array;
//    }
//}
