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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;
    private int header;
    private Map<Integer, String> codeMap;
    private HuffmanTree huffTree;
    private int[] freqs;
    private boolean preprocessCalled;
    private int bitsSaved;

    /**
     * Preprocess data so that compression is possible ---
     * count characters/create tree/store state so that
     * a subsequent call to compress will work. The InputStream
     * is <em>not</em> a BitInputStream, so wrap it int one as needed.
     * @param in is the stream which could be subsequently compressed
     * @param headerFormat a constant from IHuffProcessor that determines what kind of
     * header to use, standard count format, standard tree format, or
     * possibly some format added in the future.
     * @return number of bits saved by compression or some other measure
     * Note, to determine the number of
     * bits saved, the number of bits written includes
     * ALL bits that will be written including the
     * magic number, the header format number, the header to
     * reproduce the tree, AND the actual data.
     * @throws IOException if an error occurs while reading from the input file.
     */
    public int preprocessCompress(InputStream in, int headerFormat) throws IOException {
        int originalBits = 0;
        BitInputStream bt = new BitInputStream(in);
        int[] frequencies = new int[IHuffConstants.ALPH_SIZE];

        // count how many times each byte value appears in the input
        int code = bt.readBits(IHuffConstants.BITS_PER_WORD);
        while (code != -1) {
            frequencies[code]++;
            // each original symbol is 8 bits
            originalBits += 8;
            code = bt.readBits(IHuffConstants.BITS_PER_WORD);
        }

        bt.close();

        // Build Huffman tree and code map
        huffTree = new HuffmanTree(frequencies);
        codeMap = huffTree.createMap();

        // store header type and frequencies to use in compress
        header = headerFormat;
        freqs = frequencies;

        // estimate how many bits the compressed file will need
        int compressedBits = calculateCompressedBits(headerFormat, codeMap, frequencies);

        preprocessCalled = true;
        bitsSaved = originalBits - compressedBits;
        return bitsSaved;
    }

    // helper method to compute how many bits the compressed file would use
    // pre: headerFormat != null, codeMap != null, frequencies != null
    // post: returns total bits
    private int calculateCompressedBits(int headerFormat, Map<Integer, String> codeMap,
                                        int[] frequencies) {
        if (headerFormat == 0 || codeMap == null || frequencies == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
        int compressedBits = 0;

        // Magic number
        compressedBits += IHuffConstants.BITS_PER_INT;

        // format identifier
        compressedBits += IHuffConstants.BITS_PER_INT;
        
        if (headerFormat == IHuffConstants.STORE_COUNTS) {
            // STORE_COUNTS: one int per symbol in alphabet
            compressedBits += IHuffConstants.BITS_PER_INT * IHuffConstants.ALPH_SIZE;
        } else if (headerFormat == IHuffConstants.STORE_TREE) {
            // STORE_TREE: one int for header size, plus the actual header bits
            compressedBits += IHuffConstants.BITS_PER_INT + (codeMap.keySet().size() *
                (IHuffConstants.BITS_PER_WORD + 1 + 1)) + (codeMap.keySet().size() - 1);
        }

        // bits for all actual data symbols
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                compressedBits += frequencies[i] * codeMap.get(i).length();
            }
        }

        //Pseudo-EOF
        compressedBits += codeMap.get(256).length();

        return compressedBits;
    }

    /**
	 * Compresses input to output, where the same InputStream has
     * previously been pre-processed via <code>preprocessCompress</code>
     * storing state used by this call.
     * <br> pre: <code>preprocessCompress</code> must be called before this method
     * @param in is the stream being compressed (NOT a BitInputStream)
     * @param out is bound to a file/stream to which bits are written
     * for the compressed file (not a BitOutputStream)
     * @param force if this is true create the output file even if it is larger than the input file.
     * If this is false do not create the output file if it is larger than the input file.
     * @return the number of bits written.
     * @throws IOException if an error occurs while reading from the input file or
     * writing to the output file.
     */
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        if (!preprocessCalled) {
            throw new IOException("preprocessCompress must be called before compress.");
        }

        // stops method from creating the output file if force is false
        // and output file is larger than input file
        if (!force && bitsSaved < 0) {
            // no bits written
            return 0;
        }

        BitInputStream btIn = new BitInputStream(in);
        BitOutputStream btOut = new BitOutputStream(out);

        int bitsWritten = compressHelper(btIn, btOut);
        
        // read in the original file
        int code = btIn.readBits(IHuffConstants.BITS_PER_WORD);
        while (code != -1) {
            String compressed = codeMap.get(code);
            // write out the new code for each character
            for (int i = 0; i < compressed.length(); i++) {
                btOut.writeBits(1, compressed.charAt(i) - '0');
                bitsWritten++;
            }
            code = btIn.readBits(IHuffConstants.BITS_PER_WORD);
        }

        // write out pseudo EOF value at the end
        String pseudoEof = codeMap.get(256);
        for (int i = 0; i < pseudoEof.length(); i++) {
            btOut.writeBits(1, pseudoEof.charAt(i) - '0');
            bitsWritten++;
        }

        btOut.close();
        return bitsWritten;
    }

    // helper method for compress
    // pre: same as compress
    // post: updates bitsWritten
    private int compressHelper(BitInputStream btIn, BitOutputStream btOut) throws IOException {
        int bitsWritten = 0;
        // write magic number for hf file
        btOut.writeBits(IHuffConstants.BITS_PER_INT, IHuffConstants.MAGIC_NUMBER);
        bitsWritten += IHuffConstants.BITS_PER_INT;

        if (header == IHuffConstants.STORE_COUNTS) {
            // write identifier for store count header format
            btOut.writeBits(IHuffConstants.BITS_PER_INT, IHuffConstants.STORE_COUNTS);
            bitsWritten += IHuffConstants.BITS_PER_INT;

            // write out frequencies of each character
            for(int k=0; k < IHuffConstants.ALPH_SIZE; k++) {
                btOut.writeBits(BITS_PER_INT, freqs[k]);
                bitsWritten += BITS_PER_INT;
            }
        } else if (header == IHuffConstants.STORE_TREE) {
            // write identifier for store tree header format
            btOut.writeBits(IHuffConstants.BITS_PER_INT, IHuffConstants.STORE_TREE);
            bitsWritten += IHuffConstants.BITS_PER_INT;

            // calculate and write outsize of header
            int size = (codeMap.keySet().size() * (BITS_PER_WORD + 1 + 1)) + (codeMap.keySet().size() - 1);
            btOut.writeBits(BITS_PER_INT, size);
            bitsWritten += BITS_PER_INT;

            // represent the huffman tree in bits
            huffTree.writeHeader(btOut);
            bitsWritten += size;
        }
        return bitsWritten;
    }
    

    /**
     * Uncompress a previously compressed stream in, writing the
     * uncompressed bits/data to out.
     * @param in is the previously compressed data (not a BitInputStream)
     * @param out is the uncompressed file/stream
     * @return the number of bits written to the uncompressed file/stream
     * @throws IOException if an error occurs while reading from the input file or
     * writing to the output file.
     */
    public int uncompress(InputStream in, OutputStream out) throws IOException {
            BitInputStream btIn = new BitInputStream(in);
            BitOutputStream btOut = new BitOutputStream(out);
            int bitsWritten = 0;

            // read magic number
            int isMagic = btIn.readBits(BITS_PER_INT);
            if (isMagic != IHuffConstants.MAGIC_NUMBER) {
                myViewer.showError("Error reading compressed file. \n" +
                    "File did not start with the huff magic number.");
                return -1;
            }

            // uncompress helper method that reads in header and reconstructs huffman tree
            uncompressHelper(btIn);

            codeMap = huffTree.createMap();

            // create a reverse mapping
            Map<String, Integer> decompressMap = new HashMap<>();

            for (int key : codeMap.keySet()) { 
                decompressMap.put(codeMap.get(key), key);
            }

            // helper method that updates bitsWritten
            return helper(bitsWritten, btIn, btOut, decompressMap);
    }

    // helper method for uncompress
    // pre: same as uncompress
    // post: reconstructs huffman tree
    private void uncompressHelper(BitInputStream btIn) throws IOException {
        // read header format
        int headerFormat = btIn.readBits(BITS_PER_INT);

        if (headerFormat == IHuffConstants.STORE_COUNTS) {
            // reconstruct frequency array
            freqs = new int[ALPH_SIZE];
            for(int k=0; k < IHuffConstants.ALPH_SIZE; k++) {
                int frequencyInOriginalFile = btIn.readBits(BITS_PER_INT);
                if (frequencyInOriginalFile == -1) {
                    throw new IOException("Reached end of file while reading frequency data.");
                }
                freqs[k] = frequencyInOriginalFile;
            }
            // reconstruct huffman tree
            System.out.println("over here");
            huffTree = new HuffmanTree(freqs);
        } else if (headerFormat == IHuffConstants.STORE_TREE) {
            System.out.println("going into store tree");
            // read in size of the header
            int bitsLeft = btIn.readBits(BITS_PER_INT);

            // reconstruct huffman tree
            huffTree = new HuffmanTree(btIn);
        }
    }

    // another helper method for uncompress
    // pre: same as uncompress
    // post: updates bitsWritten
    private int helper(int bitsWritten, BitInputStream btIn, BitOutputStream btOut,
                       Map<String, Integer> decompressMap) throws IOException {
        // read compressed data and write out decompressed file
        String currentPath = "";
        String pseudoEofCode = codeMap.get(IHuffConstants.PSEUDO_EOF);

        // read compressed data until PseudoEOF is reached
        while(!currentPath.equals(pseudoEofCode)) {
            // if a valid code, write out the character
            if (decompressMap.keySet().contains(currentPath)) {
                btOut.writeBits(BITS_PER_WORD, decompressMap.get(currentPath));
                bitsWritten += BITS_PER_WORD;
                currentPath = "";
            }

            int currentBit = btIn.readBits(1);
            if (currentBit == -1) {
                throw new IOException("Reached end of compressed file before PSEUDO_EOF.");
            }
            currentPath += currentBit;
        }

        return bitsWritten;
    }

    // pre: none
    // post: myViewer refers to viewer
    public void setViewer(IHuffViewer viewer) {
        if (viewer == null) {
            throw new IllegalArgumentException("IHuffViewer viewer cannot be null.");
        }
        myViewer = viewer;
    }

    // pre: s != null
    //post: updates display with s
    private void showString(String s){
        if (s == null) {
            throw new IllegalArgumentException("String s cannot be null.");
        }
        if (myViewer != null) {
            myViewer.update(s);
        }
    }
}
