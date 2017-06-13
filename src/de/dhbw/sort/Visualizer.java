package de.dhbw.sort;

import processing.core.PApplet;

public class Visualizer {
	PApplet processing;
	public final int COMPARE_COLOR = ActionColor.GREEN.hexValue();
	public final int STORAGE_COLOR = ActionColor.BLUE.hexValue();
	public final int MOVE_COLOR = ActionColor.RED.hexValue();
	public final int HIGHLIGHT_COLOR = ActionColor.YELLOW.hexValue();

	public final static int BORDER_THICKNESS = 5;

	private final int INFO_X_OFFSET = 0;
	private final int INFO_Y_OFFSET = 10;
	private final int INFO_BETWEEN_OFFSET = 10;

	private float RECT_WIDTH;
	private int RECT_SPACING;
	private float SCALE;

	private int x;
	private int y;
	private int myWidth;
	private int myHeight;

	public Visualizer(PApplet theParent, int theX, int theY, int theWidth, int theHeight, int theAmountOfValues,
			int theSpacing) {
		x = theX + BORDER_THICKNESS;
		y = theY + BORDER_THICKNESS;
		myWidth = theWidth - BORDER_THICKNESS * 2;
		myHeight = theHeight - BORDER_THICKNESS * 2;
		RECT_SPACING = theSpacing;
		RECT_WIDTH = (float) myWidth / theAmountOfValues - RECT_SPACING;
		SCALE = ((float) myHeight) / (myWidth / (RECT_WIDTH + RECT_SPACING) + 1);
		processing = theParent;
	}

	public Visualizer(PApplet theParent, int theX, int theY, int theWidth, int theHeight, int theAmountOfValues) {
		this(theParent, theX, theY, theWidth, theHeight, theAmountOfValues, 1);
	}

	public int calculateAmountOfBars() {
		return myWidth / RECT_SPACING;
	}

	public void drawArray(int[] theArray) {
		drawBorder();
		for (int i = 0; i < theArray.length; i++)
			drawRect(i, theArray);
	}

	private void drawBorder() {
		processing.fill(210, 105, 30);
		processing.rect(x - BORDER_THICKNESS, y, BORDER_THICKNESS, myHeight);
		processing.rect(x + myWidth, y, BORDER_THICKNESS, myHeight);
		processing.rect(x - BORDER_THICKNESS, y - BORDER_THICKNESS, myWidth + 2 * BORDER_THICKNESS, BORDER_THICKNESS);
		processing.rect(x - BORDER_THICKNESS, y + myHeight, myWidth + 2 * BORDER_THICKNESS, BORDER_THICKNESS);
		processing.fill(255);
	}

	public void clearColumn(int theIndex) {
		processing.fill(0);
		processing.rect(theIndex * RECT_SPACING, 0, RECT_WIDTH, processing.height);
		processing.fill(255);
	}

	public void drawRect(int theIndex, int[] theArray) {
		processing.rect(x + theIndex * (RECT_WIDTH + RECT_SPACING),
				y + (theArray.length + 1 - theArray[theIndex]) * SCALE, RECT_WIDTH, theArray[theIndex] * SCALE);
	}

	public void drawRect(int theIndex, int[] theArray, int theColor) {
		processing.fill(theColor);
		drawRect(theIndex, theArray);
		processing.fill(255);
	}

	public void drawRect(int theIndex, int[] theArray, int theColor, int xOffset, int yOffset) {
		processing.fill(theColor);
		processing.rect(theIndex * RECT_SPACING + xOffset, (theArray.length + 1 - theArray[theIndex]) * SCALE + yOffset,
				RECT_WIDTH, theArray[theIndex] * SCALE);
		processing.fill(255);
	}

	public void drawName(String theName) {
		processing.text("Algorithm: " + theName, x + INFO_X_OFFSET, y + INFO_Y_OFFSET);
	}

	public void drawComparisons(int theAmount) {
		processing.text("Comparisons: " + theAmount, x + INFO_X_OFFSET, y + INFO_Y_OFFSET + INFO_BETWEEN_OFFSET);
	}

	public void drawMoves(int theAmount) {
		processing.text("Moves: " + theAmount, x + INFO_X_OFFSET, y + INFO_Y_OFFSET + INFO_BETWEEN_OFFSET * 2);
	}
}