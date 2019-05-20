/* *
 * Luke McDougall
 * 
 * Implementation of red black tree
 *
 * Last updated 15/05/2019
 * */
import java.util.*;

public class RedBlackTree<T> implements java.io.Serializable, DSATree<T>
{
    /*INNER CLASS*/
    private class TreeNode implements java.io.Serializable
    {
        //Class fields
        public char colour;     //'r' for red or 'b' for black
        public String key;
        public T value; 
        public TreeNode parent;
        public TreeNode leftChild;
        public TreeNode rightChild;
        
        /* specifies if this node is the left or right child of it's parent. 
         * 'r' right, 'l' left. 'u' unspecified ie this is the root node
         */
        public char orientation;    
        
        public TreeNode(char colour, String key, T value, TreeNode parent, char orientation)
        {
            this.colour = colour;
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.orientation = orientation;
            leftChild = null;
            rightChild = null;
        }

        //If node is red change to black and vice versa
        public void colourSwitch()
        {
            colour = colour == 'r' ? 'b' : 'r';
        }
    }
    /*END INNER CLASS*/

    //Class fields
    private TreeNode root;
    private int size;

    //Default constructor
    public RedBlackTree()
    {
        root = null;
    }

    /* Function clear
     * Import: None
     * Export: None
     * 
     * Resets the state of the tree. The root being set to null will mean the
     * garbage collector will free everything in the tree.
     */
    public void clear()
    {
        root = null;
        size = 0;
    }

    /* Function: toString
     * Import: None
     * Export: None
     *
     * Front end for recursive toString algorithm. Returns a string represenation
     * of the keys and values in the tree in ascending order.
     */
    public String toString()
    {
        return stringRecurse(root);
    }

    private String stringRecurse(TreeNode currNode)
    {
        String str = "";
        if(currNode != null)
        { 
            str += stringRecurse(currNode.leftChild);
            str += ((StockDay)currNode.value).toCSV() + "\n";
            str += stringRecurse(currNode.rightChild);
        }
        return str;
    }
    
    /* Function: values
     * Import: None.
     * Export: DSAQueue<T> vals
     *
     * Front end for recursive traversal algorithm. Returns a queue containing 
     * all the values in the tree in ascending order.
     */
    public DSAQueue<T> values()
    {
        DSAQueue<T> vals = new DSAQueue<>();
        valuesRecurse(vals, root);
        return vals;
    }

    private void valuesRecurse(DSAQueue<T> vals, TreeNode currNode)
    {
        if(currNode != null)
        {
            valuesRecurse(vals, currNode.leftChild);
            vals.enqueue(currNode.value);
            valuesRecurse(vals, currNode.rightChild);
        }
    }

    /* Function: insert
     * Import: String key, T value
     * Export: None.
     *
     * Front end of recursive insert method. Inserts the key and value into 
     * the tree if the key is unique. Throws an exception otherwise.
     */
    public void insert(String key, T value)
    {
        if(root == null)
        {
            root = new TreeNode('b', key, value, null, 'u');
            size++;
        }
        else if(key.equals(root.key))
        {
            throw new IllegalArgumentException(String.format("Key '%s' is already in the tree.\n", key));
        }
        else
        {
            insertRecurse(root, key, value);
        }
    }

