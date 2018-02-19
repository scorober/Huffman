public class MyPriorityQueue<T> {

    private T[] elements = (T[]) new Object[64];

    private int capacity;

    private int top;

    MyPriorityQueue() {
        top = -1;
        capacity = elements.length;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    void insert(T item) {

    }

    void deleteMin() {}




}
