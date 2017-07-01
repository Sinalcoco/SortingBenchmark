package de.dhbw.sort.visualize;

import processing.core.PGraphics;

import java.util.Arrays;

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
		super.drawBackground(0xff508acc);

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
    public void drawLine(float x1, float y1, float x2, float y2)
    {
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
	public void addFrame() {
		throw new UnsupportedOperationException("Nothing can Change in Dummy");
	}

	@Override
	public int[] getNextFrame(boolean fullscreen) {
		if (fullscreen) {
			graphicsFullscreen.loadPixels();
			return Arrays.copyOf(graphicsFullscreen.pixels, graphicsFullscreen.pixels.length);
		} else {
			graphics.loadPixels();
			return Arrays.copyOf(graphics.pixels, graphics.pixels.length);
		}
	}
}
