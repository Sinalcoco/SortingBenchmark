package de.dhbw.sort;

import de.dhbw.sort.algorithms.AbstractAlgorithmHelper;
import processing.core.PApplet;

public class InPlaceAlgorithmHelper extends AbstractAlgorithmHelper {

	private Visualizer valuesView;
	
	
	public InPlaceAlgorithmHelper(PApplet theParent, int[] theArray, int theX, int theY, int theWidth, int theHeight) {
		super(theParent, theArray, theX, theY, theWidth, theHeight);
		valuesView = new Visualizer(processing, viewX, viewY, viewWidth, viewHeight, values.length);
	}
	
	public void drawValues() {
		valuesView.drawArray(values);
	}
	
	public void processCommands() {
		if (commands.size() > 0) {
			ready = false;
			AlgorithmCommand command = commands.get(0);
			switch (command.type()) {
			case COMPARE:
				valuesView.drawRect(command.first(), values, valuesView.COMPARE_COLOR);
				valuesView.drawRect(command.second(), values, valuesView.COMPARE_COLOR);
				break;
			case SWAP:
				valuesView.drawRect(command.first(), values, valuesView.MOVE_COLOR);
				valuesView.drawRect(command.second(), values, valuesView.MOVE_COLOR);
				int temp = values[command.first()];
				values[command.first()] = values[command.second()];
				values[command.second()] = temp;
				break;
			case HIGHLIGHT:
				valuesView.drawRect(command.first(), values, command.second());
			}
			commands.remove(0);
		}
		if (commands.size() == 0)
			ready = true;
	}

	public void drawInfo() {
		valuesView.drawName(algorithmName);
		valuesView.drawComparisons(comparisons);
		valuesView.drawMoves(moves);
	}
}