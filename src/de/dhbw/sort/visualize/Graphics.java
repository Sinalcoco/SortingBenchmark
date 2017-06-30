package de.dhbw.sort.visualize;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import de.dhbw.sort.util.AbstractAlgorithmHelper;
import de.dhbw.sort.util.InPlaceAlgorithmHelper;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Created by jbi on 20.06.2017.
 */
public class Graphics {

    private PGraphics graphics;
    private PGraphics graphicsFullscreen;
    private float vScaling;
    private float hScaling;
    private boolean finished;
    private int borderWidth = 10;
    private int borderHeight = 10;
    private int borderColor = 0xFF00FFFF;
    public ReentrantLock lock = new ReentrantLock();

    public LinkedBlockingQueue<int[]> frames = new LinkedBlockingQueue<>(100);
    private AbstractAlgorithmHelper helper;
    public LinkedBlockingQueue<int[]> framesFullscreen = new LinkedBlockingQueue<>(100);

    public Graphics(PGraphics graphics, PGraphics graphicsFullscreen) {
        this.graphics = graphics;
        this.graphicsFullscreen = graphicsFullscreen;

        hScaling = graphicsFullscreen.height / graphics.height;
        vScaling = graphicsFullscreen.width / graphics.width;


    }

    @Deprecated
    public void setGraphics(PGraphics graphics) {

        this.graphics = graphics;

    }

    public void generateNextFrame() {
        if (helper != null) {
            this.helper.nextFrame();
        }
    }

    public void drawRect(int x, int y, int width, int height) {
        lock.lock();
        try {
            x = x + borderWidth;
            y = y + borderHeight;
            startDraw();

            this.graphics.rect(x, y, width, height);
            this.graphicsFullscreen.rect(x * hScaling, y * vScaling, width * hScaling, height * vScaling);

            endDraw();
        } finally {
            lock.unlock();
        }
    }

    private void endDraw() {

        this.graphics.endDraw();
        this.graphicsFullscreen.endDraw();

    }

    private void startDraw() {

        this.graphics.beginDraw();
        this.graphicsFullscreen.beginDraw();

    }

    public void drawEllipse(int x, int y, int width, int height) {
        lock.lock();
        try {
            x = x + borderWidth;
            y = y + borderHeight;
            startDraw();
            this.graphics.ellipse(x, y, width, height);
            this.graphicsFullscreen.ellipse(x * hScaling, y * vScaling, width * hScaling, height * vScaling);
            endDraw();
        } finally {
            lock.unlock();
        }

    }

    public void drawBackground(int r, int g, int b) {
        lock.lock();
        try {
            startDraw();
            this.graphics.background(borderColor);
            this.graphicsFullscreen.background(borderColor);
            this.graphics.fill(r, b, g);
            this.graphicsFullscreen.fill(r, b, g);
            this.graphics.rect(borderWidth, borderHeight, this.getWidth(), this.getHeight());
            this.graphicsFullscreen.rect(borderWidth * hScaling, borderHeight * vScaling, this.getWidth() * hScaling,
                    this.getHeight() * vScaling);

            endDraw();
        } finally {
            lock.unlock();
        }
    }

    public void fill(int r, int g, int b) {
        lock.lock();
        try {
            startDraw();
            this.graphics.fill(r, g, b);
            this.graphicsFullscreen.fill(r, g, b);

            endDraw();
        } finally {
            lock.unlock();
        }

    }

    public PImage getGraphics(boolean fullscreen) {
        lock.lock();
        try {
            if (fullscreen) {
                return this.graphicsFullscreen.get();
            } else {
                return this.graphics.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public PImage getGraphics() {

        return this.getGraphics(false);
    }

    public void text(String text, int x, int y) {
        lock.lock();
        try {
            x = x + borderWidth;
            y = y + borderHeight;
            startDraw();
            this.graphics.text(text, x, y);
            this.graphicsFullscreen.textSize(this.graphics.textSize * vScaling);
            this.graphicsFullscreen.text(text, x * hScaling, y * vScaling);
            endDraw();
        } finally {
            lock.unlock();
        }

    }

    public int getWidth() {
        return this.graphics.width - 2 * borderWidth;
    }

    public int getHeight() {
        return this.graphics.height - 2 * borderHeight;
    }

    public void setHelper(AbstractAlgorithmHelper abstractAlgorithmHelper) {
        this.helper = abstractAlgorithmHelper;
    }

    public void addFrame() {

        try {
            this.graphics.loadPixels();

            this.frames.put(Arrays.copyOf(this.graphics.pixels,this.graphics.pixels.length));
        } catch (Exception e) {
            e.printStackTrace();

        }
        try {
            this.graphicsFullscreen.loadPixels();
            this.framesFullscreen.put(Arrays.copyOf(this.graphicsFullscreen.pixels,this.graphicsFullscreen.pixels.length));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
