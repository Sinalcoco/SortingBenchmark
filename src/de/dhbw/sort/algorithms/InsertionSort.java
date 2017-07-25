package de.dhbw.sort.algorithms;

public class InsertionSort extends SortingAlgorithm {
    int currentIndex;

    public InsertionSort() {
        this.name = "InsertionSort";
    }

    public void initialize() {
        currentIndex = 1;
    }

    public void sort() {
        helper.setTemp(0);


        for (int b = 1; b < helper.arrayLength(); b++) {
            helper.setTemp(b);
            currentIndex = b;
            while (currentIndex > 0 && helper.compare(currentIndex - 1, b) > 0) {

                currentIndex--;
            }
            for(int i = b - 1; i >= currentIndex;i--)
            {
                helper.move(i, i+1);

            }

            helper.loadTemp(currentIndex);
        }
    }

    @Override
    public int getColor() {
        return 0xFFf27674;
    }

}