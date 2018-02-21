import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
    	System.out.println("This program makes a Huffman code for a file.");
        System.out.println();

        Scanner console = new Scanner(System.in);
        System.out.print("input file name? ");
        String inFile = console.nextLine();
        
        long startTime = System.nanoTime();
        
        FileInputStream input = new FileInputStream(inFile);
        Scanner line = new Scanner(new File(inFile));
        StringBuilder fileString = new StringBuilder();
        while(line.hasNextLine()) {
        		fileString.append(line.nextLine());
        		fileString.append('\n');
        		
        }
        CodingTree t = new CodingTree(fileString.toString());
        
        long endTime = System.nanoTime(); 
		long elapsedTime = (endTime - startTime ) / 1000000;

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
