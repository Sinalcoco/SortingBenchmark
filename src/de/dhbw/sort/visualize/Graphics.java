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
}
