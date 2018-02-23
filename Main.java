
/**
 * The main class. Takes user input for text file wanted to compress. Creates a string (builder) for the text
 * requested. After the string is compiled, it creates a CodingTree object that it then compresses. As this happens
 * real time outputs are generated to show compression ratio, size, and run time. 
 * @author Eviatar Goldschmidt, Scott Robertson, Danny Carns
 * 
 * Project 2, Coding Tree, 02/22/2018
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
    	System.out.println("This program makes a Huffman code for a file.");
        System.out.println();
        
        //Takes the file name from input.
        Scanner console = new Scanner(System.in);
        System.out.print("input file name? ");
        String inFile = console.nextLine();
        
        //Timer for the string compile and compression. 
        long startTime = System.nanoTime();
        
        
        FileInputStream input = new FileInputStream(inFile);
        Scanner line = new Scanner(new File(inFile));
        StringBuilder fileString = new StringBuilder();
        
        //After the input file string is started this while loop compiles the lines
        //into the string builder for use in the tree class.
        while(line.hasNextLine()) {
        		fileString.append(line.nextLine());
        		fileString.append('\n');
        		
        }
        
        //Creation of the tree.
        CodingTree t = new CodingTree(fileString.toString());
        
        //time stops
        long endTime = System.nanoTime(); 
		long elapsedTime = (endTime - startTime ) / 1000000;

		//outputs from the generation of the tree.
        File file = new File(inFile);
        double fileSize = file.length();
        System.out.println("\nUncompressed file size : " + (int) fileSize + " bytes");

        File cfile = new File("compressed.txt");
        double cfileSize = cfile.length();
        System.out.println("Compressed file size : " + (int) cfileSize + " bytes");

        double ratio = (cfileSize/fileSize) * 100;
        System.out.println("Compression ratio: " + (int) ratio + "%");
        
        System.out.println("Running time:" + elapsedTime + " milliseconds");

        

    }
}