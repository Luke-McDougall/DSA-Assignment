import java.util.*;
import java.io.*;
public class DSABinarySearchTreeTest
{
    public static void main(String[] args)
    {
        DSABinarySearchTree<Integer> tree = new DSABinarySearchTree<Integer>();
        //Test insert()
        int key = 0;
        for(int i = 1; i <= 10; i++)
        {
            key = i - 1;
            try
            {
                tree.insert(key, i);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        }
        
        boolean success = true;
        for(int i = 0; i < 10; i++)
        {
            success = success && (tree.find(i) == i + 1);//value should always be key + 1
        }

        if(success)
        {
            System.out.println("INSERT SUCCESS\nFIND SUCCESS");
        }
        
        //Test delete
        for(int i = 4; i <= 7; i++)
        {
            tree.delete(i);
        }
        
        success = true;
        for(int i = 0; i < 10; i++)
        {
            boolean s = true;
            try
            {
                tree.find(i);
                if(i >= 4 && i <= 7)
                {
                    s = false;
                }
                else
                {
                    s = true;
                }
            }
            catch(NoSuchElementException e)
            {
                if(i >= 4 && i <= 7)
                {
                    s = true;
                }
                else
                {
                    s = false;
                }
            }
            success = success && s;
        }
        if(success)
        {
            System.out.println("DELETE SUCCESS");
        }

        int[] array = new int[20];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = i + 1;
        }

        shuffle(array);

        tree = new DSABinarySearchTree<Integer>();
        for(int i = 0; i < array.length; i++)
        {
            tree.insert(array[i], array[i] + 1);
        }
        //test height(). Value should be close to ideal
        printHeight(tree, array.length);
        
        for(int i = 0; i < array.length; i++)
        {
            array[i] = i + 1;
        }

        tree = new DSABinarySearchTree<Integer>();
        insertBalanced(tree, array); 
        //value should be equal to ideal
        printHeight(tree, array.length);
        System.out.println("Size = " + tree.getSize());
        traversalMethods(tree);
        writeCSV(tree, "preOrder.txt");
    }

    public static void traversalMethods(DSABinarySearchTree<Integer> tree)
    {
        DSAQueue<int[]> queue = null;
        queue = tree.inOrderTraversal();
        printTree(queue, "In Order.");
        queue = tree.preOrderTraversal();
        printTree(queue, "Pre Order.");
        queue = tree.postOrderTraversal();
        printTree(queue, "Post Order.");
    }

    public static void printTree(DSAQueue<int[]> queue, String msg)
    {
        String s = "";
        System.out.println(msg);
        while(!queue.isEmpty())
        {
            int[] a = queue.dequeue();
            s += "key: " + a[0] + " value: " + a[1] + "\n";
        }
        System.out.println(s);
    }

    public static void insertBalanced(DSABinarySearchTree<Integer> tree, int[] array)
    {
        bRecurse(tree, array, 0, array.length);
    }

    public static void bRecurse(DSABinarySearchTree<Integer> tree, int[] array, int lIdx, int rIdx)
    {
        int midIdx = (lIdx + rIdx) / 2;
        if(lIdx < rIdx)
        {
            tree.insert(array[midIdx], array[midIdx] + 1);
            bRecurse(tree, array, lIdx, midIdx);
            bRecurse(tree, array, midIdx + 1, rIdx);
        }
    }

    public static void printHeight(DSABinarySearchTree<Integer> tree, int n)
    {
        int eHt = (int)(Math.log(n) / Math.log(2));
        int aHt = tree.height();
        System.out.println("Actual = " + aHt + " ideal = " + eHt);
    }

    public static void shuffle(int[] array)
    {
        for(int i = 0; i < 2*array.length; i++)
        {
            int idxa, idxb, temp;
            idxa = (int)(Math.random()*array.length);
            idxb = (int)(Math.random()*array.length);
            temp = array[idxa];
            array[idxa] = array[idxb];
            array[idxb] = temp;
        }
    }
    
    public static void writeCSV(DSABinarySearchTree<Integer> tree, String fileName)
    {
        DSAQueue<int[]> queue = tree.preOrderTraversal();
        FileOutputStream fileOut = null;
        PrintWriter pw;
        try
        {
            fileOut = new FileOutputStream(fileName);
            pw = new PrintWriter(fileOut);
            while(!queue.isEmpty())
            {
                int[] a = queue.dequeue();
            	pw.println(a[0] + " " + a[1]);
            }
            pw.close();
            fileOut.close();
        } 
        catch(IOException e)
        {
            if(fileOut != null)
            {
                try
                {
                    fileOut.close();
                }
                catch(IOException e2) {}
            }
            System.out.println("Error in file processing: " + e.getMessage());
        }
    }
}
