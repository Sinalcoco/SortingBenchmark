public class HeapSort extends SortingAlgorithm
{
  int currentIndex;

  public HeapSort(AbstractAlgorithmHelper theHelper)
  {
    helper = theHelper;
    helper.setName("HeapSort");
  }

  public void initialize()
  {
  }
  public void sort()
  {
    generateMaxHeap();

    //hier wird sortiert
    for (int i = helper.arrayLength() -1; i > 0; i--) {
      helper.swap(i, 0);
      versenke(0, i);
    }
  }
  /**
   * Erstellt einen MaxHeap Baum im Array 
   * @param a das array
   */
  private void generateMaxHeap() {
    //starte von der Mitte rückwärts.
    for (int i = (helper.arrayLength() / 2) - 1; i >= 0; i--) {
      versenke(i, helper.arrayLength());
    }
  }

  /**
   * versenkt ein element im baum
   * @param a Das Array
   * @param i Das zu versenkende Element
   * @param n Die letzte Stelle im Baum die beachtet werden soll
   */
  private void versenke(int i, int n) {
    while (i <= (n / 2) - 1) {
      int kindIndex = ((i+1) * 2) - 1; // berechnet den Index des linken kind

      //bestimme ob ein rechtes Kind existiert
      if (kindIndex + 1 <= n -1) {
        //rechtes kind existiert
        if (helper.compare(kindIndex, kindIndex+1) < 0) {
          kindIndex++; // wenn rechtes kind größer ist nimm das
        }
      }

      //teste ob element sinken muss 
      if (helper.compare(i, kindIndex) < 0) {
        helper.swap(i, kindIndex); //element versenken
        i = kindIndex; // wiederhole den vorgang mit der neuen position
      } else break;
    }
  }

}