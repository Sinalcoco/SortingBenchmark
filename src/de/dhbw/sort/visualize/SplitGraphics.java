package de.dhbw.sort.visualize;

import java.security.InvalidParameterException;
import java.util.Arrays;

import processing.core.PGraphics;

public class SplitGraphics extends AbstractGraphics{

	private int [] heights;
	
	public SplitGraphics(PGraphics graphics, PGraphics graphicsFullscreen, int amountOfSplits) {
		super(graphics, graphicsFullscreen);
		heights = new int[amountOfSplits];
		for (int i = 0; i < amountOfSplits; i++)
		{
			heights[i] = (int) ((graphics.height / amountOfSplits) - borderHeight);
		}
	}

	@Override
	@Deprecated
	public void setGraphics(PGraphics graphics) {

		this.graphics = graphics;

	}
	@Deprecated
	@Override
	public void drawRect(float x, float y, float width, float height) {
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
	public void drawRect(float x, float y, float width, float height, int index) {
		lock.lock();
		try {
			x = x + borderWidth;
			y = y + borderHeight;
			
			for (int i = 0; i < index; i++)
			{
				y += heights[i];
			}
			
			startDraw();

			this.graphics.rect(x, y, width, height);
			this.graphicsFullscreen.rect(x * hScaling, y * vScaling, width * hScaling, height * vScaling);

			endDraw();
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
			endDraw();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void drawLine(float x1, float y1, float x2, float y2) {
		lock.lock();
		try {
			startDraw();
			this.graphics.line(x1, y1, x2, y2);
			this.graphicsFullscreen.line(x1 * hScaling, y1 * vScaling, x2 * hScaling, y2 * vScaling);
			endDraw();
		} finally {
			lock.unlock();
		}
	}

	// @Override
	// public void drawBackground(int r, int g, int b) {
	// lock.lock();
	// try {
	// startDraw();
	// this.graphics.background(borderColor);
	// this.graphicsFullscreen.background(borderColor);
	// this.graphics.fill(r, b, g);
	// this.graphicsFullscreen.fill(r, b, g);
	// this.graphics.rect(borderWidth, borderHeight, this.getWidth(),
	// this.getHeight());
	// this.graphicsFullscreen.rect(borderWidth * hScaling, borderHeight *
	// vScaling, this.getWidth() * hScaling,
	// this.getHeight() * vScaling);
	//
	// endDraw();
	// } finally {
	// lock.unlock();
	// }
	// }

	// public void fill(int r, int g, int b) {
	// lock.lock();
	// try {
	// startDraw();
	// this.graphics.fill(r, g, b);
	// this.graphicsFullscreen.fill(r, g, b);
	//
	// endDraw();
	// } finally {
	// lock.unlock();
	// }
	// }

	@Override
	public void text(String text, float x, float y) {
		lock.lock();
		if (text == null)
			throw new IllegalArgumentException("Der zu zeichnende Text darf nicht null sein.");
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
	public void text(String text, float x, float y, int index) {
		lock.lock();
		if (text == null)
			throw new IllegalArgumentException("Der zu zeichnende Text darf nicht null sein.");
		try {
			x = x + borderWidth;
			y = y + borderHeight;
			
			for (int i = 0; i < index; i++)
			{
				y += heights[i];
			}
			
			startDraw();
			this.graphics.text(text, x, y);
			this.graphicsFullscreen.textSize(this.graphics.textSize * vScaling);
			this.graphicsFullscreen.text(text, x * hScaling, y * vScaling);
			endDraw();
		} finally {
			lock.unlock();
		}

	}

	@Override
	public void addFrame() {
		try {
			this.graphics.loadPixels();

			this.frames.put(Arrays.copyOf(this.graphics.pixels, this.graphics.pixels.length));
		} catch (Exception e) {
			e.printStackTrace();

		}
		try {
			this.graphicsFullscreen.loadPixels();
			this.framesFullscreen
					.put(Arrays.copyOf(this.graphicsFullscreen.pixels, this.graphicsFullscreen.pixels.length));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public float getHeight(int i) {
		return heights[i];
	}

	public float getAmountOfScreens() {
		return heights.length;
	}

}
