package de.dhbw.sort.util;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import de.dhbw.sort.visualize.AbstractGraphics;
import de.dhbw.sort.visualize.Graphics;
import processing.core.PApplet;


public abstract class AbstractAlgorithmHelper extends Thread{
    protected int[] values;
    protected int[] graphicsValues = values;

    protected int comparisons;
    protected int swaps;
    protected int moves;

    protected ArrayList<AlgorithmCommand> commands;

    protected boolean ready;

    protected String algorithmName;

    protected int viewX, viewY, viewWidth, viewHeight;


    protected AbstractGraphics screen;

    protected float height;
    protected float width;

    protected LinkedBlockingQueue<Moves> mov = new LinkedBlockingQueue();
    protected LinkedBlockingQueue<Integer> indexes = new LinkedBlockingQueue();
    private int liableHeight = 40;


    protected AbstractAlgorithmHelper() {
    }
    protected AbstractAlgorithmHelper(AbstractGraphics screen) {
        this.screen = screen;
    }
    public AbstractAlgorithmHelper(AbstractGraphics screen, int[] theArray) {
    	initialize(screen, theArray);
    }

    public void run(){
        long l ;
        while (true){
//            l = System.currentTimeMillis();
            this.nextFrame();
//            System.out.println(System.currentTimeMillis()-l);
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
        ready = true;
        int big = 0;
        for (int a : values)
            {
                if (a > big)
                    {
                        big = a;
                    }
            }

        width = screen.getWidth() / values.length;
        height = (screen.getHeight()- liableHeight) / big;

    }



    public abstract void drawValues();

    public abstract void nextFrame();

    public abstract void drawInfo();

    public void highlight(int theIndex, ActionColor theColor) {
        commands.add(new AlgorithmCommand(AlgorithmCommand.Action.HIGHLIGHT, theIndex, theColor.hashCode()));
    }

    public synchronized int compare(int firstIndex, int secondIndex) {
        comparisons++;
        mov.add(Moves.COMPARE);
        indexes.add(firstIndex);
        indexes.add(secondIndex);

        return (values[firstIndex] - values[secondIndex]);
    }

    public synchronized void move(int fromIndex, int toIndex) {
        moves++;
        mov.add(Moves.MOVE);
        indexes.add(fromIndex);
        indexes.add(toIndex);

        //TODO do move stuff in array


    }

    public synchronized void swap(int firstIndex, int secondIndex) {
        moves += 3;
        swaps++;
        mov.add(Moves.SWAP);
        indexes.add(firstIndex);
        indexes.add(secondIndex);

        int temp = values[firstIndex];
        values[firstIndex] = values[secondIndex];
        values[secondIndex] = temp;

    }


    public int arrayLength() {
        return values.length;
    }

    public boolean ready() {
        return mov.size() == 0;
    }


    public void setAlgorithmName(String theName) {
        algorithmName = theName;
        drawInfo();
    }

    public abstract void setNewArray(int[] theArray);

    public int getComparisons() {
        return comparisons;
    }

    public int getMoves() {
        return moves;
    }

    public String getAlgorithemName() {
        return algorithmName;
    }


}