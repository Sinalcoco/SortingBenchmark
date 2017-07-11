//package de.dhbw.sort.algorithms;
//
//import de.dhbw.sort.util.OutOfPlaceAlgorithmHelper;
//
///**
// * Created by jbi on 30.06.2017.
// */
//public class RadixSort extends SortingAlgorithm{
//
//        OutOfPlaceAlgorithmHelper helper;
//
//    public RadixSort(OutOfPlaceAlgorithmHelper theHelper)   {
//        helper = theHelper;
//        super.helper = theHelper;
//        helper.setAlgorithmName("RadixSort");
//    }
//
//
//    @Override
//    public void initialize() {
//
//    }
//
//    @Override
//    public void sort() {
//            int     n;                             // Fachnummer
//            int[]   nPart = new int[2];            // Anzahl der Elemente in den beiden Faechern
//            int[][] part  = new int[2][helper.arrayLength()];  // die beiden Faecher haben die Groesse des Arrays a
//
//            // Schleife ueber alle Bits der Schluessel (bei int: 32 Bit)
//            for (int i=0; i<32; i++) {
//                nPart[0] = 0;
//                nPart[1] = 0;
//
//                // Partitionierungsphase: teilt "a" auf die Faecher auf
//                for (int j=0; j<helper.arrayLength(); j++) {
//                    n = (helper.getValue(j)>>i)&1;              // ermittelt die Fachnummer: 0 oder 1
//                    part[n][nPart[n]++] = helper.getValue(j);   // kopiert j-tes Element ins richtige Fach
//                }
//
//                // Sammelphase: kopiert die beiden Faecher wieder nach "a" zusammen
//                System.arraycopy(part[0], 0, a, 0,        nPart[0]);
//                System.arraycopy(part[1], 0, a, nPart[0], nPart[1]);
//            }
//        }
//
//    }
//}