    /* Function: insertRecurse
     * Import: TreeNode currNode, String key, T value.
     * Export: None
     *
     * Recursive insert method. Calls flip if a black node with two red children
     * is found. Also handles any appropriate rotating needed to ensure red-black
     * correctness when inserting a node.
     */
    private void insertRecurse(TreeNode currNode, String key, T value)
    {
        if(key.equals(currNode.key))    //Keys must be unique
        {
            throw new IllegalArgumentException(String.format("Key' %s' is already in the tree.\n", key));
        }
    
        if(currNode.leftChild != null && currNode.rightChild != null)   //Avoid null pointer exceptions
        {
            if(currNode.colour == 'b' && currNode.leftChild.colour == 'r' && currNode.rightChild.colour == 'r')
            {
                flip(currNode);
            }
        }

        if(key.compareTo(currNode.key) < 0)
        {
            if(currNode.leftChild == null)
            {
                currNode.leftChild = new TreeNode('r', key, value, currNode, 'l');
                size++;
                if(currNode.colour == 'r')
                {
                    TreeNode grandparent = currNode.parent;  //grandparent of inserted node
                    if(currNode.orientation == 'l') //Outside
                    {
                        currNode.parent.colourSwitch();
                        currNode.colourSwitch();
                        rotateRight(currNode.parent);
                    }
                    else                            //Inside
                    {
                        currNode.parent.colourSwitch();
                        currNode.leftChild.colourSwitch();
                        rotateRight(currNode);
                        rotateLeft(grandparent);
                    }
                }
            }
            else
            {
                insertRecurse(currNode.leftChild, key, value); 
            }
        }
        else
        {
            if(currNode.rightChild == null)
            {
                currNode.rightChild = new TreeNode('r', key, value, currNode, 'r');
                size++;
                if(currNode.colour == 'r')
                {
                    TreeNode grandparent = currNode.parent;  //grandparent of inserted node
                    if(currNode.orientation == 'r') //Outside
                    {
                        currNode.parent.colourSwitch();
                        currNode.colourSwitch();
                        rotateLeft(currNode.parent);
                    }
                    else                            //Inside
                    {
                        currNode.parent.colourSwitch();
                        currNode.rightChild.colourSwitch();
                        rotateLeft(currNode);
                        rotateRight(grandparent);
                    }
                }
            }
            else
            {
                insertRecurse(currNode.rightChild, key, value);
            }
        }
        
    }

    /* Function rotateRight
     * Import: TreeNode rNode
     * Export: None
     * Rotates subtree right about the passed node.
     */
    private void rotateRight(TreeNode rNode)
    {
        TreeNode parent = rNode.parent;
        TreeNode child = rNode.leftChild;

        child.parent = parent;
        child.orientation = rNode.orientation;
        switch(rNode.orientation)   //if orientation 'u' ie rNode is root do nothing
        {
            case 'l':
            parent.leftChild = child;
            break;

            case 'r':
            parent.rightChild = child;
            break;
        }
        rNode.orientation = 'r';    //rotating right will always result in a right orientation
        
        rNode.leftChild = child.rightChild; //crossover node
        if(child.rightChild != null)
        {
            child.rightChild.parent = rNode;
            child.rightChild.orientation = 'l';
        }
        child.rightChild = rNode;
        rNode.parent = child;
        if(child.orientation == 'u')    //update root class field if necessary
        {
            root = child;
        }
    }

    /* Function rotateLeft
     * Import: TreeNode rNode
     * Export: None
     * Rotates subtree left about the passed node.
     */
    private void rotateLeft(TreeNode rNode)
    {
        TreeNode parent = rNode.parent;
        TreeNode child = rNode.rightChild;
        
        child.parent = rNode.parent;
        child.orientation = rNode.orientation;
        switch(rNode.orientation)   //if orientation 'u' ie rNode is root do nothing
        {
            case 'l':
            parent.leftChild = child;
            break;
            
            case 'r':
            parent.rightChild = child;
            break;
        }
        rNode.orientation = 'l';    //rotating left will always result in a left orientation
        
        rNode.rightChild = child.leftChild; //crossover node
        if(child.leftChild != null)
        {
            child.leftChild.parent = rNode;
            child.leftChild.orientation = 'r';
        }
        child.leftChild = rNode;
        rNode.parent = child;
        if(child.orientation == 'u')    //update root class field if necessary
        {
            root = child;
        }
    }

    /* Function: flip
     * Import: TreeNode node
     * Export: None
     * 
     * Swaps the colour of the node with that of it's children. Only pass nodes
     * that are black with two red children to this function.
     */
    private void flip(TreeNode node)
    {
        node.colour = node.parent == null ? 'b' : 'r';  //Leave black if root
        node.leftChild.colour = 'b';
        node.rightChild.colour = 'b';
        
        if(node.parent != null && node.parent.colour == 'r')    //Fix red-red viloations
        {
            char n = node.orientation;
            char p = node.parent.orientation;
            TreeNode grandparent = node.parent.parent;
            if(p == 'r' && n == 'r')        //Outside right
            {
                grandparent.colourSwitch();
                node.parent.colourSwitch();
                rotateLeft(grandparent);
            }
            else if(p == 'l' && n == 'l')   //Outside left
            {
                grandparent.colourSwitch();
                node.parent.colourSwitch();
                rotateRight(grandparent);
            }
            else if(p == 'r' && n == 'l')   //Inside right
            {
                grandparent.colourSwitch();
                node.colourSwitch();
                rotateRight(node.parent);
                rotateLeft(grandparent);
            }
            else                            //Inside left
            {
                grandparent.colourSwitch();
                node.colourSwitch();
                rotateLeft(node.parent);
                rotateRight(grandparent);
            }
        }
    }
    
