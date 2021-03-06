package de.dhbw.sort.visualize;

import processing.core.PGraphics;

import java.util.Arrays;

/**
 * Created by jbi on 20.06.2017.
 */
public class Graphics extends AbstractGraphics {

    public Graphics(PGraphics graphics, PGraphics graphicsFullscreen) {
        super(graphics, graphicsFullscreen);

    }

    @Override
    @Deprecated
    public void setGraphics(PGraphics graphics) {

        this.graphics = graphics;

    }

    @Override
    public void drawRect(float x, float y, float width, float height) {
        lock.lock();
        try {
            x = x + borderWidth;
            y = y + borderHeight;
            startDraw();

            this.graphics.rect(x, y, width, height);
            this.graphicsFullscreen.rect(x * hScaling, y * vScaling, width * hScaling, height * vScaling);

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void drawEllipse(float x, float y, float width, float height) {
        lock.lock();
        try {
            x = x + borderWidth;
            y = y + borderHeight;
            startDraw();
            this.graphics.ellipse(x, y, width, height);
            this.graphicsFullscreen.ellipse(x * hScaling, y * vScaling, width * hScaling, height * vScaling);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        lock.lock();
        try {
            x1 += borderWidth;
            y1 += borderHeight;
            x2 += borderWidth;
            y2 += borderHeight;
            startDraw();
            this.graphics.line(x1, y1, x2, y2);
            this.graphicsFullscreen.line(x1 * hScaling, y1 * vScaling, x2 * hScaling, y2 * vScaling);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void text(String text, float x, float y) {
        lock.lock();
        try {
            x = x + borderWidth;
            y = y + borderHeight;
            startDraw();
            this.graphics.text(text, x, y);
            this.graphicsFullscreen.textSize(this.graphics.textSize * vScaling);
            this.graphicsFullscreen.text(text, x * hScaling, y * vScaling);
        } finally {
            lock.unlock();
        }

    }


}
