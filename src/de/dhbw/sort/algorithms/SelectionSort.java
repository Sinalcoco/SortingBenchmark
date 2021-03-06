package de.dhbw.sort.algorithms;

public class SelectionSort extends SortingAlgorithm {
    int smallestIndex;


    public SelectionSort() {
        this.name = "SelectionSort";
    }

    public void initialize() {
        smallestIndex = 1;
    }

    public void sort() {
        for (int b = 0; b < helper.arrayLength(); b++) {
            smallestIndex = b;
            for (int i = b + 1; i < helper.arrayLength(); i++) {
                if (helper.compare(i, smallestIndex) < 0) {
                    smallestIndex = i;
                }
            }
            if (b != smallestIndex)
                helper.swap(b, smallestIndex);
        }
    }

	@Override
	public int getColor() {
		return 0xFF72ccf9;
	}

}