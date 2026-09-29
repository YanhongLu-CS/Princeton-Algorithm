import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node<Item> {
        public Node<Item> prev;
        public Node<Item> next;
        public Item value;

        public Node(Node<Item> p, Item v, Node<Item> n) {
            prev = p;
            next = n;
            value = v;
        }
    }

    private int size;
    private Node<Item> sentinel;

    // construct an empty deque
    public Deque() {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        sentinel.next = new Node<>(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }


    // add the item to the back
    public void addLast(Item item) {
        sentinel.prev = new Node<>(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item returnItem = sentinel.next.value;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return returnItem;
    }


    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item returnItem = sentinel.prev.value;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return returnItem;
    }

    private class dequeIterator implements Iterator<Item> {
        private Node<Item> cur;
        private int remains;

        public dequeIterator() {
            cur = sentinel.next;
            remains = size;
        }

        @Override
        public Item next() {
            if (remains == 0) {
                throw new NoSuchElementException();
            }
            Item returnValue = cur.value;
            cur = cur.next;
            remains -= 1;
            return returnValue;
        }

        @Override
        public boolean hasNext() {
            return remains > 0;
        }

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new dequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            dq.addFirst("A" + i);
        }
        for (int i = 0; i < 5; i++) {
            dq.addLast("B" + 1);
        }
        for (String s : dq) {
            System.out.println(s);
        }
        System.out.println("dq has " + dq.size() + " elements in total");
        for (int i = 0; i < 5; i++) {
            System.out.println(dq.removeFirst());
            System.out.println(dq.removeLast());
            System.out.println(dq.size());
        }
    }
}