package de.dhbw.sort.visualize;

import de.dhbw.sort.util.AbstractAlgorithmHelper;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jbi on 30.06.2017.
 */
public abstract class AbstractGraphics {
    public ReentrantLock lock = new ReentrantLock();
    public LinkedBlockingQueue<int[]> frames = new LinkedBlockingQueue<>(100);
    public LinkedBlockingQueue<int[]> framesFullscreen = new LinkedBlockingQueue<>(100);

    protected PGraphics graphics;
    protected PGraphics graphicsFullscreen;

    protected float vScaling;
    protected float hScaling;
    protected float borderWidth = 10;
    protected float borderHeight = 10;

    protected int borderColor = 0xFFcc9350;

    public AbstractGraphics(PGraphics graphics, PGraphics graphicsFullscreen) {
        this.graphicsFullscreen = graphicsFullscreen;
        this.graphics = graphics;
        this.hScaling = graphicsFullscreen.height / graphics.height;
        this.vScaling = graphicsFullscreen.width / graphics.width;
    }

    protected AbstractGraphics() {
    }

    @Deprecated
    public abstract void setGraphics(PGraphics graphics);

    public abstract void drawRect(float x, float y, float width, float height);

    public abstract void drawEllipse(float x, float y, float width, float height);

    public void startDraw() {
        this.graphics.beginDraw();
        this.graphicsFullscreen.beginDraw();

    }


    public void endDraw() {
        this.graphics.endDraw();
        this.graphicsFullscreen.endDraw();

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

    public abstract void text(String text, float x, float y);

    public float getWidth() {
        return (this.graphics.width - 2 * borderWidth);
    }

    public float getHeight() {
        return (this.graphics.height - 2 * borderHeight);
    }


    public abstract void addFrame();

    public int getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int[] getNextFrame(boolean fullscreen) {
        if (fullscreen) {
            return this.framesFullscreen.poll();
        } else {
            return this.frames.poll();
        }

    }

    public Object peek(boolean fullscreen){
        if (fullscreen) {
            return this.framesFullscreen.peek();
        } else {
            return this.frames.peek();
        }
    }
}