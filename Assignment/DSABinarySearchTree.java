/* Luke McDougall
 * Binary search tree class
 * Last updated 09/04/2019
 *
 * Previously submitted in Practical 5.
 */
import java.util.*;
public class DSABinarySearchTree<T> implements java.io.Serializable, DSATree<T>
{
    /* DSATreeNode
     * Private class for the nodes in the tree
     */
    private class DSATreeNode implements java.io.Serializable
    {
        public String key;
        public T value;
        public DSATreeNode leftChild;
        public DSATreeNode rightChild;

        public DSATreeNode(String inKey, T inValue)
        {
            key = inKey;
            value = inValue;
            leftChild = null;
            rightChild = null;
        }
    }
    
    //class fields
    private DSATreeNode root;
    private int size;
    
    //Constructor    
    public DSABinarySearchTree()
    {
        root = null;
        size = 0;
    }

    public int size()
    {
        return size;
    }

    public void clear()
    {
        root = null;
        size = 0;
    }
    
    /* find
     * Front end for kicking starting recursive method
     */
    public T find(String inKey)
    {
        T value = null;
        value = findRecurse(inKey, root);
        return value;
    }

    /* findRecurse
     * Recursive method that returns the value of a tree node with the passed key
     * throws exception if key can't be found
     */
    private T findRecurse(String inKey, DSATreeNode currNode)
    {
        T val = null;
        if(currNode == null)
        {
            throw new NoSuchElementException("Key " + inKey + " not found");
        }
        else if(currNode.key.equals(inKey))
        {
            val = currNode.value;
        }
        else if(inKey.compareTo(currNode.key) < 0)
        {
            val =  findRecurse(inKey, currNode.leftChild);
        }
        else
        {
            val = findRecurse(inKey, currNode.rightChild);
        }
        return val;
    }

    /* insert
     * front end for kick starting recursive method
     * unless tree is empty
     */
    public void insert(String inKey, T value)
    {
        if(root == null)
        {
            root = new DSATreeNode(inKey, value);
            size++;
        }
        else
        {
            insertRecurse(inKey, value, root);
        }
    }

    /* insertRecurse
     * inserts a new node into the tree, throws exception if passed key
     * is equal to a node already in the tree
     */
    private void insertRecurse(String inKey, T value, DSATreeNode currNode)
    {
        if(currNode.key.equals(inKey))
        {
            throw new IllegalArgumentException("Key " + inKey + " is already in use");
        }
        else if(inKey.compareTo(currNode.key) < 0)
        {
            if(currNode.leftChild == null)
            {
                currNode.leftChild = new DSATreeNode(inKey, value);
                size++;
            }
            else
            {
                insertRecurse(inKey, value, currNode.leftChild);
            }
        }
        else
        {
            if(currNode.rightChild == null)
            {
                currNode.rightChild = new DSATreeNode(inKey, value);
                size++;
            }
            else
            {
                insertRecurse(inKey, value, currNode.rightChild);
            }
        }
    }
    
    /* delete
     * front end for kick starting recursive method. Throws exception if tree
     * is empty
     */
    public void remove(String inKey)
    {
        if(root == null)
        {
            throw new IllegalArgumentException("Tree is empty");
        }
        root = deleteRecurse(inKey, root);
    }

    /* deleteRecurse
     * finds node with passed key to be deleted, throws exception if key
     * isn't found in the tree
     */
    private DSATreeNode deleteRecurse(String inKey, DSATreeNode currNode)
    {
        
        DSATreeNode newNode = currNode;
        if(currNode == null)
        {
            throw new NoSuchElementException("Key " + inKey + " not found");
        }
        else if(inKey.equals(currNode.key))
        {
            newNode = deleteNode(currNode);
            size--;
        }
        else if(inKey.compareTo(currNode.key) < 0)
        {
            currNode.leftChild = deleteRecurse(inKey, currNode.leftChild);
        }
        else
        {
            currNode.rightChild = deleteRecurse(inKey, currNode.rightChild);
        }

        return newNode;
    }

    /* deleteNode 
     * deletes passed node from the tree
     */
    private DSATreeNode deleteNode(DSATreeNode node)
    {
        DSATreeNode newNode = null;
        if(node.leftChild == null && node.rightChild == null) //node has no children
        {
            newNode = null;
        }
        else if(node.leftChild == null && node.rightChild != null) //node has right child
        {
            newNode = node.rightChild;
        }
        else if(node.leftChild != null && node.rightChild == null) //node has left child
        {
            newNode = node.leftChild;
        }
        else //node has both left and right children, calls promoteSuccessor
        {
            newNode = promoteSuccessor(node.rightChild);
            if(newNode != node.rightChild)
            {
                newNode.rightChild = node.rightChild;
            }
            newNode.leftChild = node.leftChild;
        }
        return newNode;
    }
    
    /* promoteSuccessor
     * finds the next smallest key larger than the key to be deleted
     */
    private DSATreeNode promoteSuccessor(DSATreeNode currNode)
    {
        DSATreeNode successor = currNode;
        if(currNode.leftChild != null)
        {
            successor = promoteSuccessor(currNode.leftChild);
            if(successor.key.equals(currNode.leftChild.key))
            {
                currNode.leftChild = successor.rightChild;
            }
        }
        return successor;
    }
         
    /* isEmpty
     * returns true if root is null
     */
    public boolean isEmpty()
    {
    	return root == null;
    }

    /* height
     * front end for kick starting recursive method
     * throws exception if root is null
     */
    public int height()
    {
        if(root == null)
        {
        	throw new IllegalArgumentException("Error: tree is empty");
        }
        return heightRecurse(root);
    }

    /* heightRecurse
     * returns height of the tree
     */
    private int heightRecurse(DSATreeNode currNode)
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

    /* Function: idealHeight
     * Import: None
     * Export: int idealHeight
     *
     * Calculates the ideal height of the tree if it was perfectly balanced
     * based on the number of nodes in the tree.
     */
    public int idealHeight()
    {
        //log base e of x / log base e of y = log base y of x
        return (int)(Math.log(size) / Math.log(2));
    }

    /* inOrderTraversal
     * front end for kick starting recursive method. Throws exception if root is null
     */
    public String toString()
    {
        return toStringRecurse(root);
    }

    private String toStringRecurse(DSATreeNode currNode)
    {
        String str = "";
        if(currNode != null)
        {
            str += toStringRecurse(currNode.leftChild);
            str += ((StockDay)currNode.value).toCSV() + "\n";
            str += toStringRecurse(currNode.rightChild);
        }
        return str;
    }

    /* Function values
     * Import: None
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

    private void valuesRecurse(DSAQueue<T> vals, DSATreeNode currNode)
    {
        if(currNode != null)
        {
            valuesRecurse(vals, currNode.leftChild);
            vals.enqueue(currNode.value);
            valuesRecurse(vals, currNode.rightChild);
        }
    }
}
