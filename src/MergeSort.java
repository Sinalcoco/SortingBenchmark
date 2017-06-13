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
}