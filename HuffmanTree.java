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