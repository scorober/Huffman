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
        long fileSize = file.length();
        System.out.println("Uncompressed file size : " + fileSize + " bytes");

        File cfile = new File("compressed.txt");
        long cfileSize = cfile.length();
        System.out.println("Compressed file size : " + cfileSize + " bytes");

        //Not Working 
        double ratio = (cfileSize/fileSize)*100;
        System.out.println("Compression ratio: " + ratio + '%');
        
        System.out.println("Running time:" + elapsedTime + " milliseconds");

        

    }
}
