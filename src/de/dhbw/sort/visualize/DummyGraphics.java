package de.dhbw.sort.visualize;

import processing.core.PGraphics;

/**
 * Created by jbi on 30.06.2017.
 */
public class DummyGraphics extends AbstractGraphics {

    public DummyGraphics() {

    }

    public DummyGraphics(PGraphics graphics, PGraphics graphicsFullscreen) {
        super(graphics, graphicsFullscreen);
        try {
            this.frames.put(new int[1]);
            this.framesFullscreen.put(new int[1]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.drawBackground(0);
        this.endDraw();
        this.dr = false;
    }

    @Override
    public void setGraphics(PGraphics graphics) {

    }

    @Override
    public void drawRect(float x, float y, float width, float height) {
        throw new UnsupportedOperationException("Can't Draw on Dummy");

    }

    @Override
    public void drawEllipse(float x, float y, float width, float height) {
        throw new UnsupportedOperationException("Can't Draw on Dummy");
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        throw new UnsupportedOperationException("Can't Draw on Dummy");
    }

    @Override
    public void drawBackground(int r, int g, int b) {
        throw new UnsupportedOperationException("Can't Draw on Dummy");
    }

    @Override
    public void fill(int r, int g, int b) {
        throw new UnsupportedOperationException("Can't Draw on Dummy");
    }

    @Override
    public void text(String text, float x, float y) {
        throw new UnsupportedOperationException("Can't Draw on Dummy");
    }

    @Override
    public boolean addFrame() {
        throw new UnsupportedOperationException("Nothing can Change in Dummy");
    }

    @Override
    public int[] getNextFrame(boolean fullscreen) {

        //TODO review performance
        if (fullscreen) {
            this.dr = false;
            return graphicsFullscreen.pixels;

        } else {
            this.dr = true;
            return graphics.pixels;
        }
    }
}
