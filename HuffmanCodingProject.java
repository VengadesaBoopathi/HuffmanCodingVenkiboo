import java.util.*;
import java.io.*;

class HuffmanCodingProject {
    static ArrayList<String> inputText = new ArrayList<String>();

    static void getInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("enter the text file which needs to be compressed");
        String fileName = in.next();

        File fileobjext = new File(fileName);
        try {
            Scanner sc = new Scanner(fileobjext);
            while (sc.hasNextLine()) {
                String input=sc.nextLine();

                for(int i=0;i<input.length();i++){
                    String s= input.charAt(i)+"";
                    inputText.add(s);
                }
            }
            sc.close();
        } catch (Exception e) {
                System.out.println("cannot open file");
        }

    }

    public static void main(String[] args) {
        Encoding encode = new Encoding();
        Decoding decode = new Decoding();
        getInput();
        encode.freqCalc();
        encode.addtotree();
        encode.writingtofile();
        decode.decodeFile();
    }
}