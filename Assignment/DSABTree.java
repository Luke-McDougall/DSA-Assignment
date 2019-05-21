/* *
 * Luke McDougall
 * 
 * B tree implementation
 *
 * Last updated 11/05/2019
 *
 * Modified version of B-Tree implementation by Justin Wetherell
 * URL: https://github.com/phishman3579/java-algorithms-implementation/blob/master/src/com/jwetherell/algorithms/data_structures/BTree.java
 * Accessed 13/05/2019
 * */
import java.util.*;
import java.lang.reflect.Array;

public class DSABTree<T> implements java.io.Serializable, DSATree<T>
{
    //Class fields
    private int minKeySize;
    private int minChildrenSize;
    private int maxKeySize;
    private int maxChildrenSize;

    private ExternTreeNode root;
    private int size;
    private int height;

    /*INNER CLASS*/

    private class ExternTreeNode implements java.io.Serializable
    {
        /*INNER INNER CLASS*/
        private class InternTreeNode implements java.io.Serializable
        {
            public String key;
            public T value;
            public InternTreeNode(String inKey, T inValue)
            {
                key = inKey;
                value = inValue;
            }
        }
        /*END INNER INNER CLASS*/
        
        public InternTreeNode[] keys;
        public int keyIndex;
        public ExternTreeNode[] children;
        public int childrenIndex;
        public ExternTreeNode parent;

        public ExternTreeNode(ExternTreeNode inParent)
        {
            keys = (InternTreeNode[])Array.newInstance(InternTreeNode.class, maxKeySize + 1);
            keyIndex = 0;
            children = (ExternTreeNode[])Array.newInstance(ExternTreeNode.class, maxChildrenSize + 1);
            childrenIndex = 0;
            parent = inParent;
        }

        /* Function: keyPresent
         * Import: String key
         * Export: boolean present
         *
         * Iterates over the keys in keys array and returns true if any of them
         * are equal to the passed key
         */
        public boolean keyPresent(String key)
        {
            boolean present = false;
            int index = 0;
            while(index < keyIndex && !present)
            {
                if(keys[index].key.equals(key))
                {
                    present = true;
                }
                index++;
            }
            return present;
        }

        /* Function: insert
         * Import: String key. T value
         * Export: None
         * Creates a new InternTreeNode with the passed key and value to the keys array.
         */
        public void insert(String key, T value)
        {
            keys[keyIndex] = new InternTreeNode(key, value);
            keyIndex++;
            if(keyIndex > 1)
            {
                insertionSortKey();
            }
        }

        /* Function: insertChild
         * Import: ExternTreeNode child
         * Export: None
         * Adds the passed node to this nodes children array and sorts it.
         */
        public void insertChild(ExternTreeNode child)
        {
            child.parent = this;
            children[childrenIndex] = child;
            childrenIndex++;
            if(childrenIndex > 1)
            {
                insertionSortChild();
            }
        }
        
        /* Function: insertionSortKey
         * Import: None.
         * Export: None.
         * Sorts the keys array in ascending order.
         *
         * Modified version of algorithm originally submitted in Practical 02.
         */
        private void insertionSortKey()
        {
            for(int i = 1; i < keyIndex; i++)
            {
                int j = i;
                InternTreeNode temp = keys[j];
                while(j > 0 && keys[j-1].key.compareTo(temp.key) > 0)
                {
                    keys[j] = keys[j-1];//move element j-1 right by one
                    j--;
                }   
                keys[j] = temp;
            }
        }
        
        /* Function: insertionSortChild
         * Import: None.
         * Export: None.
         * Sorts the children array in ascending order.
         *
         * Modified version of algorithm originally submitted in Practical 02.
         */
        private void insertionSortChild()
        {
            for(int i = 1; i < childrenIndex; i++)
            {
                int j = i;
                ExternTreeNode temp = children[j];
                while(j > 0 && children[j-1].getKey(0).compareTo(temp.getKey(0)) > 0)
                {
                    children[j] = children[j-1];//move element j-1 right by one
                    j--;
                }   
                children[j] = temp;
            }
        }

        /* Function removeChild
         * Import: ExternTreeNode n
         * Export: None
         * Removes passed node from this nodes children array
         */
        public void removeChild(ExternTreeNode n)
        {
            boolean found = false;
            for(int i = 0; i < childrenIndex; i++)
            {
                if(found)
                {
                    children[i -1] = children[i];
                }
                if(children[i] == n)
                {
                    found = true;
                }
            }

            childrenIndex--;
            children[childrenIndex] = null;
        }
    
