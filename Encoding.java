import java.util.*;
import java.io.*;

class Encoding {
    public ArrayList<String> inputText = new ArrayList<String>();
    public HashMap<String, Integer> frequency = new HashMap<String, Integer>();
    public PriorityQueue<HuffmanTree> Qobj = new PriorityQueue<HuffmanTree>(new frequencyCompare());
    static HashMap<String, String> newTable = new HashMap<String, String>();

    /*
     * FUNCTION TO GET TEXT FILE AS INPUT AND STORE THE CHARCTERS IN THE FILE IN
     * ARRAYLIST OF STRING TYPE.
     */

    void getInput() {
        Scanner in = new Scanner(System.in);

        System.out.println("\nENTER THE NAME OF THE TEXT FILE TO BE COMPRESSED");
        String fileName = in.next();

        File fileobject = new File(fileName);
        try {
            Scanner sc = new Scanner(fileobject);
            while (sc.hasNextLine()) {
                String input = sc.nextLine();

                for (int i = 0; i < input.length(); i++) {
                    String s = input.charAt(i) + "";
                    inputText.add(s);
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("cannot open file");
        }
    }

    /*
     * CALCULATES THE FREQUENCY OF EACH CHARACTER OCCURING IN THE TEXT FILE AND
     * STORES IT IN frequency HASHMAP
     */

    void freqCalc() {

        for (int i = 0; i < inputText.size(); i++) {
            String c = inputText.get(i);
            if (frequency.containsKey(c)) {
                int count = frequency.get(c);
                frequency.put(c, ++count);
            } else {
                frequency.put(c, 1);
            }
        }
        System.out.println("\n1.FREQUENCY OF CHARACTERS \n");
        System.out.println(frequency);
    }

    /*
     * CREATE LEAF NODE OF TYPE HUFFMAN TREE AND ADD IT TO PRIORITY QUEUE FOR
     * ARRANGEMENT IN ASCENDING ORDER THEN POLL FIRST TWO NODE AND MERGE AND PUSH
     * TILL ONE NODE LEFT IN PRIORITY QUEUE WHICH IS ROOT.
     */

    void addtotree() {
        for (String key : frequency.keySet()) {
            Integer value = frequency.get(key);
            HuffmanTree leafnode = new HuffmanTree(key, value);
            Qobj.add(leafnode);
        }

        while (Qobj.size() > 1) {
            HuffmanTree left = Qobj.poll();
            HuffmanTree right = Qobj.poll();

            HuffmanTree parent = new HuffmanTree(left.getalphabet() + right.getalphabet(), left.freq + right.freq);
            parent.setLeft(left);
            parent.setRight(right);
            Qobj.add(parent);
        }
        HuffmanTree root = Qobj.poll();

        generateHuffmanCodes(root, "", newTable);
        System.out.println(
                "\n2.GENERATED HUFFMAN CODE FOR EACH DISTINCT CHARACTERS (characters with high frequency has less bits of code)--\n");
        System.out.println(newTable);
    }

    /* generating huffman code using recusive tree travesal */
    void generateHuffmanCodes(HuffmanTree root, String code, HashMap<String, String> newTable) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            newTable.put(root.getalphabet(), code);
            return;
        }

        generateHuffmanCodes(root.left, code + "0", newTable);
        generateHuffmanCodes(root.right, code + "1", newTable);
    }

    /*
     * this method writes the hufman code for each character in bit by bit format
     * using the customised bitoutputstream class into secretoutput.huf file
     */
    static String outputFile = "secretoutput.huf";

    void writingtofile() {
        try {
            BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream(outputFile));
            for (String key : inputText) {
                String value = newTable.get(key);
                for (int i = 0; i < value.length(); i++) {
                    if (value.charAt(i) == '0') {
                        bitOutputStream.writeBit(0);
                    } else {
                        bitOutputStream.writeBit(1);
                    }
                }
            }
            System.out.println("\n\n3.Successfully Encoded (COMPRESSED) the file into " + outputFile + "\n");
            bitOutputStream.close();
        } catch (IOException e) {
            System.out.println("Unable to open or write to the file.");
        }
    }

}

/* custom comparator for priority queue */
class frequencyCompare implements Comparator<HuffmanTree> {
    public int compare(HuffmanTree n1, HuffmanTree n2) {
        if (n1.freq > n2.freq)
            return 1;
        else if (n1.freq < n2.freq)
            return -1;
        else
            return 0;
    }
}

/* huffman tree */
class HuffmanTree {
    String alphabets;
    int freq;
    HuffmanTree left;
    HuffmanTree right;

    public HuffmanTree(String alphabets, int freq) {
        this.alphabets = alphabets;
        this.freq = freq;
        left = null;
        right = null;
    }

    public String getalphabet() {
        return alphabets;
    }

    public int getfreq() {
        return freq;
    }

    void setLeft(HuffmanTree left) {
        this.left = left;
    }

    void setRight(HuffmanTree right) {
        this.right = right;
    }
}

/*
 * THIS CLASS CREATES THE secretouptut.huf BY WRITING THE HUFMAN CODE OF EACH
 * CHARACTER BY STORING 8 BIT INTO 1 BYTE AND WRITING INTO FILE SINCE WRITING
 * BIT IN FILE IS NOT POSSIBLE
 * IN THE END IF 8 BIT IS NOT FILLED BY THE OUTPUT IT FILLS 0 AND COMPLETE A
 * BYTE
 */

class BitOutputStream {
    private OutputStream outputStream;
    private int currentByte;
    private int numBitsFilled;
    static public int totalsize;

    public BitOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        currentByte = 0;
        numBitsFilled = 0;
        totalsize = 0;
    }

    public void writeBit(int bit) throws IOException {
        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Bit must be 0 or 1.");
        }
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;
        if (numBitsFilled == 8) {
            outputStream.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
            totalsize += 8;
        }
    }

    public void close() throws IOException {
        while (numBitsFilled != 0) {
            writeBit(0);
        }
        outputStream.close();
    }

}

/* TIHS CLASS RETREIVES EACH CHARACTER FROM .huf FILE */
class BitInputStream {
    private InputStream input;
    private int currentByte;
    private int bitsRemaining;

    public BitInputStream(InputStream input) {
        this.input = input;
        this.currentByte = 0;
        this.bitsRemaining = 0;
    }

    public int readBit() throws IOException {
        if (bitsRemaining == 0) {
            currentByte = input.read();
            if (currentByte == -1) {
                return -1;
            }
            bitsRemaining = 8;
        }

        int bit = (currentByte >> (bitsRemaining - 1)) & 1;
        bitsRemaining--;

        return bit;
    }

    public void close() throws IOException {
        input.close();
    }
}
