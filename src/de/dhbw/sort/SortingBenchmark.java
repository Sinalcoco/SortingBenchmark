package de.dhbw.sort;

import java.util.ArrayList;

import javax.sound.midi.Soundbank;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import de.dhbw.sort.algorithms.*;
import de.dhbw.sort.util.AbstractAlgorithmHelper;
import de.dhbw.sort.util.InPlaceAlgorithmHelper;
import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
//import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
import de.dhbw.sort.util.Statistics;
import de.dhbw.sort.visualize.AbstractGraphics;
import de.dhbw.sort.visualize.Visualizer;
import processing.core.PApplet;

public class SortingBenchmark {
	private static de.dhbw.sort.visualize.Visualizer visualizer;

	private static Statistics stats;
	private static ArrayList<SortingAlgorithm> sorters;
	private static final int AMOUNT_OF_VALUES = 10;

	private static final boolean ascending = true;
	private static int start = 10;
	private static int run = start;
	private static int end = 100;
	private static final boolean FAST = true;
	private static String[] algorithmNames = new String[9];

	public static void main(String[] args) {

		visualizer = new Visualizer(1200, 600, 1.5, 3, 3);
		PApplet.runSketch(new String[] { "" }, visualizer);
		try {
			// Es muss gewartet werden, bis PApplet die setup() Methode von
			// Visualizer aufgerufen hat.
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final int AMOUNT = visualizer.getGridNumber();

		final int STATS_ID = 4;
		Statistics stats = new Statistics(visualizer.getScreen(STATS_ID));
		algorithmNames[STATS_ID] = "Statistic";
		int counter = 0;

		int[] values;
		if (ascending) {
			values = randomIntArray(run);
		} else {
			run = AMOUNT_OF_VALUES;
			// values = randomIntArray(run);
			// values = sortedIntArray(run);
			values = invertedIntArray(run);
		}

		// stats = new Statistics();

		sorters = new ArrayList<SortingAlgorithm>();

		AbstractAlgorithmHelper bubbleHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(0), values);
		sorters.add(new BubbleSort(bubbleHelper));
		algorithmNames[0] = bubbleHelper.getAlgorithemName();

		AbstractAlgorithmHelper insertionHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(1), values);
		sorters.add(new InsertionSort(insertionHelper));
		algorithmNames[1] = insertionHelper.getAlgorithemName();

		AbstractAlgorithmHelper cocktailHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(2), values);
		sorters.add(new CocktailShaker(cocktailHelper));
		algorithmNames[2] = cocktailHelper.getAlgorithemName();

		AbstractAlgorithmHelper selektionHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(3), values);
		sorters.add(new SelectionSort(selektionHelper));
		algorithmNames[3] = selektionHelper.getAlgorithemName();

		AbstractAlgorithmHelper heapHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(5), values);
		sorters.add(new HeapSort(heapHelper));
		algorithmNames[5] = heapHelper.getAlgorithemName();

		AbstractAlgorithmHelper quickHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(6), values);
		sorters.add(new QuickSort(quickHelper));
		algorithmNames[6] = quickHelper.getAlgorithemName();

		AbstractAlgorithmHelper mergeHelper = new OutOfPlaceAlgorithmHelper(visualizer.getSplitScreen(7), values);
		sorters.add(new MergeSort((OutOfPlaceAlgorithmHelper) mergeHelper));
		algorithmNames[7] = mergeHelper.getAlgorithemName();

		AbstractAlgorithmHelper CombHelper = new InPlaceAlgorithmHelper(visualizer.getScreen(8), values);
		sorters.add(new CombSort(CombHelper));
		algorithmNames[8] = CombHelper.getAlgorithemName();

		for (SortingAlgorithm s : sorters) {
			s.start();
			// s.helper().start();
		}

		// long l;
		boolean allDone;
		int oldIndex = -1;
		long timing = System.currentTimeMillis();
		while (true) {
			allDone = true;
			// l = System.currentTimeMillis();
			if (!FAST) {
				for (SortingAlgorithm s : sorters) {
					// for (int i = 0; i < 1; i++) {

					// sorters.get(i).helper().nextFrame();
					s.helper().nextFrame();
				}
			}
			int mouseOverIndex = visualizer.getMouseOverIndex();
			if (mouseOverIndex != oldIndex && mouseOverIndex >= 0 && mouseOverIndex != STATS_ID) {
				oldIndex = mouseOverIndex;
				stats.highlight(algorithmNames[mouseOverIndex]);
			}
			if (ascending && run <= end) {
				for (SortingAlgorithm s : sorters) {
					if (FAST && !s.done() || !FAST && !s.helper().ready()) {
						// When fast forwarding it only depends on the algorithm
						// to finish
						// When slow mode is active then the helper has to be
						// reade i.e. all the actions have been converted to
						// frames
						allDone = false;
					}
				}
				if (allDone) {
					stats.addData("n-curve", run);
					stats.addData("nlogn-curve", (int) (8 * run * Math.log(run)));
					stats.addData("0.5n2-curve", (int) (0.5 * run * run));
					stats.addData("n2-curve", (int) (run * run));
					for (SortingAlgorithm s : sorters) {
						stats.addData(s.helper().getAlgorithemName(),
								s.helper().getMoves() + s.helper().getComparisons());
						s.helper().setNewArray(randomIntArray(run));
						s.reset();
					}
					run++;
					stats.highlight(algorithmNames[oldIndex]);
				} else {
					System.out.println("Too slow!");
				}
			}
			// System.out.println(System.currentTimeMillis() - l);
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
}
