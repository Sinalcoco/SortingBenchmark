package de.dhbw.sort.util;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {

	private class Stat {
		String name;
		int operations;

		Stat(String theAlgorithmName, int theOperationCount) {
			name = theAlgorithmName;
			operations = theOperationCount;
		}
		
		public String toString()
		{
			return name + ": " + operations + " operations.";
		}
	}

	HashMap<Integer, ArrayList<Stat>> stats;

	public Statistics() {
		stats = new HashMap<Integer, ArrayList<Stat>>();
	}

	public void addData(int theRun, String theAlgorithmName, int theOperationCount) {
		if (stats.get(theRun) == null) {
			stats.put(theRun, new ArrayList<Stat>());
		}
		ArrayList<Stat> list = stats.get(theRun);
		list.add(new Stat(theAlgorithmName, theOperationCount));
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
}
