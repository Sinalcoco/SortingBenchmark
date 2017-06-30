package de.dhbw.sort.algorithms;

import de.dhbw.sort.util.AbstractAlgorithmHelper;

public class BubbleSort extends SortingAlgorithm
{
  
  public BubbleSort(AbstractAlgorithmHelper theHelper)
  {
    helper = theHelper;
    helper.setAlgorithmName("BubbleSort");
  }
  
  public void initialize()
  {
  }
  
  public void sort()
  {
    for (int b = helper.arrayLength() - 1; b > 0 ; b--)
    {
      for (int i = 0; i < b; i++)
      {
        if (helper.compare(i, i+1) > 0)
          helper.swap(i, i+1);
      }
    }
    try {
      this.wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}