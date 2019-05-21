/* *
 * Luke McDougall
 *
 * Unit test for DSABinarySearchTree.java RedBlackTree.java and DSABTree.java
 *
 * Last updated 21/05/2019
 * */
import java.util.*;
public class TreeUnitTest
{
    public static void main(String[] args)
    {
        int[] array = new int[2000];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = i;
        }
        random(array);
        
        DSATree<Integer> tree;
        String format = "Testing %s\n";
        
        //Testing DSABinarySearchTree
        System.out.printf(format, "DSABinarySearchTree");
        tree = new DSABinarySearchTree<>();
        test(tree, array);

        //Testing RedBlackTree
        System.out.printf(format, "RedBlackTree");
        tree = new RedBlackTree<>();
        test(tree, array);

        //Testing DSABTree
        System.out.printf(format, "DSABTree");
        tree = new DSABTree<>(4);
        test(tree, array);
    }

    public static void test(DSATree<Integer> tree, int[] array)
    {
        //Testing insert valid data
        boolean pass = true;
        System.out.println("    Testing insert.");
        String key = "";
        try
        {
            for(int i = 0; i < array.length; i++)
            {
                key = Integer.toString(array[i]);
                tree.insert(key, array[i]);
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("        Insert test failed. " + e.getMessage());
        }
        System.out.println("        Insert test 1 passed.");
        
        //Testing insert invalid data
        for(int i = 0; i < array.length; i++)
        {
            try
            {
                key = Integer.toString(array[i]);
                tree.insert(key, array[i]);
                pass = false;   //Should never be reached
            }
            catch(IllegalArgumentException e) {}
        }
        System.out.println("        Insert test 2 " + (pass ? "passed." : "failed."));
        System.out.printf("        %d keys inserted. Tree size = %d. Tree height %d\n", 
                            array.length, tree.size(), tree.height());
        
        //Testing find elements present
        System.out.println("    Testing find.");
        pass = true;
        for(int i = 0; i < array.length; i++)
        {
            key = Integer.toString(array[i]);
            pass = pass && tree.find(key) == array[i];
        }
        System.out.println("        Find test 1 " + (pass ? "passed." : "failed."));

        //Testing find elements not present
        pass = true;
        for(int i = 0; i < array.length; i++)
        {
            try
            {
                key = Integer.toString(array[i] + array.length); //make sure key isn't present
                tree.find(key);
                pass = false;   //Should never be reached
            }
            catch(NoSuchElementException e) {}
        }
        System.out.println("        Find test 2 " + (pass ? "passed." : "failed."));

        //Testing remove elements present
        System.out.println("    Testing remove.");
        pass = true;
        try
        {
            for(int i = 0; i < array.length / 2; i++)
            {
                key = Integer.toString(array[i]);
                tree.remove(key);
                try
                {
                    tree.find(key);
                    pass = false;   //Should never be reached
                }
                catch(NoSuchElementException c) {}
            }
        }
        catch(UnsupportedOperationException e)
        {
            System.out.println("        Expecting exception thrown for DSABTree and RedBlackTree");
            System.out.println("        " + e.getMessage());
        }
        System.out.println("        Remove test 1 " + (pass ? "passed." : "failed."));

        //Testing remove elements not present
        pass = true;
        try
        {
            for(int i = 0; i < array.length / 2; i++)
            {
                key = Integer.toString(array[i]);
                try
                {
                    tree.remove(key);
                    pass = false;
                }
                catch(NoSuchElementException c) {}
            }
        }
        catch(UnsupportedOperationException e)
        {
            System.out.println("        Expecting exception thrown for DSABTree and RedBlackTree");
            System.out.println("        " + e.getMessage());
        }
        System.out.println("        Remove test 2 " + (pass ? "passed." : "failed."));
        System.out.printf("        Tree size = %d. Tree height %d\n", tree.size(), tree.height());
    }

    /* Function: random
     * Import: int[] array
     * Export: None
     * Randomises the order of the elements in the passed array.
     */
    private static void random(int[] array)
    {
        for(int i = 0; i < array.length * 2; i++)
        {
            //Choose two random indexes
            int idxa = (int)(Math.random() * array.length);
            int idxb = (int)(Math.random() * array.length);

            //Swap the indexes
            int temp = array[idxa];
            array[idxa] = array[idxb];
            array[idxb] = temp;
        }
    }
}
