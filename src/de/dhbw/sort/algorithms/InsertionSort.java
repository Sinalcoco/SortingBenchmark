package de.dhbw.sort.algorithms;

public class InsertionSort extends SortingAlgorithm {
    int currentIndex;

    public InsertionSort() {
        this.name = "InsertionSort";
    }

    public void initialize() {
        currentIndex = 1;
    }

    public void sort(){
        for (int b = 0; b < helper.arrayLength() - 1; b++) {
            currentIndex = b + 1;
            while (currentIndex > 0 && helper.compare(currentIndex, currentIndex - 1) < 0) {
                helper.swap(currentIndex, currentIndex - 1);
                currentIndex--;
            }
        }
    }

}