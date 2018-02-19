import java.util.*;
import java.io.*;

public class CodingTree {

    //lala
    private HuffmanNode huffmanTree;

    public static final int CHAR_MAX = 256;  // max char value to be encoded

    public Map<Character, String> codes;

    public String bits;

    public CodingTree(String message) throws IOException {
        codes = new HashMap<Character, String>();
        int[] counts = new int[CHAR_MAX];
        for(int i = 0; i < message.length(); i++) {
            counts[(int)message.charAt(i)]++;
        }
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
        for(int i = 0; i < counts.length; i++) {
            if(counts[i] != 0) {
                queue.add(new HuffmanNode(counts[i], i));
            }
        }
        queue.add(new HuffmanNode(1,256));
        while(queue.size() > 1) {
            HuffmanNode childOne = queue.poll();
            HuffmanNode childTwo = queue.poll();
            HuffmanNode temp = new HuffmanNode(childOne.getFrequency() + childTwo.getFrequency(), -1);
            temp.setZero(childOne);
            temp.setOne(childTwo);
            queue.add(temp);
        }
        huffmanTree = queue.poll();

        write();
    }


    public void write() throws IOException{
        PrintStream output = new PrintStream(new File("codes.txt"));
        write(huffmanTree, "");
        for (Character key : this.codes.keySet()) {
            output.println(key);
            output.println(this.codes.get(key));
        }
    }

    // Helper method for the writing method.
    // It takes a printStream, a HuffmanNode and a String as parameters.
    private void write(HuffmanNode root, String HuffmanCode) {
        if(root.getZero() != null) {
            write(root.getZero(), (HuffmanCode + "0"));
            write(root.getOne(), (HuffmanCode + "1"));
        } else {
            codes.put(root.getChar(), HuffmanCode);
        }
    }

    // Constructor of the HuffmanTree for the encode class and decode method.
    // Takes a scanner as a parameter.
    // Uses a helper method build for recursion building of the tree.
    public CodingTree(Scanner input) {
        while(input.hasNextLine()) {
            int n= Integer.parseInt(input.nextLine()); // ASCII code
            String code = input.nextLine();  // HuffmanCode
            huffmanTree = build(huffmanTree, n, code);
        }
    }

    // Helper method for the decode constructor of the HuffmanTree.
    // Takes a HuffmanNode, an int and a String as parameters.
    // it returns a HuffmanNode.
    private HuffmanNode build(HuffmanNode root, int ascii, String code) {
        if(root == null) {
            root = new HuffmanNode();
        }
        if(code.equals("")) {
            return new HuffmanNode(0,ascii);
        } else if('0' == code.charAt(0)) {
            root.setZero(build(root.getZero(), ascii, code.substring(1)));
        } else if('1' == code.charAt(0)) {
            root.setOne(build(root.getOne(), ascii, code.substring(1)));
        }
        return root;
    }
}
