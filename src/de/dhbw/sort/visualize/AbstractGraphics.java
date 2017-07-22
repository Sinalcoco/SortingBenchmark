package de.dhbw.sort.visualize;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jbi on 30.06.2017.
 */
public abstract class AbstractGraphics {
	public ReentrantLock lock = new ReentrantLock();
	protected int[][][] frameRing = new int[2][15][];
	protected int writeIndex, readIndex;
	protected LinkedBlockingQueue<int[]> frames = new LinkedBlockingQueue<>(15);
	protected LinkedBlockingQueue<int[]> framesFullscreen = new LinkedBlockingQueue<>(15);
	protected boolean dr;
	protected PGraphics graphics;
	protected PGraphics graphicsFullscreen;
	protected float vScaling;
	protected float hScaling;
	protected float borderWidth = 4;
	protected float borderHeight = 4;
	protected int borderColor = 0xFFcc9350;

	public AbstractGraphics(PGraphics graphics, PGraphics graphicsFullscreen) {
		this.graphicsFullscreen = graphicsFullscreen;
		this.graphics = graphics;
		this.hScaling = graphicsFullscreen.height / graphics.height;
		this.vScaling = graphicsFullscreen.width / graphics.width;
		dr = true;
		writeIndex = 0;
		readIndex = 0;
	}

	protected AbstractGraphics() {
	}

	public abstract void drawRect(float x, float y, float width, float height);

	public abstract void drawEllipse(float x, float y, float width, float height);

	public abstract void drawLine(float x1, float y1, float x2, float y2);

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

		} finally {
			lock.unlock();
		}
	}

	public void drawBackground(int rgb) {
		lock.lock();
		try {
			startDraw();
			this.graphics.background(borderColor);
			this.graphicsFullscreen.background(borderColor);
			this.graphics.fill(rgb);
			this.graphicsFullscreen.fill(rgb);
			this.graphics.rect(borderWidth, borderHeight, this.getWidth(), this.getHeight());
			this.graphicsFullscreen.rect(borderWidth * hScaling, borderHeight * vScaling, this.getWidth() * hScaling,
					this.getHeight() * vScaling);
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

		} finally {
			lock.unlock();
		}
	}
	public void fill(int theColor) {
		lock.lock();
		try {
			startDraw();
			this.graphics.fill(theColor);
			this.graphicsFullscreen.fill(theColor);

		} finally {
			lock.unlock();
		}
	}

	public void stroke(int r, int g, int b) {
		lock.lock();
		try {
			startDraw();
			this.graphics.stroke(r, g, b);
			this.graphicsFullscreen.stroke(r, g, b);

		} finally {
			lock.unlock();
		}
	}

	public void stroke(int theColor) {
		lock.lock();
		try {
			startDraw();
			this.graphics.stroke(theColor);
			this.graphicsFullscreen.stroke(theColor);

		} finally {
			lock.unlock();
		}
	}

	public void strokeWeight(int weight) {
		lock.lock();
		try {
			startDraw();
			this.graphics.strokeWeight(weight);
			this.graphicsFullscreen.strokeWeight(weight);

		} finally {
			lock.unlock();
		}
	}

	public void textAlign(int xPosition, int yPosition) {
		lock.lock();
		try {
			startDraw();
			graphics.textAlign(xPosition, yPosition);
			graphicsFullscreen.textAlign(xPosition, yPosition);

		} finally {
			lock.unlock();
		}
	}
	public void textSize(int theSize) {
		lock.lock();
		try {
			startDraw();
			graphics.textSize(theSize);
			graphicsFullscreen.textSize(theSize);

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

	@Deprecated
	public abstract void setGraphics(PGraphics graphics);

	public abstract void text(String text, float x, float y);

	public float getWidth() {
		return (this.graphics.width - 2 * borderWidth);
	}

	public float getHeight() {
		return (this.graphics.height - 2 * borderHeight);
	}

	public boolean addFrame() {
		endDraw();
		try {
			this.graphics.loadPixels();
			this.graphicsFullscreen.loadPixels();

			return write(this.graphics.pixels, this.graphicsFullscreen.pixels);
			// this.frames.put(Arrays.copyOf(this.graphics.pixels,
			// this.graphics.pixels.length));
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		// try {
		// this.framesFullscreen
		// .put(Arrays.copyOf(this.graphicsFullscreen.pixels,
		// this.graphicsFullscreen.pixels.length));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	private synchronized boolean write(int[] theArray, int[] theFullscreenArray) {
		if ((writeIndex + 1) % frameRing[0].length != readIndex) {
			writeIndex = (writeIndex + 1) % frameRing[0].length;
			
			if (frameRing[0][writeIndex] == null)
			{
				frameRing[0][writeIndex] = Arrays.copyOf(theArray, theArray.length);
			}
			else
			{
				for (int i = 0; i < frameRing[0][writeIndex].length; i++)
				{
					frameRing[0][writeIndex][i] = theArray[i];
				}
			}
//			frameRing[0][writeIndex] = theArray;
			
			if (frameRing[1][writeIndex] == null)
			{
				frameRing[1][writeIndex] = Arrays.copyOf(theFullscreenArray, theFullscreenArray.length);
			}
			else
			{
				for (int i = 0; i < frameRing[1][writeIndex].length; i++)
				{
					frameRing[1][writeIndex][i] = theFullscreenArray[i];
				}
			}
//			frameRing[1][writeIndex] = theFullscreenArray;
			return true;
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

	public int getBorderColor() {
		return this.borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
	}

	public int[] getNextFrame(boolean fullscreen) {
		return read(fullscreen);
		// if (fullscreen) {
		// if (framesFullscreen.size() > 1)
		// return this.framesFullscreen.poll();
		// else
		// return this.framesFullscreen.peek();
		// } else {
		// if (frames.size() > 1)
		// return this.frames.poll();
		// else
		// return this.frames.peek();
		// }
	}

	public synchronized int[] read(boolean fullscreen) {
		notify();
		if ((readIndex) % frameRing[0].length != writeIndex) {
			readIndex = (readIndex + 1) % frameRing[0].length;
		}
		return frameRing[fullscreen ? 1 : 0][readIndex];
	}

	@Deprecated
	public Object peek(boolean fullscreen) {
		if (fullscreen) {
			return this.framesFullscreen.peek();
		} else {
			return this.frames.peek();
		}
	}
}