        /* Function: split
         * Import: None
         * Export: None
         *
         * Finds the median key in the keys array and adds all keys less than
         * the median to a new node and all keys greater than the median to another
         * new node. The median key is added to this nodes parent. The two new nodes
         * are added to this nodes parents children array and this node is removed 
         * from it's parents children array. Split may be called again on the parent
         * if necessary.
         */
        public void split()
        {
            int medianIdx = keyIndex / 2; 
            ExternTreeNode left = new ExternTreeNode(null);
            for(int i = 0; i < medianIdx; i++)
            {
                left.insert(keys[i].key, keys[i].value);
            }
            if(childrenIndex > 0)
            {
                for(int j = 0; j <= medianIdx; j++)
                {
                    ExternTreeNode n = children[j];
                    left.insertChild(n);
                }
            }

            ExternTreeNode right = new ExternTreeNode(null);
            for(int i = medianIdx + 1; i < keyIndex; i++)
            {
                right.insert(keys[i].key, keys[i].value);
            }
            if(childrenIndex > 0)
            {
                for(int j = medianIdx + 1; j < childrenIndex; j++)
                {
                    ExternTreeNode n = children[j];
                    right.insertChild(n);
                }
            }

            if(parent == null)
            {
                ExternTreeNode newRoot = new ExternTreeNode(null);
                newRoot.insert(keys[medianIdx].key, keys[medianIdx].value);
                root = newRoot;
                root.insertChild(left);
                root.insertChild(right);
                height++;
            }
            else
            {
                parent.insert(keys[medianIdx].key, keys[medianIdx].value);
                parent.removeChild(this);
                parent.insertChild(left);
                parent.insertChild(right);
                if(parent.keyIndex > maxKeySize)
                {
                    parent.split();
                }
            }
        }
        
        //getters
        public T getValue(int index)
        {
            return keys[index].value;
        }
        
        public ExternTreeNode getChild(int index)
        {
            return children[index];
        }

        public String getKey(int index)
        {
            return keys[index].key;
        }
        
        /* Function: toString
         * Import: None
         * Export: String str
         * Recursive to string method used for saving contents of tree in CSV format
         */ 
        public String toString()
        {
            String str = "";
            if(childrenIndex == 0)
            {
                for(int i = 0; i < keyIndex; i++)
                {
                    str += ((StockDay)keys[i].value).toCSV() + "\n";
                }
            }
            else
            {
                for(int i = 0; i < childrenIndex; i++)
                {
                    str += children[i].toString();
                    if( i < keyIndex)
                    {
                        str += ((StockDay)keys[i].value).toCSV() + "\n";
                    }
                }
            }
            return str;
        }

        /* Function: values
         * Import: DSAQueue<T> vals
         * Export: None
         *
         * Recursively stores all values in the tree in a queue used for saving 
         * the tree in a binary file
         */
        public void values(DSAQueue<T> vals)
        {
            if(childrenIndex == 0)
            {
                for(int i = 0; i < keyIndex; i++)
                {
                    vals.enqueue(keys[i].value);
                }
            }
            else
            {
                for(int i = 0; i < childrenIndex; i++)
                {
                    children[i].values(vals);
                    if(i < keyIndex)
                    {
                        vals.enqueue(keys[i].value);
                    }
                }
            }
        }
    }

    //constructor
    public DSABTree()
    {
        minKeySize = 1;
        minChildrenSize = minKeySize + 1; //2
        maxKeySize = 2 * minKeySize; //2
        maxChildrenSize = maxKeySize + 1; //3
        root = new ExternTreeNode(null);
        size = 0;
        height = 0;
    }

    //alternate constructor
    public DSABTree(int order)
    {
        minKeySize = order;
        minChildrenSize = minKeySize + 1;
        maxKeySize = 2 * minKeySize;
        maxChildrenSize = maxKeySize + 1;
        root = new ExternTreeNode(null);
        size = 0;
    }

    public int size()
    {
        return size;
    }

    public int height()
    {
        return height;
    }

    public int idealHeight()
    {
        //log base e of x / log base e of y = log base y of x
        return (int)(Math.log(size) / Math.log(maxChildrenSize));
    }

