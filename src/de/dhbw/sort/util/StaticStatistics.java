package de.dhbw.sort.util;

import de.dhbw.sort.visualize.Graphics;
import javafx.util.Pair;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class StaticStatistics {
	private HashMap<String, ArrayList<Integer>> stats = new HashMap<>();
	private HashMap<String, Integer> colors = new HashMap<>();
	private Graphics screen;
	private int run = 0;
	private float xStart, xEnd, yEnd;
	private int xOffset = 60, yOffset = 35;

	private float xDist = 0;
	private int xValueStep = 10;
	private int xStep = 0;

	private float yDist = 0;
	private int yValueStep = 1000;
	private int yStep = 0;

	public StaticStatistics(Graphics theScreen, float theXStart, float theXEnd, float theYEnd) {
		screen = theScreen;
		this.xStart = theXStart;
		this.xEnd = theXEnd;
		this.yEnd = theYEnd;
		screen.drawBackground(0);

		drawAxis();
		screen.addFrame();
	}

	private void drawAxis() {
		screen.fill(255, 255, 255);
		screen.textAlign(PApplet.CENTER, PApplet.BASELINE);
		screen.text("Laufzeitverhalten", screen.getWidth() / 2, yOffset / 1);

		screen.textSize(5);
		// screen.fill(100, 100, 100); //Optional
		screen.stroke(100, 100, 100);

		screen.drawLine(xOffset, yOffset, xOffset, screen.getHeight() - yOffset);
		screen.drawLine(xOffset, screen.getHeight() - yOffset, screen.getWidth() - xOffset,
				screen.getHeight() - yOffset);

		while (xDist < 15) {
			xStep += xValueStep;
			xDist = (xStep * (screen.getWidth() - xOffset * 2)) / (xEnd - xStart);
		}
		while (yDist < 15) {
			yStep += yValueStep;
			yDist = (yStep * (screen.getHeight() - yOffset * 2)) / (yEnd);
		}

		screen.textAlign(PApplet.LEFT, PApplet.TOP);
		for (int x = (int) xStart; x < xEnd; x += xStep) {
			screen.text("" + x, (int) ((x - xStart) / xStep * xDist) + xOffset,
					screen.getHeight() - yOffset + xOffset / 8);
		}
		screen.textAlign(PApplet.RIGHT, PApplet.BASELINE);
		for (int y = 0; y < yEnd; y += yStep) {
			screen.text("" + y, xOffset - xOffset / 8, screen.getHeight() - yOffset - y / yStep * yDist);
		}
		screen.textAlign(PApplet.LEFT, PApplet.BASELINE);
	}

	@Deprecated
	public void addData(String theAlgorithmName, int theOperationCount) {
		addData(theAlgorithmName, theOperationCount, 0xFFFFFFFF);
	}

	public void addData(String theAlgorithmName, int theOperationCount, int theAlgorithmColor) {
		ArrayList<Integer> values = stats.get(theAlgorithmName);
		if (values == null) {
			values = new ArrayList<Integer>();
			colors.put(theAlgorithmName, theAlgorithmColor);

		}
		values.add(theOperationCount);
		stats.put(theAlgorithmName, values);
	}

	private void drawLegend() {
		TreeMap<Integer, String> algorithmRanking = new TreeMap<>();

		for (String algorithmName : stats.keySet()) {
				ArrayList<Integer> valueList = stats.get(algorithmName);
				algorithmRanking.put(valueList.get(valueList.size()-1), algorithmName);
		}
		int counter = algorithmRanking.size();
		for (Entry<Integer, String> algorithm : algorithmRanking.entrySet()) {
			counter--;
			String theAlgorithmName = algorithm.getValue();
			int theAlgorithmColor = colors.get(theAlgorithmName);
			screen.fill(theAlgorithmColor);
			screen.drawRect(screen.getWidth() - 15, yOffset + 10 * counter - 1, 8, 8);

			screen.textAlign(PApplet.RIGHT, PApplet.TOP);
			screen.text(theAlgorithmName, screen.getWidth() - 20, yOffset + 10 * counter);
			screen.textAlign(PApplet.LEFT, PApplet.BASELINE);
			
		}

	}

	public void updateScreen() {
		float hSpacing, x1, y1, x2, y2;
		Set<String> algorithmNames = stats.keySet();
		for (String algorithm : algorithmNames) {
			ArrayList<Integer> values = stats.get(algorithm);
			int color = colors.get(algorithm);
			screen.stroke(color);
			if (run + 1 < values.size()) {
				if (run + 2 < values.size())
					run++;

				x1 = xOffset + run * xDist / xStep;
				y1 = (screen.getHeight() - 2 * yOffset) - values.get(run) / yEnd * (screen.getHeight() - 2 * yOffset)
						+ yOffset;
				x2 = xOffset + (run + 1) * xDist / xStep;
				y2 = screen.getHeight() - 2 * yOffset - values.get(run + 1) / yEnd * (screen.getHeight() - 2 * yOffset)
						+ yOffset;

				screen.drawLine(x1, y1, x2, y2);
			}
		}
		//TODO uncomplicate
		if (stats.get(stats.keySet().iterator().next()).size() >= xEnd - xStart)
		{
			drawLegend();
		}
		screen.addFrame();
	}

	public void addFrame() {
		screen.addFrame();
	}

	public void highlight(String theAlgorithmName) {
		System.out.println(theAlgorithmName);
	}

}
