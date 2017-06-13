package de.dhbw.sort.algorithms;

public class BubbleSort extends SortingAlgorithm
{
  
  public BubbleSort(AbstractAlgorithmHelper theHelper)
  {
    helper = theHelper;
    helper.setName("de.dhbw.sort.algorithms.BubbleSort");
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
  }
}