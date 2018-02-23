import java.util.*;

import java.io.*;

public class CodingTree {

	private HuffmanNode huffmanTree;

	public static final int CHAR_MAX = 256;  // max char value to be encoded
	public static final boolean DEBUG = false; // set to true to print ASCII 0s

	private static final int BYTE_SIZE = 8; // digits per byte
	
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
		encode(message);
		decode();
	}

	public void write() throws IOException {
		PrintStream output = new PrintStream(new File("codes.txt"));
		write(huffmanTree, "");
		for(Character key : this.codes.keySet()) {
        		output.println(key);
        		output.println(this.codes.get(key));

        }
		output.close();
		
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

	private void encode(String message) throws IOException {
		PrintStream output = new PrintStream(new File("compressed.txt"));
		int digits = 0;
		int numDigits = 0;
		for(char c : message.toCharArray()) {
			String temp = codes.get(c);
			for(int i = 0; i < temp.length(); i++) {
				int bit = (temp.charAt(i) - '0');
				digits += bit << numDigits;
				numDigits++;
				if (numDigits == BYTE_SIZE) {
					output.write(digits);
					digits = 0;
					numDigits = 0;
				}
			}
		}
		output.close();
	}
	private void decode() throws FileNotFoundException {
//		FileInputStream input = new FileInputStream(inFile);
        Scanner line = new Scanner(new File("compressed.txt"));
        StringBuilder fileString = new StringBuilder();
        while(line.hasNextLine()) {
        		fileString.append(line.nextLine());
        		fileString.append('\n');
        		
        }
        String codedMessage = fileString.toString();
        PrintStream output = new PrintStream(new File("compressed.txt"));
		int digits = 0;
		int numDigits = 0;
		for(char c : codedMessage.toCharArray()) {
			String temp = codes.get(c);
			for(int i = 0; i < temp.length(); i++) {
				int bit = (temp.charAt(i) - '0');
				digits += bit << numDigits;
				numDigits++;
				if (numDigits == BYTE_SIZE) {
					output.write(digits);
					digits = 0;
					numDigits = 0;
				}
			}
		}
		output.close();
	}
}