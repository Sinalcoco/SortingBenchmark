package de.dhbw.sort.algorithms;

/**
 * Created by jbi on 30.06.2017.
 */
public class CombSort extends SortingAlgorithm {
    public CombSort() {
        this.name = "CombSort";
    }


    @Override
    public void initialize() {

    }

    @Override
    public void sort() {

        int gap = helper.arrayLength();
        boolean sorted = false;
        do {

            gap--;
            if (gap <= 1) {
                sorted = true;
            } else {
                sorted = false;
            }
            int i = 0;
            while (i + gap < helper.arrayLength()) {
                if (helper.compare(i, i + gap) > 0) {
                    helper.swap(i, i + gap);
                }
                ++i;
            }
        } while (!sorted);
    }
}
