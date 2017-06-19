package de.dhbw.sort.util;

import java.util.ArrayList;

import de.dhbw.sort.SortingBenchmark;
import processing.core.PApplet;
import processing.core.PGraphics;


public abstract class AbstractAlgorithmHelper {
	protected int[] values;

	protected int comparisons;
	protected int moves;

	protected ArrayList<AlgorithmCommand> commands;

	protected boolean ready;

	protected String algorithmName;

	protected int viewX, viewY, viewWidth, viewHeight;

	protected SortingBenchmark.State state;

	protected PApplet processing;
	protected PGraphics grafics;

	protected AbstractAlgorithmHelper(PApplet theParent) {
		processing = theParent;
	};
	
	public void setPGrafics(PGraphics grafics){
		this.grafics = grafics;
	};

	public AbstractAlgorithmHelper(PApplet theParent, int[] theArray, int theX, int theY, int theWidth, int theHeight) {
		initialize(theParent, theArray, theX, theY, theWidth, theHeight);
	}

	protected void initialize(PApplet theParent, int[] theArray, int theX, int theY, int theWidth, int theHeight) {
		processing = theParent;
		viewX = theX;
		viewY = theY;
		viewWidth = theWidth;
		viewHeight = theHeight;
		values = new int[theArray.length];
		PApplet.arrayCopy(theArray, values);

		comparisons = 0;
		moves = 0;
		commands = new ArrayList<AlgorithmCommand>();
		ready = true;
		state = SortingBenchmark.State.WAIT;
	}

	public void processChange() {
		drawValues();

		processCommands();

		drawInfo();
	}

	public abstract void drawValues();
	
	public abstract void processCommands();
	
	public abstract void drawInfo();
	
	public void highlight(int theIndex, ActionColor theColor)
	{
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.HIGHLIGHT, theIndex, theColor.hashCode()));
	}

	public synchronized int compare(int firstIndex, int secondIndex) {

		state = SortingBenchmark.State.WAIT;
		comparisons++;
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.COMPARE, firstIndex, secondIndex));
		try {
			while (state != SortingBenchmark.State.GO) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (values[firstIndex] - values[secondIndex]);
	}

	public synchronized void move(int fromIndex, int toIndex) {
		moves++;
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.MOVE, fromIndex, toIndex));
		state = SortingBenchmark.State.WAIT;
		try {
			while (state != SortingBenchmark.State.GO) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void swap(int firstIndex, int secondIndex) {
		state = SortingBenchmark.State.WAIT;
		// store(firstIndex);
		// store(secondIndex);
		moves += 2;
		commands.add(new AlgorithmCommand(AlgorithmCommand.Action.SWAP, firstIndex, secondIndex));
		try {
			while (state != SortingBenchmark.State.GO) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public int arrayLength() {
		return values.length;
	}

	public boolean ready() {
		return ready;
	}

	public synchronized void setState(SortingBenchmark.State theState) {
		state = theState;
		notify();
	}

	public void setName(String theName) {
		algorithmName = theName;
	}

	public abstract void setNewArray(int [] theArray);
	

	public int getComparisons() {
		return comparisons;
	}

	public int getMoves() {
		return moves;
	}

	public String getName() {
		return algorithmName;
	}
	
}