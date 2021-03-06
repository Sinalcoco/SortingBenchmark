package de.dhbw.sort.util;

import de.dhbw.sort.algorithms.SortingAlgorithm;
import de.dhbw.sort.visualize.AbstractGraphics;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public abstract class AbstractAlgorithmHelper extends Thread {
    protected int[] values;
    protected int[] graphicsValues = values;

    protected int liableHeight = 40;
    protected int big = 0;
    protected int moves;
    protected int comparisons;
    protected int swaps;
    protected int graphMoves = 0;
    protected int graphComparisons = 0;
    protected int graphSwaps = 0;

    protected ArrayList<AlgorithmCommand> commands;

    protected boolean ready = false;

    protected String algorithmName;

    protected AbstractGraphics screen;

    protected float height;
    protected float width;

    protected LinkedBlockingQueue<Moves> mov = new LinkedBlockingQueue();
    protected LinkedBlockingQueue<Integer> indexes = new LinkedBlockingQueue();

    protected SortingAlgorithm sort;
    private boolean algorithRady = false;
    private boolean running = true;
    private int temp;

    public AbstractAlgorithmHelper(AbstractGraphics screen, int[] theArray) {
        initialize(screen, theArray);
    }

    protected AbstractAlgorithmHelper(AbstractGraphics screen, int[] theArray, SortingAlgorithm sort) {
        this(screen, theArray);
        this.sort = sort;
        this.sort.setHelper(this);
    }


    public synchronized void run() {

        startSorter();
        this.drawValues();


        while (running) {
            this.nextFrame();
            if (this.isReady()) {
                try {
//                    System.out.println(this.algorithmName+": Wait");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected void initialize(AbstractGraphics screen, int[] theArray) {
        this.screen = screen;


        values = new int[theArray.length];
        graphicsValues = new int[theArray.length];
        PApplet.arrayCopy(theArray, values);
        PApplet.arrayCopy(theArray, graphicsValues);

        comparisons = 0;
        moves = 0;
        commands = new ArrayList<AlgorithmCommand>();
        for (int a : graphicsValues) {
            if (a > big) {
                big = a;
            }
        }

        width = screen.getWidth() / graphicsValues.length;
        height = (screen.getHeight() - liableHeight) / big;

    }

    public synchronized void resetGraphics(int[] b) {
        graphMoves = 0;
        graphComparisons = 0;
        graphSwaps = 0;
        int l = b.length;
        graphicsValues = new int[l];
        PApplet.arrayCopy(b, graphicsValues);

        for (int a : graphicsValues) {
            if (a > big) {
                big = a;
            }
        }

        width = screen.getWidth() / graphicsValues.length;
        height = (screen.getHeight() - liableHeight) / big;

        this.drawValues();
        this.drawInfo();
        this.notify();
        this.ready = false;

    }

    public abstract void drawValues();

    public abstract void nextFrame();

    public abstract void drawInfo();

    public int compare(int firstIndex, int secondIndex) {
        this.comparisons++;
        this.mov.add(Moves.COMPARE);

        this.indexes.add(firstIndex);
        this.indexes.add(secondIndex);

        return (this.values[firstIndex] - this.values[secondIndex]);
    }

    public void move(int fromIndex, int toIndex) {
        moves++;
        mov.add(Moves.MOVE);
        indexes.add(fromIndex);
        indexes.add(toIndex);
        values[toIndex] = values[fromIndex];
        values[fromIndex] = 0;
    }

    public void swap(int firstIndex, int secondIndex) {
        moves += 3;
        swaps++;
        mov.add(Moves.SWAP);
        indexes.add(firstIndex);
        indexes.add(secondIndex);

        int temp = values[firstIndex];
        values[firstIndex] = values[secondIndex];
        values[secondIndex] = temp;
    }

    //TODO implement for insertion sort
//    public void swapWithMoves(int firstIndex, int secondIndex)
//    {
//    	 
//    }

    public int arrayLength() {
        return values.length;
    }

    public boolean isReady() {
        return ready;
    }

    public boolean isAlgorithRady() {
        return algorithRady;
    }

    public void resetAlgorithm(int[] theArray) {
        this.algorithRady = false;


        values = new int[theArray.length];
        PApplet.arrayCopy(theArray, values);

        this.comparisons = 0;
        this.moves = 0;
        this.swaps = 0;
        this.sort.reset();


    }


    public int getComparisons() {
        return comparisons;
    }

    public int getMoves() {
        return moves;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String theName) {
        this.algorithmName = theName;
    }


    public void ready() {
        try {
            this.mov.put(Moves.READY);
            this.algorithRady = true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startSorter() {
        this.sort.start();
    }

    protected void blackout(int index) {
        screen.fill(0, 0, 0);
        screen.drawRect(index * width, liableHeight, width, screen.getHeight() - liableHeight);
    }

    protected void reDraw(int index) {
        blackout(index);
        screen.fill(255, 255, 255);
        screen.drawRect(index * width,
                (screen.getHeight() - (height * graphicsValues[index])), width,
                (height * graphicsValues[index]));

    }

    public void endSorter() {
        if (sort.getState() == State.WAITING) {
            this.sort.end();
        }
    }

    public State sortState() {
        return this.sort.getState();
    }

    public synchronized void end() {
        this.running = false;
        this.notify();
    }

    public int getAlgorithmColor() {
        return this.sort.getColor();
    }

    public void setTemp(int i) {
        this.temp = values[i];
        moves++;

    }

    public void loadTemp(int i) {
        this.set(i, this.temp);
    }

    private void set(int i, int temp) {
        moves++;
        mov.add(Moves.SET);
        indexes.add(i);
        indexes.add(temp);
        values[i] = temp;
    }


}