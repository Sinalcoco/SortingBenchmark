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

    public AbstractGraphics(PGraphics graphicsFullscreen, PGraphics graphics) {
        this.graphicsFullscreen = graphicsFullscreen;
        this.graphics = graphics;
        this.hScaling = graphicsFullscreen.height / graphics.height;
        this.vScaling = graphicsFullscreen.width / graphics.width;
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



    public abstract void drawBackground(int r, int g, int b);

    public abstract void fill(int r, int g, int b);

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
}
