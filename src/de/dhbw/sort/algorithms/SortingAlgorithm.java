package de.dhbw.sort.algorithms;

import de.dhbw.sort.util.AbstractAlgorithmHelper;

public abstract class SortingAlgorithm extends Thread {
	private boolean done = false;
	protected String name;
	protected AbstractAlgorithmHelper helper;

	public SortingAlgorithm() {
	}

	public synchronized void run() {
		while (true) {
			if (!done) {
				initialize();
				this.sort();
				this.helper().ready();
				done = true;
			} else {
				try {
					wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public abstract void initialize();

	public void setHelper(AbstractAlgorithmHelper helper) {
		this.helper = helper;
		this.helper.setAlgorithmName(this.name);
	}

	public abstract void sort();

	public AbstractAlgorithmHelper helper() {
		return helper;
	}

	public boolean done() {
		return done;
	}

	public synchronized void reset() {
		done = false;
		notify();
	}

}