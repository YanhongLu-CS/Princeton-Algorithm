/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;
    private static int initialSize = 16;
    private int capacity;
    private static double ratio = 0.25;

    public RandomizedQueue() {
        array = (Item[]) new Object[initialSize];
        size = 0;
        capacity = initialSize;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == capacity) {
            resize(capacity * 2);
        }
        array[size] = item;
        size += 1;
    }

    private void resize(int len) {
        if (len < size) {
            throw new NoSuchElementException();
        }
        Item[] newArray = (Item[]) new Object[len];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
        capacity = len;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(size);
        Item returnValue = array[idx];
        array[idx] = array[size - 1];
        array[size - 1] = null;
        size -= 1;
        if (size > 0 && (double) size / (double) capacity <= ratio) {
            resize(capacity / 2);
        }
        return returnValue;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(size);
        return array[idx];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int remain;
        private Item[] copy;

        public RandomizedQueueIterator() {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copy[i] = array[i];
            }
            StdRandom.shuffle(copy);
            remain = size;
        }

        public boolean hasNext() {
            return remain != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (remain == 0) {
                throw new NoSuchElementException();
            }
            remain -= 1;
            return copy[remain];

        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 18; i++) {
            rq.enqueue("A" + i);
        }
        System.out.println("first iterator");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("second iterator ");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < 18; i++) {
            System.out.print("deque ");
            System.out.print(rq.dequeue());
            System.out.println(". remain " + rq.size() + " elements. now capacity ");
        }

    }
}
