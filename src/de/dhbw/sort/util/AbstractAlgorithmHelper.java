package de.dhbw.sort.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

import de.dhbw.sort.algorithms.SortingAlgorithm;
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

    protected boolean ready =false;

    protected String algorithmName;

    protected int viewX, viewY, viewWidth, viewHeight;


    protected AbstractGraphics screen;

    protected float height;
    protected float width;

    protected LinkedBlockingQueue<Moves> mov = new LinkedBlockingQueue();
    protected LinkedBlockingQueue<Integer> indexes = new LinkedBlockingQueue();
    protected int liableHeight = 40;


    protected SortingAlgorithm sort;

    protected AbstractAlgorithmHelper() {
    }
    protected AbstractAlgorithmHelper(AbstractGraphics screen) {
        this.screen = screen;
    }
    public AbstractAlgorithmHelper(AbstractGraphics screen, int[] theArray) {
    	initialize(screen, theArray);
    }

    protected AbstractAlgorithmHelper(AbstractGraphics screen, int[] theArray, SortingAlgorithm sort) {
        this(screen,theArray);
        this.sort = sort;
        this.sort.setHelper(this);
    }



    public synchronized void run(){

        startSorter();
        this.drawValues();



        long l ;
        while (true){
//            l = System.currentTimeMillis();
            this.nextFrame();
            if (this.isReady()){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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

    public  int compare(int firstIndex, int secondIndex) {
        this.comparisons++;
        this.mov.add(Moves.COMPARE);
        this.indexes.add(firstIndex);
        this.indexes.add(secondIndex);

        return (this.values[firstIndex] - this.values[secondIndex]);
    }

    public  void move(int fromIndex, int toIndex) {
        moves++;
        mov.add(Moves.MOVE);
        indexes.add(fromIndex);
        indexes.add(toIndex);

        //TODO do move stuff in array


    }

    public  void swap(int firstIndex, int secondIndex) {
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

    public boolean isReady() {
        return ready;
    }


    public void setAlgorithmName(String theName) {
        algorithmName = theName;
//        drawInfo();
    }

    public void resetAlgorithm(int[] theArray){


        values = new int[theArray.length];
        PApplet.arrayCopy(theArray, values);

        comparisons = 0;
        moves = 0;

        
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


    public void resetHelper(int[] peek){

        this.graphicsValues = Arrays.copyOf(peek, peek.length);

        int big = 0;
        for (int a : graphicsValues) {
            if (a > big) {
                big = a;
            }
        }

        width = screen.getWidth() / graphicsValues.length;
        height = (screen.getHeight() - liableHeight) / big;
        this.drawValues();
        this.ready = false;
    }

    public  void ready(){
        try {
            this.mov.put(Moves.READY);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startSorter(){
        this.sort.start();
    }
}