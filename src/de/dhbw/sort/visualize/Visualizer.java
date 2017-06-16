package de.dhbw.sort.visualize;

import de.dhbw.sort.util.AbstractAlgorithmHelper;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by jbi on 13.06.2017.
 */
public class Visualizer extends PApplet {
	int fullScreen;

	private int width = 200;
	private int height = 600;
	private int rowCount = 3;
	private int columnCount = 3;
	private int fWidth;
	private int fHeight;

	private PGraphics[] gs;

	public Visualizer(int width, int height, int rowCount, int columnCount) {
		this.width = width;
		this.height = height;
		this.rowCount = rowCount;
		this.columnCount = columnCount;

		this.fWidth = width / rowCount;
		this.fHeight = height / columnCount;

		gs = new PGraphics[rowCount * columnCount];
		fullScreen = -1;

	}

	public PGraphics getScreen(int index) {
		return gs[index];
	}

	public void addAlgorithemHelper(AbstractAlgorithmHelper helper) {
	}

	public void initScreens() {

		for (int i = 0; i < gs.length; i++) {
			gs[i] = createGraphics(fWidth, fHeight);
		}
	}

	public void setup() {
		//Momentan würde ich das vergrößern des Hauptfensters verbieten
		//surface.setResizable(true);
		initScreens();
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

			gs[fullScreen] = createGraphics(width, height);

		} else {
			// War schon eins Vollbild setze die ansicht wieder zurück
			fullScreen = -1;
			initScreens();
		}
	}

	public void draw() {
		// Wenn keines im fullScreen Modus ist zeichne einfach alle normal
		if (fullScreen == -1) {
			for (int i = 0; i < gs.length; i++) {
				image(gs[i], (i % rowCount) * (fWidth), (i / rowCount) * (fHeight));
			}
		} else {
			// Sonst zeichne einfach nur das eine
			image(gs[fullScreen], 0, 0);
		}
	}

	public int getGridNumber() {
		return gs.length;
	}
}
