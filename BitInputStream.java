import java.io.*;

public class BitInputStream {
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
                return -1; // End of stream
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
