package de.dhbw.sort.util;

import de.dhbw.sort.visualize.Graphics;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class StaticStatistics {
    HashMap<String, ArrayList<Integer>> stats = new HashMap<>();
    Graphics screen;
    private int run = 0;
    private float xStart, xEnd, yEnd;
    private int xOffset = 60, yOffset = 35;

    private float xDist = 0;
    private int xValueStep = 10;
    private int xStep = 0;

    private float yDist = 0;
    private int yValueStep = 500;
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

        // screen.fill(100, 100, 100); //Optional
        screen.stroke(100, 100, 100);

        screen.drawLine(xOffset, yOffset, xOffset, screen.getHeight() - yOffset);
        screen.drawLine(xOffset, screen.getHeight() - yOffset, screen.getWidth() - xOffset,
                screen.getHeight() - yOffset);

        while (xDist < 40) {
            xStep += xValueStep;
            xDist = (xStep * (screen.getWidth() - xOffset * 2)) / (xEnd - xStart);
        }
        while (yDist < 40) {
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

    public void addData(String theAlgorithmName, int theOperationCount) {
        ArrayList<Integer> values = stats.get(theAlgorithmName);
        if (values == null) {
            values = new ArrayList<Integer>();
        }
        values.add(theOperationCount);
        stats.put(theAlgorithmName, values);
    }

    public void updateScreen() {
        screen.stroke(255, 255, 255);
        float hSpacing, x1, y1, x2, y2;
        Set<String> algorithmNames = stats.keySet();
        for (String algorithm : algorithmNames) {

            ArrayList<Integer> values = stats.get(algorithm);
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
        screen.addFrame();
    }

    public void addFrame() {
        screen.addFrame();
    }

    public void highlight(String theAlgorithmName) {
        System.out.println(theAlgorithmName);
    }
}
