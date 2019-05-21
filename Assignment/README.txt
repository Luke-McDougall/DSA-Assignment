Tree Profiler
Luke McDougall
19/05/2019

Files:
TreeProfiler.java
- Main method for TreeProfiler program. Takes command line arguments.
 
TreeTest.java
- Responsible for profiler mode. Runs several tests measuring insert, find and delete speeds for trees. The values will be inserted
  in ascending, descending and random order. The size, height, and balance percentage will also be calculated.

UserInterface.java
- Responsible for interactive mode. Contains code for the menu, which allows users to load tree data, insert, find or delete 
  a node in the tree, print tree size, height and balance percentage and save tree data.

DSATree.java
- Interface implemented by all tree ADTs ensures that all trees can be tested by the profiler.

DSABinarySearchTree.java
- Implementation of a binary search tree. One of the tree types supported by TreeProfiler.

DSABTree.java
- Implementation of a block-tree. One of the tree types supported by TreeProfiler.

RedBlackTree.java
- Implementation of Red-Black tree. One of the tree types supported by TreeProfiler.

StockDay.java
- Class for storing stock information used for testing the trees.

Date.java
- Class for storing a date. StockDay class depends on this.

DSAQueue.java
- Implementaion of a queue ADT. Used for exporting tree values to be written to a file.

DSALinkedList.java
- Implementation of a linked list ADT. DSAQueue depends on this.

How to use:
Tree profiler is a program designed to measure various properties of several tree ADTs. This can be done automatically
with profiler mode or you can use interactive mode to load a tree and test inserting, finding and deleting elements. Run
TreeProfiler with the following command line arguments <p/i> <r/s/b> <#> <filename>.

p: profiler mode.
i: interactive mode. If you want to enter interactive mode ignore all other command line arguments.
r: red black tree.
s: binary search tree.
b: block-tree.
#: data size. Used as the order for a block-tree. Set this to 0 if you aren't loading a block-tree.
filename: the name of a file to load tree data from.
