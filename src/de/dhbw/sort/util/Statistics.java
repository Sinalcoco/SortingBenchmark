package de.dhbw.sort.util;

import de.dhbw.sort.visualize.Graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Statistics {

    HashMap<String, ArrayList<Integer>> stats = new HashMap<>();
    float maxValue;
    Graphics screen;
    private int run = 0;

    public Statistics(Graphics theScreen) {
        screen = theScreen;
        maxValue = 0;
    }

    public void addData(String theAlgorithmName, int theOperationCount) {
        ArrayList<Integer> values = stats.get(theAlgorithmName);
        if (values == null) {
            values = new ArrayList<Integer>();
        }
        values.add(theOperationCount);
        stats.put(theAlgorithmName, values);
        if (theOperationCount > maxValue)
            maxValue = theOperationCount;


    }

    public void updateScreen() {
//		screen.drawBackground(0, 0, 0);
//		screen.stroke(255, 255, 255);
//		float hSpacing, x1, y1, x2, y2;
//		Set<String> algorithmNames = stats.keySet();
//		for (String algorithm : algorithmNames) {
//			ArrayList<Integer> values = stats.get(algorithm);
//			hSpacing = screen.getWidth() / (values.size() + 1);
//			for (int i = 0; i < values.size() - 1; i++) {
//				x1 = hSpacing + i * hSpacing;
//				y1 = screen.getHeight() - values.get(i) / maxValue * screen.getHeight() + 10;
//
//				x2 = hSpacing + (i + 1) * hSpacing;
//				y2 = screen.getHeight() - values.get(i + 1) / maxValue * screen.getHeight() + 10;
//
//				screen.drawLine(x1, y1, x2, y2);
//			}
//		}
//		screen.stroke(0, 0, 0);
//		screen.addFrame();
        highlight("dummy");
    }

    public void highlight(String theAlgorithmName) {
        screen.drawBackground(0, 0, 0);
        float hSpacing, x1, y1, x2, y2;
        Set<String> algorithmNames = stats.keySet();
        for (String algorithm : algorithmNames) {
            if (algorithm == theAlgorithmName) {
                screen.stroke(0, 255, 0);
                screen.strokeWeight(4);
            } else if (algorithm == "n-curve") {
                screen.stroke(0, 255, 255);
                screen.strokeWeight(1);
            } else if (algorithm == "nlogn-curve") {
                screen.stroke(255, 255, 0);
                screen.strokeWeight(1);
            } else if (algorithm == "0.5n2-curve") {
                screen.stroke(125, 0, 130);
                screen.strokeWeight(1);
            } else if (algorithm == "n2-curve") {
                screen.stroke(255, 0, 0);
                screen.strokeWeight(1);
            } else {
                screen.stroke(255, 255, 255);
                screen.strokeWeight(1);
            }
            ArrayList<Integer> values = stats.get(algorithm);
            hSpacing = screen.getWidth() / (values.size() + 1);
            for (int i = 0; i < values.size() - 1; i++) {
                x1 = hSpacing + i * hSpacing;
                y1 = screen.getHeight() - values.get(i) / maxValue * screen.getHeight() + 10;

                x2 = hSpacing + (i + 1) * hSpacing;
                y2 = screen.getHeight() - values.get(i + 1) / maxValue * screen.getHeight() + 10;

                screen.drawLine(x1, y1, x2, y2);
            }
        }
        screen.stroke(0, 0, 0);
        screen.addFrame();
    }
}
