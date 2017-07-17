package de.dhbw.sort.algorithms;


public class BubbleSort extends SortingAlgorithm {
    public BubbleSort() {
        this.name = "BubbleSort";
    }

    public void initialize() {
    }

    public void sort() {
        for (int b = helper.arrayLength() - 1; b > 0; b--) {
            for (int i = 0; i < b; i++) {
                if (helper.compare(i, i + 1) > 0) {
                    helper.swap(i, i + 1);
                }
            }
        }
    }
}