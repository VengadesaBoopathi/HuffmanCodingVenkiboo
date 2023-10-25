import java.io.*;
import java.util.*;

public class Decoding {
    Encoding obj=new Encoding();
    public void decodeFile() {
        String inputFile="output.huf";
        String outputFile="DecodedFile.txt";
        try {
            BitInputStream bitInputStream = new BitInputStream(new FileInputStream(inputFile));
            FileWriter fileWriter = new FileWriter(outputFile);
            
            StringBuilder currentBitString = new StringBuilder();
            
            int bit;
            while ((bit = bitInputStream.readBit()) != -1) {
                currentBitString.append(bit);
                for (String key : obj.newTable.keySet()) {
                    if (obj.newTable.get(key).equals(currentBitString.toString())) {
                        fileWriter.write(key);
                        currentBitString.setLength(0); // Reset the bit string
                        break; // Move on to the next bits
                    }
                }
            }

            fileWriter.close();
            bitInputStream.close();
            System.out.println("Successfully decoded to the file.");
        } catch (IOException e) {
            System.out.println("Unable to open or read the file.");
        }
    }
}
