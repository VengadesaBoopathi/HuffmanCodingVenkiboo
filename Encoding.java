import java.util.*;
import java.io.*;

class Encoding extends HuffmanCodingProject {
    HashMap<String, Integer> frequency = new HashMap<String, Integer>();

    void freqCalc() {
        for (int i = 0; i < HuffmanCodingProject.inputText.size(); i++) {
            String c = HuffmanCodingProject.inputText.get(i);
            if (frequency.containsKey(c)) {
                int count = frequency.get(c);
                frequency.put(c, ++count);
            } else {
                frequency.put(c, 1);
            }
        }
        System.out.println(frequency);
    }

    PriorityQueue<HuffmanTree> Qobj = new PriorityQueue<HuffmanTree>(new frequencyCompare());
    public HashMap<String, String> newTable = new HashMap<String, String>();

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

        System.out.println(newTable);
    }

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

    void writingtofile() {
        try {
            BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream("output.huf"));
            for (String key : HuffmanCodingProject.inputText) {
                String value = newTable.get(key);
                for(int i=0;i<value.length();i++){
                    if(value.charAt(i)=='0'){
                        bitOutputStream.writeBit(0);
                    }
                    else{
                        bitOutputStream.writeBit(1);
                    }
                }
            }
            System.out.println("Successfully written to the file.");
            bitOutputStream.close();
        } catch (IOException e) {
            System.out.println("Unable to open or write to the file.");
        }
    }
    
}

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