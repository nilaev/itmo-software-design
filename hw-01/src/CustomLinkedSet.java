import java.util.HashMap;
import java.util.NoSuchElementException;

public class CustomLinkedSet<E> {

    private final HashMap<E, Node<E>> map = new HashMap<>();
    private Node<E> first = null;
    private Node<E> last = null;

    public boolean contains(E elem) {
        return map.containsKey(elem);
    }

    public int size() {
        return map.size();
    }

    public boolean addFirst(E elem) {
        if (this.contains(elem)) {
            return false;
        } else {
            Node<E> newFirst = new Node<>(elem);
            newFirst.next = this.first;
            if (size() == 0) {
                this.last = newFirst;
            } else {
                this.first.prev = newFirst;
            }
            this.first = newFirst;
            map.put(elem, newFirst);
            assert nonEmptySizeAssertion();
            assert firstAndLastAssertion();
            return true;
        }
    }

    public E removeLast() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Removing last element from an empty set");
        } else {
            E elem = this.last.elem;
            this.last = this.last.prev;
            map.remove(elem);
            switch (this.size()) {
                case 1 -> {
                    this.last.next = null;
                    this.first = this.last;
                }
                case 0 -> {
                    this.first = null;
                    this.last = null;
                }
                default -> this.last.next = null;
            }
            assert sizeAssertion();
            assert firstAndLastAssertion();
            return elem;
        }
    }

    public boolean remove(E elem) {
        if (!this.contains(elem)) {
            return false;
        } else {
            Node<E> node = map.get(elem);
            assert node != null;
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                this.last = node.prev;
            }
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                this.first = node.next;
            }
            map.remove(elem);
            assert sizeAssertion();
            assert firstAndLastAssertion();
            return true;
        }
    }

    private boolean sizeAssertion() {
        return first == null && last == null && size() == 0     // empty
                || nonEmptySizeAssertion();                     // non-empty
    }

    private boolean nonEmptySizeAssertion() {
        return first != null && last != null
                && (first == last && size() == 1)      // single elem
                || (first != last && size() > 1);      // >1 elements
    }

    private boolean firstAndLastAssertion() {
        return map.size() == 0 || (first.prev == null && last.next == null);
    }

    private static class Node<E> {
        private final E elem;
        private Node<E> prev = null;
        private Node<E> next = null;
        private Node(E elem) {
            this.elem = elem;
        }
    }
}
