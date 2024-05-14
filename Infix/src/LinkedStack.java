public class LinkedStack<E> {
    private Node<E> top; // Top of the stack
    private int size; // Number of elements in the stack

    private static class Node<E> {
        E element; // Data in the node
        Node<E> next; // Reference to the next node

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
    public LinkedStack() {
        top = null;
        size = 0;
    }
    // Push an element onto the stack
    public void push(E item) {
        top = new Node<>(item, top);
        size++;
    }
    // Pop an element from the top of the stack
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        E item = top.element;
        top = top.next;
        size--;
        return item;
    }
    // Peek at the top element of the stack
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        return top.element;
    }
    // Check if the stack is empty
    public boolean isEmpty() {
        return top == null;
    }
    // Get the number of elements in the stack
    public int size() {
        return size;
    }
    // Clear the stack
    public void clear() {
        top = null;
        size = 0;
    }
}
