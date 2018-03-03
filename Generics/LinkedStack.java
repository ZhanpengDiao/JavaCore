// create stack without standard library

public class LinkedStack<T> {
    private static class Node<U> {
        U item;
        Node<U> next;
        Node() {
            this.item = null;
            this.next = null;
        }
        Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }
        // check if the current node is the end
        boolean end() {return this.item == null && this.next == null;}
    }

    private Node<T> top = new Node<>(); // the sentinel node
    public void push(T item) {
        top = new Node<T>(item, top);
    }
    public T pop() {
       T ret = top.item;
       if(!top.end()) {
           top = top.next;
       }
       return ret;
    }

    public static void main(String[] args) {
        LinkedStack<String> ls = new LinkedStack<>();
        for(String s: "add some text".split(" ")) {
            ls.push(s);
        }
        String s;
        while((s = ls.pop()) != null) {
            System.out.println(s);
        }
    }
}
