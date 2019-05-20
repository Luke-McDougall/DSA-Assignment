/* *
 * Luke McDougall
 *
 * User interface
 *
 * 17/05/2019
 * */
import java.util.*;

public class UserInterface
{
    /* Function: menu
     * Import: None.
     * Export: None.
     * Main menu for interactive mode.
     */
    public static void menu()
    {
        Scanner sc = new Scanner(System.in);
        DSATree<StockDay> tree = null;
        String menu = "Please make a choice:\n1: Load new data.\n2: Tree find.\n"
                      + "3: Tree insert.\n4: Tree delete.\n5: Tree statistics."
                      + "\n6: Save tree.\n7: Quit.";    //String of options displayed to user.
        int choice = 0; 
        boolean dataLoaded = false; //make sure data has been loaded before trying to access a tree
        do
        {
            choice = intInput(1, 7, menu);
            switch(choice)
            {
                case 1: //Load data
                String chooseTree = "What kind of tree?\n1: Binary Search Tree.\n"
                                    + "2: B-Tree.\n3: Red-Black Tree.\n4: Back.";
                int treeChoice = intInput(1, 4, chooseTree);
                //Type of data storage is decided by filename extension
                System.out.println("Enter the file name. File name must end with .txt .ser or .bin");
                String filename = sc.nextLine();
                try
                {
                    switch(treeChoice)
                    {
                        case 1: //binary search tree
                        tree = new DSABinarySearchTree<>();
                        tree = FileIOTree.loadData(tree, filename);
                        dataLoaded = true;
                        break;

                        case 2: //B-Tree
                        tree = new DSABTree<>();
                        tree = FileIOTree.loadData(tree, filename);
                        dataLoaded = true;
                        break;

                        case 3: //Red-Black tree
                        tree = new RedBlackTree<>();
                        tree = FileIOTree.loadData(tree, filename);
                        dataLoaded = true;
                        break;

                        case 4:
                        System.out.println("Returning...");
                        break;
                    }
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
                break;
                
                case 2: //Tree find
                if(dataLoaded)
                {
                    treeFind(tree);
                }
                else
                {
                    System.out.println("You must load data before accessing a tree.");
                }
                break;

                case 3: //Tree insert
                if(dataLoaded)
                {
                    treeInsert(tree);
                }
                else
                {
                    System.out.println("You must load data before accessing a tree.");
                }
                break;

                case 4: //Tree delete
                if(dataLoaded)
                {
                    treeDelete(tree);
                }
                else
                {
                    System.out.println("You must load data before accessing a tree.");
                }
                break;

                case 5: //Tree statistics
                if(dataLoaded)
                {
                    treeStats(tree);
                }
                else
                {
                    System.out.println("You must load data before accessing a tree.");
                }
                break;

                case 6: //Save tree
                if(dataLoaded)
                {
                    System.out.println("Enter the file name. File name must end with .txt .ser or .bin");
                    filename = sc.nextLine();
                    try
                    {
                        FileIOTree.saveTree(tree, filename);
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else
                {
                    System.out.println("No data loaded to be saved");
                }
                break;

                case 7: //Exit
                System.out.println("Closing...");
                break;
            }
        }while(choice < 7);
    }

    /* Function: intInput
     * Import: int min, int max, String prompt2
     * Export: int val
     * 
     * Handles user input of integer values. Displays prompt2 and loops until
     * user inputs a number greater than min and less than max.
     */
    private static int intInput(int min, int max, String prompt2)
	{
		String prompt = "";
		int val = 0;
		Scanner sc = new Scanner(System.in);
		do
		{
			System.out.println(prompt + prompt2);
			try
			{
				val = sc.nextInt();
			}
			catch(InputMismatchException e)
			{
				val = 0;
				sc.next();
			}
			prompt = String.format("Error, input must be an integer between %d and %d\n", min, max);
		}while(val < min || val > max);
		return val;
	}

    /* Function: treeFind
     * Import: DSATree<StockDay> tree
     * Export: None.
     * 
     * Tests tree's find method. User inputs a key and if the key is found
     * it's corresponding value will be displayed.
     */
    private static void treeFind(DSATree<StockDay> tree)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter key");
        String key = sc.nextLine();
        try
        {
            StockDay s = tree.find(key);
            System.out.println("Success! Found:\n" + s.toString());
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /* Function: treeInsert
     * Import: DSATree<StockDay> tree
     * Export: None.
     *
     * Tests tree's insert method. User inputs a key and the key along with a
     * generic object value are inserted into the tree. 
     */
    private static void treeInsert(DSATree<StockDay> tree)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter key");
        String key = sc.nextLine();
        try
        {
            StockDay d = new StockDay("AAA", new Date(21, 07, 1995), 0.0, 0.0, 0.0, 0.0, 1);
            tree.insert(key, d);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /* Function: treeDelete
     * Import: DSATree<StockDay> tree
     * Export: None
     *
     * Tests tree's remove method. User inputs a key which is passed to the
     * tree's remove method.
     */
    private static void treeDelete(DSATree<StockDay> tree)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter key");
        String key = sc.nextLine();
        try
        {
            tree.remove(key);
            System.out.println("Success!");
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
        catch(UnsupportedOperationException e2)
        {
            System.out.println(e2.getMessage());
        }
    }

    /* Function: treeStats
     * Import: DSATree<StockDay> tree
     * Export: None.
     * Prints the size, height and balance percentage of the passed tree.
     */
    private static void treeStats(DSATree tree)
    {
        int size = tree.size();
        int aHt = tree.height();
        int iHt = tree.idealHeight();
        double balance = (double)iHt / (double)aHt * 100.0;
        System.out.printf("Number of elements = %d, height = %d, %.2f %% balanced\n", size, aHt, balance);
    }
}
