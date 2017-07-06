package de.dhbw.sort.algorithms;

import de.dhbw.sort.util.AbstractAlgorithmHelper;

public class HeapSort extends SortingAlgorithm {
	int currentIndex;

	public HeapSort() {

		this.name="HeapSort";
	}

	public void initialize() {
	}

	public void sort() {
		// Bringe das Array in die Form eines Heaps, so dass folgende
		// Bedingungen erf�llt sind:
		// 1. Die Wurzel jedes Teilbaums ist gr��er, als jedes seiner Kinder
		// 2. Die Kinder einer Wurzel mit dem Index i sind an den Stellen (i+1)
		// * 2 - 1 f�r das linke, und (i+1) * 2 f�r das rechte Kind.
		// (i+1) ist n�tig, da die Indize bei 0 beginnen anstatt bei eins, die
		// Formel aber f�r 1 beginnende Folgen gedacht ist.
		generateMaxHeap();

		// Sortiere das Array
		for (int i = helper.arrayLength() - 1; i > 0; i--) {
			// Die Wurzel (das gr��te Element) kommt ganz nach hinten im Array.
			// Daf�r wird das ehemals letzte Element die neue Wurzel
			helper.swap(i, 0);
			// Die stelle i ist jetzt schon Teil des sortierten Arrays
			// Versenke die neue Wurzel an die passende Position im Heap.
			// Dadurch wird die Heap-Bedingung wiederhergestellt und das gr��te
			// Element ist wieder die Wurzel.
			versenke(0, i);
		}
	}

	private void generateMaxHeap() {
		// Das letzte Element ist das rechte Kind des mittleren Elements im
		// Array.
		// Deswegen starte in der Mitte und versenke alle Elemente bis zum 0.
		// Element
		for (int i = (helper.arrayLength() / 2) - 1; i >= 0; i--) {
			versenke(i, helper.arrayLength());
		}
	}

	// Versenke das Element i. n ist die rechte Grenze. Ab dort beginnt das
	// bereits sortierte Array und muss deshalb nicht beachtet werden
	private void versenke(int i, int n) {
		while (i <= (n / 2) - 1) {
			int kindIndex = ((i + 1) * 2) - 1; // berechnet den Index des linken
												// kinds

			// bestimme ob ein rechtes Kind existiert
			if (kindIndex + 1 <= n - 1) {
				// rechtes kind existiert
				if (helper.compare(kindIndex, kindIndex + 1) < 0) {
					kindIndex++;
				}
				// Jetzt steckt das gr��ere Kind in der Variablen kindIndex;
			}

			// teste ob element sinken muss, also das Kind gr��er ist als die
			// Wurzel
			if (helper.compare(i, kindIndex) < 0) {
				helper.swap(i, kindIndex); // element versenken
				i = kindIndex; // wiederhole den vorgang mit der neuen position
								// solange wir noch nicht an der Grenze n
								// angelangt sind
			} else {
				//Die Wurzel muss nicht mehr sinken und ist an der richtigen Position, also k�nnen wir aufh�ren
				break;
			}
		}
	}

}