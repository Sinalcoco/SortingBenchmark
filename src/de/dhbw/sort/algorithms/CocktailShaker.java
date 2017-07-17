package de.dhbw.sort.algorithms;


public class CocktailShaker extends SortingAlgorithm //<>// //<>// //<>//
{
    int leftBorder, rightBorder, consecutiveSorted;
    boolean goingRight;

    public CocktailShaker() {
        this.name = "ShakerSort";
    }

    public void initialize() {
        leftBorder = 0;
        rightBorder = helper.arrayLength() - 1;
        goingRight = true;
        consecutiveSorted = 0;
    }

    public void sort() {
        while (leftBorder < rightBorder) {
            if (goingRight) {
                for (int i = leftBorder; i < rightBorder; i++) {
                    if (helper.compare(i, i + 1) > 0) {
                        helper.swap(i, i + 1);
                        consecutiveSorted = 0;
                    } else {
                        consecutiveSorted++;
                    }
                }
                if (consecutiveSorted > 0)
                    rightBorder -= consecutiveSorted;
                else
                    rightBorder--;
                goingRight = false;
            } else {
                for (int i = rightBorder; i > leftBorder; i--) {
                    if (helper.compare(i, i - 1) < 0) {
                        helper.swap(i, i - 1);
                        consecutiveSorted = 0;
                    } else {
                        consecutiveSorted++;
                    }
                }
                if (consecutiveSorted > 0)
                    leftBorder += consecutiveSorted;
                else
                    leftBorder++;
                goingRight = true;
            }
        }
    }

}