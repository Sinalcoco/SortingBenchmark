package de.dhbw.sort.visualize;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by jbi on 13.06.2017.
 */
public class Visualizer extends PApplet {
    int fullScreen = -1;

    private int width = 1200;
    private int height = 600;
    private int rowCount = 3;
    private int columnCount = 3;
    private int fWidth;
    private int fHeight;
    private boolean[] screenValid;
    private AbstractGraphics[] screens;
    private PImage display;
    private PImage displayFullscreen;
    private int mouseOverIndex = -1;

    public Visualizer(int width, int height, double zoom, int rowCount, int columnCount) {
        this.width = (int) (width * zoom);
        this.height = (int) (height * zoom);
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        this.fWidth = this.width / rowCount;
        this.fHeight = this.height / columnCount;


        screens = new AbstractGraphics[rowCount * columnCount];
        screenValid = new boolean[screens.length];


    }

    public Visualizer(double zoom, int rowCount, int columnCount) {
        new Visualizer(width, height, zoom, rowCount, columnCount);
    }

    public Visualizer(int width, int heigth, int rows, int colums) {
        new Visualizer(width, heigth, 1, rows, colums);
    }

    public void initScreens() {


        for (int i = 0; i < screens.length; i++) {
            screens[i] = new DummyGraphics(this.createGraphics(fWidth, fHeight), this.createGraphics(width, height));
        }
    }

    private void initScreen(int theIndex) {


        screens[theIndex] = new Graphics(this.createGraphics(fWidth, fHeight), this.createGraphics(width, height));
    }

    public void setup() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        //Momentan w�rde ich das vergr��ern des Hauptfensters verbieten
        //surface.setResizable(true);
        frameRate(1);
        initScreens();

        display = createImage(fWidth, fHeight, PApplet.RGB);
        displayFullscreen = createImage(width, height, PApplet.RGB);
//        try {
//
//            Thread.sleep(10000);
//            System.out.println(screens[1].frames.size());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void settings() {
        size(width, height);
    }

    public void mousePressed() {
        // Ordne dem Mausclick ein Fenster zu und setzte es auf fullscreen.
        if (fullScreen == -1) {
            fullScreen = mouseX / (fWidth);
            System.out.println("X index is: " + fullScreen);
            fullScreen += (mouseY / (fHeight)) * rowCount;
            System.out.println("Y index is: " + (mouseY / fWidth));
            System.out.println("Array index is: " + fullScreen);

            screenValid[fullScreen] = false;

        } else {
            // War schon eins Vollbild setze die ansicht wieder zurueck
            background(0);
            screenValid[fullScreen] = false;
            fullScreen = -1;
        }
    }

    public void draw() {
        interpretMouseOver();
        // Wenn keines im fullScreen Modus ist zeichne einfach alle normal
        if (fullScreen == -1) {
            for (int i = 0; i < screens.length; i++)

            {

                try {
                    if (!screens[i].dr || screens[i].peek(false) != null) {
                        display.loadPixels();
                        display.pixels = screens[i].getNextFrame(false);
                        display.updatePixels();
                        image(display, (i % rowCount) * (fWidth),
                                (i / rowCount) * (fHeight));
                        screens[i].getNextFrame(true);
                    }

                } catch (Exception e) {

                }
            }
        } else {
            try {
                if (screens[fullScreen].peek(true) != null) {

                    displayFullscreen.loadPixels();
                    displayFullscreen.pixels = screens[fullScreen].getNextFrame(true);
                    displayFullscreen.updatePixels();
                    image(displayFullscreen, 0, 0);

                }

                image(displayFullscreen, 0, 0);
                for (int i = 0; i < screens.length; i++) {
                    screens[i].getNextFrame(false);
                    screens[i].getNextFrame(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void interpretMouseOver() {
        // Ordne dem Mausclick ein Fenster zu und setzte es auf fullscreen.
        if (fullScreen == -1) {
            int index = mouseX / (fWidth);
            index += (mouseY / (fHeight)) * rowCount;

            mouseOverIndex = index;

        } else {
            mouseOverIndex = fullScreen;
        }
    }

    public int getMouseOverIndex() {
        return this.mouseOverIndex;
    }

    public int getGridNumber() {
        return screens.length;
    }


    public Graphics getScreen(int index) {
        initScreen(index);
        return (Graphics) screens[index];
    }

    public Graphics getNextAvailableScreen() throws NoScreenAvailableError {
        for (int i = 0; i < screens.length; i++) {
            AbstractGraphics g = screens[i];
            if (g instanceof DummyGraphics) {
                initScreen(i);
                return (Graphics) screens[i];

            }
        }
        throw new NoScreenAvailableError("No more Screens");
    }


    public SplitGraphics getNextAvailableSplitScreen(int theAmountOfScreens) throws NoScreenAvailableError {


        for (int i = 0; i < screens.length; i++) {
            AbstractGraphics g = screens[i];
            if (g instanceof DummyGraphics) {
                screens[i] = new SplitGraphics(this.createGraphics(fWidth, fHeight), this.createGraphics(width, height), theAmountOfScreens);
                return (SplitGraphics) screens[i];
            }
        }

        throw new NoScreenAvailableError("No more Screens");
    }


    public SplitGraphics getSplitScreen(int theIndex, int theAmountOfScreens) {
        screens[theIndex] = new SplitGraphics(this.createGraphics(fWidth, fHeight), this.createGraphics(width, height), theAmountOfScreens);
        return (SplitGraphics) screens[theIndex];
    }

    public void keyPressed() {
        switch (this.key) {
            case '+':
                if (this.frameRate + 1.0 <= 100) {
                    this.frameRate(frameRate += 1.0);
                    System.out.println(frameRate);
                }
                break;
            case '-':
                if (this.frameRate - 1.0 >= 0) {
                    this.frameRate(frameRate -= 1.0);
                    System.out.println(frameRate);
                }
                break;
            default:
                break;

        }
    }

}
