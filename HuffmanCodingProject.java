/*
 * Author: VENGADESA BOOPATHI
 * DURATION :OCTOBER 2023
 */

/*
 * THIS IS THE MAIN CLASS WHERE ALL THE METHOD FROM OTHER CLASSES ARE BEING CALLED.
 */

import java.util.Scanner;

class HuffmanCodingProject {
    static Encoding encode = new Encoding();
    static Decoding decode = new Decoding();
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        encode.getInput();
        encode.freqCalc();
        encode.addtotree();
        encode.writingtofile();

        System.out.println("PRESS 'y' FOR DECODING  THE .huf FILE");
        String choice = in.next();
        if (choice.equals("y")) {
            decode.decodeFile();
            HuffmanCodingProject.efficiency();
        }
    }

    /* EFFICIENCY CALCULATION OF HUFFMAN ALGORITHM */
    static void efficiency() {
        int no_of_characters = 0;
        int distinct_characters = 0;
        int size_of_variable_code = 0;

        for (String key : encode.frequency.keySet()) {
            int value = encode.frequency.get(key);
            no_of_characters += value;
        }
        for (String key : Encoding.newTable.keySet()) {
            distinct_characters++;
            String value = Encoding.newTable.get(key);
            size_of_variable_code += value.length();
        }
        no_of_characters = no_of_characters * 8;
        System.out.print("ORIGINAL SIZE OF YOUR TEXT FILE\t\t\t");
        System.out.println(no_of_characters + " bits\n");

        System.out.println("SIZE OF YOUR COMPRESSED FILE\t\t\t" + BitOutputStream.totalsize + " bits\n");
        System.out.print("SIZE OF YOUR HUFFMAN TABLE \t\t\t");

        int size_of_table = distinct_characters + size_of_variable_code;
        System.out.println(size_of_table + " bits\n");

        int Compressed_file_size = BitOutputStream.totalsize + size_of_table;
        System.out.print("THUS THE TOTAL SIZE = SIZE OF COMPRESSED FILE + SIZE OF HUFFMAN TABLE\t=\t");
        System.out.println(Compressed_file_size + " bits\n");

        System.out.print("EFFICIENCY OF COMPRESSION\t\t\t");
        float percentage = ((float) Compressed_file_size / (float) (no_of_characters)) * 100;
        System.out.println(100.0 - percentage + " %");
    }
}