    /* Function: find
     * Import: String key
     * Export: T value
     * Recursive find method. Throws exception of no element matches the passed key
     */
    public T find(String key)
    {
        T value = null;
        if(root == null)
        {
            throw new IllegalArgumentException("Tree is empty.");
        }
        else if(key.equals(root.key))
        {
            value = root.value;
        }
        else
        {
            value = findRecurse(root, key);
        }
        return value;
    }

    private T findRecurse(TreeNode currNode, String key)
    {
        T value = null;
        if(currNode == null)
        {
            throw new NoSuchElementException(String.format("Key '%s' is not present in the tree.\n", key));
        }
        else if(key.equals(currNode.key))
        {
            value = currNode.value;
        }
        else if(key.compareTo(currNode.key) < 0)
        {
            value = findRecurse(currNode.leftChild, key);
        }
        else
        {
            value = findRecurse(currNode.rightChild, key);
        }
        return value;
    }

    /* Function: remove
     * Import: String key
     * Export: None.
     *
     * Not yet implemented. Calling this function will result in an exception
     * being thrown.
     */
    public void remove(String key)
    {
        throw new UnsupportedOperationException("Remove not yet implemented.");
    }

    /* Function: height
     * Import: None
     * Export: None
     * front end for kick starting recursive method throws exception if root is null
     */
    public int height()
    {
        if(root == null)
        {
        	throw new IllegalArgumentException("Error: tree is empty");
        }
        return heightRecurse(root);
    }

    /* Function: heightRecurse
     * Import: TreeNode currNode
     * Export: int height
     * returns height of the tree
     */
    private int heightRecurse(TreeNode currNode)
    {
        int htSoFar, leftHt, rightHt;
        if(currNode == null)
        {
            htSoFar = -1;
        }
        else
        {
            leftHt = heightRecurse(currNode.leftChild);
            rightHt = heightRecurse(currNode.rightChild);
            if(leftHt > rightHt)
            {
                htSoFar = leftHt + 1;
            }
            else
            {
                htSoFar = rightHt + 1;
            }
        }
        return htSoFar;
    }

    public int idealHeight()
    {
        //log base e of x / log base e of y = log base y of x
        return (int)(Math.log(size) / Math.log(2));
    }

    /* 
     */
    public void redBlackCorrect()
    {
        if(root == null)
        {
        	throw new IllegalArgumentException("Error: tree is empty");
        }
        redBlackRecurse(root);
    }

    /* 
     */
    private int redBlackRecurse(TreeNode currNode)
    {
        int blackHt = 0;
        if(currNode == null)
        {
            blackHt = 0;
        }
        else if(currNode.colour == 'r')
        {
            if(currNode.leftChild != null && currNode.rightChild != null)
            {
                if(currNode.leftChild.colour == 'r' || currNode.rightChild.colour == 'r')
                {
                    System.out.printf("Red-red violation! Parent %s is red, left child %s is %s and right child %s is %s\n", currNode.key,currNode.leftChild.key, (currNode.leftChild.colour == 'r') ? "red" : "black", currNode.rightChild.key, (currNode.rightChild.colour == 'r') ? "red" : "black");
                    //throw new IllegalArgumentException("Tree is not red black correct!. Red parent rule violated.")
                }
            }
            else if(currNode.leftChild != null && currNode.leftChild.colour == 'r')
            {
                System.out.printf("Red-red violation! Parent %s is red. Left child %s is red.\n", currNode.key, currNode.leftChild.key);
            }
            else if(currNode.rightChild != null && currNode.rightChild.colour == 'r')
            {
                System.out.printf("Red-red violation! Parent %s is red. Left child %s is red.\n", currNode.key, currNode.rightChild.key);
            }
        }
        else
        {
            int bl = redBlackRecurse(currNode.leftChild);
            int br = redBlackRecurse(currNode.rightChild);
            if(bl != br)
            {
                System.out.printf("Black counts differ at %s and %s\n", currNode.leftChild.key, currNode.rightChild.key);
                //throw new IllegalArgumentException("Tree is not red black correct! Black counts differ.");
            }
            else
            {
                blackHt = bl + (currNode.colour == 'b' ? 1 : 0); //Could also be br since they are equal
            }
        }
        return blackHt;
    }

    public int size()
    {
        return size;
    }
}
