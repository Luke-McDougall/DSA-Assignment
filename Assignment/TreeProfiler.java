/* *
 * Luke McDougall
 *
 * Main method for the TreeProfiler program. Takes command line arguments and calls methods from TreeTest
 * or UserInterface.
 *
 * Last updated 19/05/2019
 * */
public class TreeProfiler
{
    public static void main(String[] args)
    {
        if(args.length == 4 || args.length == 1) //Validate correct number of args
        {
            String mode = args[0];
            if(mode.equals("i"))
            {
                UserInterface.menu();
            }
            else if(mode.equals("p"))
            {
                char treetype = args[1].charAt(0);
                int dataSize = Integer.parseInt(args[2]);
                String filename = args[3];
                DSATree<StockDay> tree = null;
                switch(treetype)
                {
                    case 's':
                    tree = new DSABinarySearchTree<>();
                    break;

                    case 'b':
                    tree = new DSABTree<>(dataSize);
                    break;
                
                    case 'r':
                    tree = new RedBlackTree<>(); 
                    break;
                }

                TreeTest.profiler(tree, filename);
            }
        }
        else
        {
            format();   //Wrong number of args. Explain how to use.
        }
    }

    /* Function: format
     * Import: None.
     * Export: None.
     * Prints instructions for how to use this program.
     */
    private static void format()
    {
        System.out.println("Format: <profiler/interactive> <treetype> <data size> <file name>");
        System.out.println("profiler = 'p', interactive = 'i', treetype = 's' binary search tree. 'b' for block tree. 'r' red black tree");
    }
}

