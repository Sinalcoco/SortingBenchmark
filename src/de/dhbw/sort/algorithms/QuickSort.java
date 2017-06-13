package de.dhbw.sort.algorithms;

import de.dhbw.sort.AbstractAlgorithmHelper;
import de.dhbw.sort.SortingAlgorithm;

public class QuickSort extends SortingAlgorithm {

	public QuickSort(AbstractAlgorithmHelper theHelper) {
		helper = theHelper;
		helper.setName("de.dhbw.sort.algorithms.QuickSort");
	}

	public void initialize() {
	}

	public void sort() {
		quickSort(0, helper.arrayLength() - 1);
	}

	private void quickSort(int leftIndex, int rightIndex)
	{
		if (leftIndex < rightIndex)
		{
			int middle = sortPart(leftIndex, rightIndex);
			quickSort(leftIndex, middle - 1);
			quickSort(middle + 1, rightIndex);
		}
	}
	
	private int sortPart(int leftIndex, int rightIndex) {
		int pivot = rightIndex;
		rightIndex--;
		while (leftIndex <= rightIndex) {
			if (helper.compare(leftIndex, pivot) > 0) {
				helper.swap(leftIndex, rightIndex);
				rightIndex--;
			} else {
				leftIndex++;
			}
		}
		helper.swap(leftIndex, pivot);
		return leftIndex;
	}

}