    public void clear()
    {
        root = null;
        size = 0;
        height = 0;
    }

    public String toString()
    {
        return root.toString();
    }

    public DSAQueue<T> values()
    {
        DSAQueue<T> vals = new DSAQueue<>();
        root.values(vals);
        return vals;
    }

    public void insert(String key, T value)
    {
        if(root == null)
        {
            root = new ExternTreeNode(null);
            root.insert(key, value);
            size++;
        }
        else if(root.childrenIndex == 0)
        {
            if(root.keyIndex < maxKeySize)
            {
                root.insert(key, value);
            }
            else
            {
                root.insert(key, value);
                root.split();
            }
            size++;
        }
        else
        {
            insertRecurse(key, value, root);
        }
    }

    private void insertRecurse(String key, T value, ExternTreeNode node)
    {
        if(node.keyPresent(key))
        {
            throw new IllegalArgumentException(String.format("Key, %s is already in the tree.\n", key));
        }

        if(node.childrenIndex == 0)
        {
            if(node.keyIndex < maxKeySize)
            {
                node.insert(key, value);
            }
            else
            {
                node.insert(key, value);
                node.split();
            }
            size++;
        }
        else
        {
            int numKeys = node.keyIndex, currIdx = 0;
            boolean found = false;
            while(!found)
            {
                String temp = node.getKey(currIdx);
                if(currIdx == 0 && key.compareTo(temp) < 0)
                {
                    insertRecurse(key, value, node.getChild(currIdx));
                    found = true;
                }
                else if(currIdx == numKeys - 1 && key.compareTo(temp) > 0)
                {
                    insertRecurse(key, value, node.getChild(numKeys));
                    found = true;   
                }
                else if(currIdx > 0)
                {   
                    String prev = node.getKey(currIdx - 1);
                    String next = node.getKey(currIdx);
                    if(key.compareTo(prev) > 0 && key.compareTo(next) <= 0)
                    {
                        insertRecurse(key, value, node.getChild(currIdx));
                        found = true;
                    }
                }
                currIdx++;
            }
        }
    }

    public T find(String key)
    {
        T value = null;
        if(root != null)
        {
            if(root.childrenIndex > 0)
            {
                value = findRecurse(key, root);
            }
            else
            {
                boolean found = false;
                int currIdx = 0;
                do
                {
                    if(key.equals(root.getKey(currIdx)))
                    {
                        found = true;
                        value = root.getValue(currIdx);
                    }
                    else
                    {
                        currIdx++;
                        if(currIdx == root.keyIndex)
                        {
                            throw new NoSuchElementException(String.format("Element with key %s does not exist", key));
                        }
                    }
                }while(!found);
            }
        }
        else
        {
            throw new IllegalArgumentException("Error: tree is empty");
        }
        return value;
    }

    private T findRecurse(String key, ExternTreeNode node)
    {
        T value = null;
        if(node.childrenIndex == 0)
        {
            boolean found = false;
            int currIdx = 0;
            do
            {
                if(key.equals(node.getKey(currIdx)))
                {
                    found = true;
                    value = node.getValue(currIdx);
                }
                else
                {
                    currIdx++;
                    if(currIdx == node.keyIndex)
                    {
                        throw new NoSuchElementException(String.format("Element with key %s does not exist", key));
                    }
                }
            }while(!found);
        }
        else
        {
            int numKeys = node.keyIndex, currIdx = 0;
            boolean found = false;
            while(!found)
            {
                String temp = node.getKey(currIdx);
                if(key.equals(temp))
                {
                    value = node.getValue(currIdx);
                    found = true;
                }
                else if(currIdx == 0 && key.compareTo(temp) < 0)
                {
                    value = findRecurse(key, node.getChild(currIdx));
                    found = true;
                }
                else if(currIdx == numKeys - 1 && key.compareTo(temp) > 0)
                {
                    value = findRecurse(key, node.getChild(numKeys));
                    found = true;
                }
                else if(currIdx < numKeys - 1)
                {
                    String next = node.getKey(currIdx + 1);
                    if(key.compareTo(temp) > 0 && key.compareTo(next) < 0)
                    {
                        value = findRecurse(key, node.getChild(currIdx + 1));
                        found = true;
                    }
                }
                currIdx++;
            }
        }
        return value;
    }

    public void remove(String key)
    {
        throw new UnsupportedOperationException("remove not yet implemented.");
    }
}
