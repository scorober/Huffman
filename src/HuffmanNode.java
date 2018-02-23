/**
 *
 * This Object allows easier use of binary search trees in the HuffmanTree class.
 * @author Eviatar Goldschmidt, Scott Robertson, Danny Carns
 * 
 * Project 2, Coding Tree, 02/22/2018
 *
 */

public class HuffmanNode implements Comparable<HuffmanNode> {

    private HuffmanNode zero;

    private HuffmanNode one;

    private int frequency;

    private int character;

    // Construcs the object and its two posible pointers (zero and one).
    // Takes two ints as parameters.
    // sets the two fields as the two ints passed.
    public HuffmanNode(int frequency, int character) {
        this.frequency = frequency;
        this.character = character;
        this.zero = null;
        this.one = null;
    }

    // A constructor of the object that doesnt take any parameters and sets all values to default.
    public HuffmanNode() {
        this.frequency = -1;
        this.character = -1;
        this.zero = null;
        this.one = null;
    }

    // Used to get the int of the frequancy.
    public int getFrequency() {
        return this.frequency;
    }

    // Used to get the int Ascii representation for the object.
    public char getChar(){
        return (char)this.character;
    }

    // Used to get the HuffmanNode of the zero child of the object.
    public HuffmanNode getZero() {
        return this.zero;
    }

    // Used to get the HuffmanNode of the one child of the object.
    public HuffmanNode getOne() {
        return this.one;
    }

    // Takes a HuffmanNode as a parameter.
    // Sets the zero child as the new Node.
    public void setZero(HuffmanNode newZero){
        this.zero = newZero;
    }

    // Takes a HuffmanNode as a parameter.
    // Sets the one child as the new Node.
    public void setOne(HuffmanNode newOne) {
        this.one = newOne;
    }

    // Takes another HuffmanNode as a parameter.
    // Compares the two and returns an int indicating which one is considered bigger.
    public int compareTo(HuffmanNode other) {
        int value = this.frequency - other.frequency;
        if(value == 0) {
            return 0;
        } else if( value < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}