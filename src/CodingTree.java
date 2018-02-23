
/**
 * The Tree class that does all the work generating the code map the files for the output
 * and compression (encrypting). 
 * @author Eviatar Goldschmidt, Scott Robertson, Danny Carns
 * 
 * Project 2, Coding Tree, 02/22/2018
 */

import java.util.*;
import java.io.*;

public class CodingTree {

	/**
	 * This is the tree 'root'.
	 */
	private HuffmanNode huffmanTree;

	/**
	 * This was for the decode that didn't get implemented properly. Disregard.
	 */
	private int numDigitsDec;
	
	/**
	 * This is the maximum value that is to be encoded from the possible ASCII values.
	 */
	public static final int CHAR_MAX = 256;  
	
	/**
	 * This is the size of a byte, used to keep track of bits being written/read.
	 */
	private static final int BYTE_SIZE = 8; // digits per byte
	
	/**
	 * The map of Characters and Strings that is written using the path from the tree as a string
	 * and the leaf assigned as the char.
	 */
	public Map<Character, String> codes;

	/**
	 * This is the bit string for the encode method. 
	 */
	public String bits;
	
	private int digits;

	/**
	 * The CodingTree takes a string that is the entire textfile from the main method. It then 
	 * creates a counting array to store ascii values of the chars that are then put into a priority
	 * queue, which is then used to determine and create the huffman tree. After which it creates
	 * the code map and codes file using the write method. Finally it calls the encode method using
	 * the same string from the constructor call to decode the file along with the new map.
	 * @param message This is from main as the Text file to be read and compressed.
	 * @throws IOException Thrown exception. 
	 */
	public CodingTree(String message) throws IOException {
		codes = new HashMap<Character, String>();
		numDigitsDec = 0;
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
	//	decode("compressed.txt");
	}

	/**
	 * This method that creates the map of codes using the chars from the txt file and location in huffman tree.
	 * It also writes the codes.txt file for compression.
	 * @throws IOException Thrown exception.
	 */
	public void write() throws IOException {
		PrintStream output = new PrintStream(new File("codes.txt"));
		write(huffmanTree, "");
		for(Character key : this.codes.keySet()) {
        		output.println(key);
        		output.println(this.codes.get(key));

        }
		output.close();
		
	}

	/**
	 * Helper method for the writing method.
	 * It takes the root, and the code (1's and 0's) and continues writing them or puts them in the map.
	 * @param root The root of the tree. 
	 * @param HuffmanCode The finished or still being written code that will be put in the map.
	 */
	
	private void write(HuffmanNode root, String HuffmanCode) {
		if(root.getZero() != null) {
			write(root.getZero(), (HuffmanCode + "0"));
			write(root.getOne(), (HuffmanCode + "1"));
		} else {
			codes.put(root.getChar(), HuffmanCode);
		}
	}

	/**
	 * This method takees the text file and then uses it with the codes map to write the compressed
	 * text file. Creating first the printstream to write to during this method. The for loop used 
	 * goes through the codes map, after which it iterates through each characters code and uses
	 * addition by shifting to determine the used ascii value for the compressed characters, each using
	 * 8 bytes apiece.
	 * @param message This is the text file string from the constructor.
	 * @throws IOException Thrown exception.
	 */
	private void encode(String message) throws IOException {
		PrintStream output = new PrintStream(new File("compressed.txt"));
		int digits = 0;
		int numDigits = 0;
		for(char c : message.toCharArray()) {
			String temp = codes.get(c);
			for(int i = 0; i < temp.length() ; i++) {
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
	
	// This method does not work as intended please disregard, working on after project.
	private void decode(String file) throws IOException {
		try{
			FileInputStream input = new FileInputStream(file);
			nextByte(input);
			PrintStream output = new PrintStream(new File("decodedText.txt"));
			boolean isEof = false;
			while (isEof == false) {
				isEof = decode(input, output, huffmanTree);
			}
			output.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
		
	}
	
	// This method does not work as intended please disregard, working on after project.
	private boolean decode(FileInputStream input, PrintStream output, HuffmanNode root) throws IOException {
		boolean isEof = false;
		//int digits = nextByte(input);
		if(root.getZero() == null && root.getOne() == null) {
			if(root.getChar() == CHAR_MAX) {
				input.close();
				return true;
			} else {
			output.print((char) root.getChar());
//			System.out.print((char) root.getChar());
			}
		} else {
			
			int cur = readBit(input);
			
			if(cur == 0) {
				isEof = decode(input, output, root.getZero());
			} else if (cur == 1) {
				isEof = decode(input, output, root.getOne());
			}
		}
		return false;
	}
		
	// This method does not work as intended please disregard, working on after project.
	private int readBit(FileInputStream input) {
		// if at eof, return -1
		
		if (digits == -1)
			return -1;
		int result = digits % 2;
		digits /= 2;
		numDigitsDec++;
		if (numDigitsDec == BYTE_SIZE) {
			nextByte(input);
		}
		return result;
	    
	}
	
	// This method does not work as intended please disregard, working on after project.
	private void nextByte(FileInputStream input) {
        try {
            digits = input.read();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
		numDigitsDec = 0;
	}
}



















