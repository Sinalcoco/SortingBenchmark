package de.dhbw.sort.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import processing.core.PGraphics;

public class Statistics {

	private class Stat {
		String name;
		int operations;

		Stat(String theAlgorithmName, int theOperationCount) {
			name = theAlgorithmName;
			operations = theOperationCount;
		}
		
		public int getOperations()
		{
			return operations;
		}
		
		public String toString()
		{
			return name + ": " + operations + " operations.";
		}
	}

	HashMap<Integer, ArrayList<Stat>> stats;
	PGraphics screen;
	int maxValue;

	public Statistics(PGraphics theScreen) {
		screen = theScreen;
		stats = new HashMap<Integer, ArrayList<Stat>>();
		maxValue = 0;
	}

	public void addData(int theRun, String theAlgorithmName, int theOperationCount) {
		if (stats.get(theRun) == null) {
			stats.put(theRun, new ArrayList<Stat>());
		}
		ArrayList<Stat> list = stats.get(theRun);
		if (theOperationCount > maxValue)
			maxValue = theOperationCount;
		list.add(new Stat(theAlgorithmName, theOperationCount));
		updateScreen();
	}
	
	private void updateScreen()
	{
		int hSpacing = screen.width / (stats.size() + 1);
		Integer [] keys = stats.keySet().toArray(new Integer [stats.keySet().size()]);
		
		screen.beginDraw();
		screen.background(0);
		
		for (int i = 0; i < keys.length; i++)
		{
			for (Stat s : stats.get(keys[i]))
			{
				screen.ellipse(i * hSpacing + hSpacing, (screen.height - ((float)s.getOperations() / maxValue) * screen.height + 10), 10, 10);
			}
		}
		screen.endDraw();
	}

	public void printStatistic(int theRun) {
		System.out.println("Statistics of the " + theRun + ". run");
		ArrayList<Stat> list = stats.get(theRun);
		if (list == null)
			System.out.println("No data yet.");
		else
		{
			for (Stat s : list)
				System.out.println('\t' + s.toString());
		}
	}

	public void setScreen(PGraphics theScreen) {
		screen = theScreen;
	}
}
