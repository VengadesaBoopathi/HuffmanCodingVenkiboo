import java.io.*;

public class Decoding {
    /*
     * this method gets each charcter from secretoutput.huf and comapare with
     * huffman code table and decode(writes) into decodedfile
     */

    public void decodeFile() {
        String inputFile = Encoding.outputFile;
        String outputFile = "DecodedFile.txt";

        try {
            BitInputStream bitInputStream = new BitInputStream(new FileInputStream(inputFile));
            FileWriter fileWriter = new FileWriter(outputFile);

            String codeBit = "";
            int bit;

            while ((bit = bitInputStream.readBit()) != -1) {
                codeBit += (bit + "");
                for (String key : Encoding.newTable.keySet()) {
                    String value = Encoding.newTable.get(key);
                    if (value.equals(codeBit)) {
                        fileWriter.write(key);
                        codeBit = "";
                        break;
                    }
                }
            }

            fileWriter.close();
            bitInputStream.close();
            System.out.println("\nSuccessfully DECODED  the file into " + outputFile + "\n\n");
        } catch (IOException e) {
            System.out.println("Unable to open or read the file.");
        }
    }
}