package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule {
    //Model:
    //    [a[0], a[1], a[2], .... , a[n - 1]]
    //    n >= 0 - is size of array
    // INV: \forall i \in [0; n) :  a[i] != null

    private static int head = 0, size = 0;
    private static Object[] elements = new Object[1];

    // Pre: n > 0
    // Post: n' = n - 1
    // \forall i \in [0; n') :  a[i]' = a[i]
    // R == a[0]
    public static Object dequeue() {
        assert size > 0;
        size--;
        Object elem = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return elem;
    }

    // Pre: n > 0
    // Post: n' = n - 1
    // \forall i \in [0; n') :  a[i]' = a[i + 1]
    // R = a[n - 1]
    public static Object remove() {
        assert size > 0;
        size--;
        int tail = (head + size) % elements.length;
        Object elem = elements[tail];
        elements[tail] = null;
        return elem;
    }


    // Pre: elem != null
    // Post: n' = n + 1 && a[n]' == elem
    // \forall i \in [0; n) :  a[i]' = a[i]
    public static void enqueue(Object elem) {
        Objects.requireNonNull(elem);

        ensureCapacity(size + 1);
        int tail = (head + size) % elements.length;
        elements[tail] = elem;
        size++;
    }

    // Pre: elem != null
    // Post: n' = n + 1
    // a[0]' = elem && \forall i \in [0; n') :  a[i]' = a[i - 1]
    public static void push(Object elem) {
        Objects.requireNonNull(elem);

        ensureCapacity(size + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = elem;
        size++;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            int tail = (head + size) % elements.length;
            Object[] obj = new Object[2 * capacity];

            if (head < tail || size == 0) {
                System.arraycopy(elements, head, obj, 0, size);
            } else {
                System.arraycopy(elements, head, obj, 0, elements.length - head);
                System.arraycopy(elements, 0, obj, elements.length - head, tail);
            }

            head = 0;
            elements = obj;
        }
    }

    // Pre: n > 0
    // Post: R == a[0] && n' = n
    // && \forall i \in [0; n) :  a[i]' = a[i]
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // Pre: n > 0
    // Post: R == a[n - 1] && n' == n
    // && \forall i \in [0; n) :  a[i]' = a[i]
    public static Object peek() {
        assert size > 0;
        return elements[(head + size - 1) % elements.length];
    }

    // Post: R == n && n' = n &&
    // \forall i \in [0; n) :  a[i]' = a[i]
    public static int size() {
        return size;
    }


    // Post: R == (n == 0) && n' = n
    // && \forall i \in [0; n) :  a[i]' = a[i]
    public static boolean isEmpty() {
        return size == 0;
    }

    // Post: n' = 0
    // && \forall elem \in a :  elem == null
    public static void clear() {
        elements = new Object[1];
        head = 0;
        size = 0;
    }

    // Post: R == a && n' = n
    // && \forall i \in [0; n) :  a[i]' = a[i]
    public static Object[] toArray() {
        Object[] temp = new Object[size];
        if (size == 0) return temp;
        int tail = (head + size) % elements.length;
        int len = (tail > head ? tail - head : elements.length - head);
        System.arraycopy(elements, head, temp, 0, len);
        System.arraycopy(elements, 0, temp, len, tail > head ? 0 : tail);
        return temp;
    }

    // Post: temp == String('[')
    // for elem in a :
    //      if (elem is not last) temp += elem + " ,"
    //         else temp += elem + ']'
    // R == temp
    // && n' = n && \forall i \in [0; n) :  a[i]' = a[i]
    public static String toStr() {
        return Arrays.toString(toArray());
    }
}
