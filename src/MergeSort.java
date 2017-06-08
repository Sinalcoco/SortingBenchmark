public class MergeSort extends SortingAlgorithm
{
  OutOfPlaceAlgorithmHelper helper;
  public MergeSort(OutOfPlaceAlgorithmHelper theHelper)
  {
    helper = theHelper;
    super.helper = theHelper;
    helper.setName("MergeSort");
  }

  public void initialize()
  {
  }
  
  public void sort() {
        mergesort(0, helper.arrayLength() - 1);
    }

    private void mergesort(int low, int high) {
        // check if low is smaller than high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergesort(low, middle);
            // Sort the right side of the array
            mergesort(middle + 1, high);
            // Combine them both
            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {

        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            //helper[i] = numbers[i];
            helper.move(i, ArrayType.MAIN, i, ArrayType.OUTPUT);
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
            //if (helper[i] <= helper[j]) {
              if (helper.compare(i, ArrayType.OUTPUT, j, ArrayType.OUTPUT) <= 0) {
                //numbers[k] = helper[i];
                helper.move(i, ArrayType.OUTPUT, k, ArrayType.MAIN);
                i++;
            } else {
                //numbers[k] = helper[j];
                helper.move(j, ArrayType.OUTPUT, k, ArrayType.MAIN);
                j++;
            }
            k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            //numbers[k] = helper[i];
            helper.move(i, ArrayType.OUTPUT, k, ArrayType.MAIN);
            k++;
            i++;
        }
        while (j <= high) {
            //numbers[k] = helper[i];
            helper.move(j, ArrayType.OUTPUT, k, ArrayType.MAIN);
            k++;
            j++;
        }

    }
  
  
  
  
  
  
  
  
  
  
  
  /*
  public void sort()
  {
    mergeSort(0, helper.arrayLength()-1);
  }
  
   // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    void merge(int l, int m, int r)
    {
      println("merge(",l,m,r,")");
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m; 
 
        /* Merge the temp arrays 
 
        // Initial indexes of first and second subarrays
        int i = l, j = l + n1;
 
        // Initial index of merged subarry array
        int k = l;
        println("while(",i,"<",n1,"&&",j,"<",l+n1+n2);
        while (i < n1 && j <= r)
        {
          println("Is",i,"<=", j);
            if (helper.compare(i, j) <= 0)
            {
              println("yes");
                helper.move(i, ArrayType.MAIN, k, ArrayType.OUTPUT);
                i++;
            }
            else
            {
              println("no");
                helper.move(j, ArrayType.MAIN, k, ArrayType.OUTPUT);
                j++;
            }
            k++;
        }
 
         println("copying");
        /* Copy remaining elements of L[] if any 
        while (i < n1)
        {
            helper.move(i, ArrayType.MAIN, k, ArrayType.OUTPUT);
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any 
        while (j < n2)
        {
            helper.move(j, ArrayType.MAIN, k, ArrayType.OUTPUT);
            j++;
            k++;
        }
        for (int c = l; c < r; c++)
        {
          helper.move(c, ArrayType.OUTPUT, c, ArrayType.MAIN);
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
    void mergeSort(int l, int r)
    {
        println("mergeSort(",l,r,")");
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;
 
            // Sort first and second halves
            mergeSort(l, m);
            mergeSort(m+1, r);
 
            // Merge the sorted halves
            println("Merging: ", l, m, r);
            merge(l, m, r);
        }
    }  
  
 */
}