/* *
 * Luke McDougall
 * 
 * Profiler mode. Runs several tests measuring insert, find and remove time for a tree.
 * Also calculates size, height and balance percentage. 
 *
 * Last updated 19/05/2019
 * */
import java.util.*;

public class TreeTest
{
    /* Function: profiler
     * Import: DSATree tree, String filename.
     * Export: None.
     * 
     * Loads test data from a file into an array of objects. Calls calcStats
     * passing in the array in ascending, descending and random order and the tree.
     */
    public static void profiler(DSATree tree, String filename)
    {
        StockDay[] elements = FileIOTree.loadData(filename);    //Load test data
        //Used for testing remove, can't remove more elements than were inserted.
        int numRepeats = elements.length / 2;

        quickSort(elements);    //Ascending order
        System.out.println("Elements inserted in ascending order.");
        calcStats(elements, tree, numRepeats);
        tree.clear();   //Reset tree

        reverse(elements);  //Descending order
        System.out.println("\nElements inserted in descending order.");
        calcStats(elements, tree, numRepeats);
        tree.clear();   //Reset tree

        random(elements);   //Random order
        System.out.println("\nElements inserted in random order.");
        calcStats(elements, tree, numRepeats);
    }

    /* Function: calcStats
     * Import: StockDay[] elements, DSATree tree, int numRepeats
     * Export: None.
     * 
     * Calculates average insert, find and remove time for passed tree ADT with 
     * passed test data.
     */
    private static void calcStats(StockDay[] elements, DSATree tree, int numRepeats)
    {
        int numElements = 0;
        long maxTime = 0;
        long time = 0;
        for(StockDay d : elements)      //Measure insert speed
        {   
            try
            {
                String key = d.getTicker();
                time = System.nanoTime();
                tree.insert(key, d);
                time = System.nanoTime() - time;
                maxTime += time;
                numElements++;
            }
            catch(IllegalArgumentException e) {} //duplicate keys in data file means exceptions are expected
        }
        
        System.out.printf("    Average insert time for %d elements is %d nanoseconds\n", numElements, maxTime / numElements);
        System.out.printf("    Height = %d\n", tree.height());
        int eHt = tree.idealHeight();
        int aHt = tree.height();
        double balance = ((double)eHt / (double)aHt) * 100.0;
        System.out.printf("    %.2f %% balanced\n", balance);   //Print stats

        maxTime = 0;    //Reset max time
        for(int i = 0; i < numRepeats; i++)    //Measure find speed
        {
            int randIdx = (int)(Math.random() * (double)numElements);
            String key = elements[randIdx].getTicker();
            time = System.nanoTime();
            tree.find(key);
            time = System.nanoTime() - time;
            maxTime += time;
        }

        System.out.printf("\n    Average find time for %d size tree is %d nanoseconds\n", numElements, maxTime / numRepeats);
       
        maxTime = 0;    //Reset max time
        try
        {
            int start = (int)(Math.random() * (double)elements.length); //Choose random start point
            for(int i = start; i < start + numRepeats; i++)
            {
                try
                {
                    String key = elements[i % elements.length].getTicker(); //Wrap around to 0 if i exceeds length of array
                    time = System.nanoTime();
                    tree.remove(key);
                    time = System.nanoTime() - time;
                    maxTime += time;
                }
                catch(NoSuchElementException e) {} //duplicate keys in data file means exceptions are expected
            }
        
            System.out.printf("\n    Average remove time is %d nanoseconds\n", maxTime / numRepeats);
            System.out.printf("    Height = %d\n", tree.height());
            eHt = tree.idealHeight();
            aHt = tree.height();
            balance = ((double)eHt / (double)aHt) * 100.0;
            System.out.printf("    %.2f %% balanced\n", balance);
        }
        catch(UnsupportedOperationException e)  //Some tree ADT's do not implement a remove method yet.
        {
            System.out.println("    " + e.getMessage());
        }

    }

    /* Function: random
     * Import: StockDay[] array
     * Export: None
     * Randomises the order of the elements in the passed array.
     */
    private static void random(StockDay[] array)
    {
        for(int i = 0; i < array.length * 2; i++)
        {
            //Choose two random indexes
            int idxa = (int)(Math.random() * array.length);
            int idxb = (int)(Math.random() * array.length);

            //Swap the indexes
            StockDay temp = array[idxa];
            array[idxa] = array[idxb];
            array[idxb] = temp;
        }
    }

    /* Function: reverse
     * Import: StockDay[] elements
     * Export: None.
     * Reverses the order of the passed array.
     */
    private static void reverse(StockDay[] elements)
    {
        int len = elements.length;
        int mid = len / 2;
        for(int i = 0; i < mid; i++)
        {
            StockDay temp = elements[i];
            elements[i] = elements[len - 1 - i];
            elements[len - 1 - i] = temp;
        }
    }

    /* Function: quicksort
     * Import: StockDay[] elements
     * Export: None.
     * Sorting algorithm used to ensure elements are in ascending order
     */
    private static void quickSort(StockDay[] elements)
    {
        quickSortRecurse(elements, 0, elements.length-1);
    }
    
    private static void quickSortRecurse(StockDay[] elements, int leftIdx, int rightIdx)
    {
        if(leftIdx < rightIdx)
        {
            int pivotIdx = (leftIdx + rightIdx) / 2;
            int newPivotIdx = doPartitioning(elements, leftIdx, rightIdx, pivotIdx);
            quickSortRecurse(elements, leftIdx, newPivotIdx -1);
            quickSortRecurse(elements, newPivotIdx + 1, rightIdx);
        }
    }
    
    private static int doPartitioning(StockDay[] elements, int leftIdx, int rightIdx, int pivotIdx)
    {
	    StockDay pivot = elements[pivotIdx];
        elements[pivotIdx] = elements[rightIdx];
        elements[rightIdx] = pivot;
        int currIdx = leftIdx;
        for(int i = leftIdx; i <= rightIdx -1; i++)
        {
            if(elements[i].getTicker().compareTo(pivot.getTicker()) < 0)
            {
                StockDay temp = elements[i];
                elements[i] = elements[currIdx];
                elements[currIdx] = temp;
                currIdx++;
            }
        }	
        
        int newPivotIdx = currIdx;
        elements[rightIdx] = elements[newPivotIdx];
        elements[newPivotIdx] = pivot;
            
        return newPivotIdx;
    }
}
