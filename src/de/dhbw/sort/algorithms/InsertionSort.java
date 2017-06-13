package de.dhbw.sort.algorithms;

import de.dhbw.sort.SortingAlgorithm;
import de.dhbw.sort.AbstractAlgorithmHelper;

public class InsertionSort extends SortingAlgorithm
{
  int currentIndex;

  public InsertionSort(AbstractAlgorithmHelper theHelper)
  {
    helper = theHelper;
    helper.setName("de.dhbw.sort.algorithms.InsertionSort");
  }

  public void initialize()
  {
    currentIndex = 1;
  }
  public void sort()
  {
    for (int b = 0; b < helper.arrayLength() - 1; b++)
    {
      currentIndex = b + 1;
      while (currentIndex > 0 && helper.compare(currentIndex, currentIndex - 1) < 0)
      {
        helper.swap(currentIndex, currentIndex - 1);
        currentIndex--;
      }
    }
  }

}