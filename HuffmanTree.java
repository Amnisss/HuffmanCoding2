/*  Student information for assignment:
 *
 *  On OUR honor, Rachel Yun and Amruta Soma,
 *  this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: rny82
 *  email address: rny82@utexas.edu
 *  Grader name: Hrutvik Palutla
 *  Section number: 54705
 *
 *  Student 2
 *  UTEID: as228483
 *  email address: as228483@my.utexas.edu
 *
 */

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class HuffmanTree implements IHuffConstants{

    private TreeNode root;

    // builds a Huffman tree from a frequency table
    // pre: freqList != null
    // post: root refers to a valid Huffman tree that includes a PSEUDO_EOF leaf
    public HuffmanTree (int[] freqList) {
        // Priority queue storing nodes ordered by frequency (smallest first)
        PriorityQueue314<TreeNode> q = new PriorityQueue314<>();

        // Add a leaf node for each value that actually appears in the file
        for (int i = 0; i < freqList.length; i++) {
            if (freqList[i] != 0) {
                TreeNode n = new TreeNode(i, freqList[i]);
                q.enqueue(n);
            }
        }

        // adds PSEUDO_EOF to mark end of data
        q.enqueue(new TreeNode(IHuffConstants.PSEUDO_EOF, 1)); 

        // repeatedly combines two lowest frequency nodes into a new internal node
        while (q.size() > 1) {
            // smallest node
            TreeNode n1 = q.dequeue();
            // second smallest
            TreeNode n2 = q.dequeue();
            // internal nod
            TreeNode combined = new TreeNode(n1, 0, n2);
            q.enqueue(combined);

        }
        // remaining node is root of Huffman tree
        root = q.dequeue();
    }

    // creates a map from symbol to its code
    // pre: root != null
    // post: returns a map that has an entry for each leaf
    public Map<Integer, String> createMap() {
        Map<Integer, String> result = new HashMap<>();
        createMapHelper(root, "", result);
        return result;
        
    }

    // recursive helper method for createMap
    // pre: n != null, result != null
    // post: result contains mappings for all leaves
    private void createMapHelper(TreeNode n, String path, Map<Integer, String> result) {
        if (n == null) {
            throw new IllegalArgumentException("TreeNode n cannot be null.");
        }
        if (result == null) {
            throw new IllegalArgumentException("result cannot be null.");
        }
        if (n != null) {
            if (n.isLeaf()) {
                // at a leaf, store the code for this symbol
                result.put(n.getValue(), path);
            }
            // left child, add "0" to path
            createMapHelper(n.getLeft(), path + "0", result);
            // right child, add "1" to path
            createMapHelper(n.getRight(), path + "1", result);
        } 
    }

    // rebuilds Huffman tree from BitInputStream in Standard Tree format
    // pre: btIn != null
    // post: root now refers to new reconstructed Huffman tree
    public HuffmanTree(BitInputStream btIn) throws IOException{
        if (btIn == null) {
            throw new IllegalArgumentException("btIn cannot be null.");
        }
        root = reconstructHelper(btIn);
    }

    // Helper method for HuffmanTree(BitInputStream)
    // pre: btIn != null
    // post: returns root of subtree
    private TreeNode reconstructHelper(BitInputStream btIn) throws IOException {
        if (btIn == null) {
            throw new IllegalArgumentException("btIn cannot be null.");
        }
        int currentBit = btIn.readBits(1);
        if (currentBit == -1) {
            // error: at the end of the file
            throw new IOException("incomplete header");
        }
        if (currentBit == 1) {
            // leaf node, next bits are value stored at this leaf
            int value = btIn.readBits(BITS_PER_WORD + 1);
            if (value == -1) {
                // error: at the end of the file
                throw new IOException("incomplete header");
            }
            return new TreeNode(value, -1); 
        } 

        // add internal node, recursively build left and right
        TreeNode left = reconstructHelper(btIn);
        TreeNode right = reconstructHelper(btIn);

        return new TreeNode(left, -1, right);
    }

    // pre: none
    // post: returns the root node
    public TreeNode getRoot() { // TODO unhygenic
        return root;
    }

    // pre: btOut != null
    // post: writes entire tree encoding to btOut
    public void writeHeader(BitOutputStream btOut) throws IOException {
        if (btOut == null) {
            throw new IllegalArgumentException("btOut cannot be null.");
        }
        preOrderTraversalHelper(root, btOut);
    }

    // pre: n != null, bt != null
    // post: writes the subtree rooted at n to bt
    private void preOrderTraversalHelper(TreeNode n, BitOutputStream bt) throws IOException {
        if (n == null) {
            throw new IllegalArgumentException("TreeNode n cannot be null.");
        }
        if (bt == null) {
            throw new IllegalArgumentException("bt cannot be null.");
        }
        if (n != null) {
            if (n.isLeaf()) {
                // tag as leaf
                bt.writeBits(1,1);
                bt.writeBits(BITS_PER_WORD + 1, n.getValue()); 
            } else {
                // tag as internal
                bt.writeBits(1,0);
            }
            preOrderTraversalHelper(n.getLeft(), bt);
            preOrderTraversalHelper(n.getRight(), bt);
        }
    }


}
