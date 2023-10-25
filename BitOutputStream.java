import java.io.*;

class BitOutputStream {
    private OutputStream outputStream;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        currentByte = 0;
        numBitsFilled = 0;
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
        }
    }

    public void close() throws IOException {
        while (numBitsFilled != 0) {
            writeBit(0); // Padding with zeros if needed
        }
        outputStream.close();
    }
    
}
/**
 * BitInputStream
 */


