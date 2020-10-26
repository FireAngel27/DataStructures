package list;

import java.io.Serializable;
import java.util.Iterator;

/******************************************************************************
 * Class of LinkedList.
 *
 *
 * @author  Peter SKUHRA
 * @version 1.0 — 2020-10-07
 */
public class LinkedList<E> extends AList<E>
        implements IList<E>, IDeque<E>, Cloneable, Serializable {


    //*************************************************************************
    //== VARIABLE INSTANCE ATTRIBUTES =========================================
    //*************************************************************************

    /**
     *
     */
    private int size;

    /**
     *
     */
    private Node<E> firstNode;

    /**
     *
     */
    private Node<E> lastNode;


    //*************************************************************************
    //== CONSTRUCTORS AND FACTORY METHODS =====================================
    //*************************************************************************

    /**
     *
     */
    public LinkedList() {
        this.size = 0;
        this.firstNode = null;
        this.lastNode = null;
    }


    //*************************************************************************
    //== PUBLIC INSTANCE METHODS ==============================================
    //*************************************************************************

    //****************** Implementation of List interface *******************//

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean add(E item) {
        this.addLast(item);
        return true;
    }

    /**
     *
     * @param index
     * @param item
     */
    @Override
    public void add(int index, E item) {
        this.checkRangeForAdd(index);

        if (index == 0) {
            this.addFirst(item);
            return;
        }

        if (index == this.size) {
            this.addLast(item);
            return;
        }

        final Node<E> newNode = new Node<>(item);
        Node<E> currentNode = this.getNode(index);

        newNode.next = currentNode;
        newNode.prev = currentNode.prev;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;

        ++this.size;
    }

    /**
     *
     */
    @Override
    public void clear() {
        Node<E> currentNode = this.firstNode;
        Node<E> nextNode = null;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.item = null;
            currentNode.prev = null;
            currentNode.next = null;
            currentNode = nextNode;
        }

        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
    }

    /**
     *
     * @return
     */
    @Override
    public Object clone() {
        LinkedList<E> clone = null;

        try {
            clone = (LinkedList<E>) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }

        clone.clear();

        for (Node<E> node = this.firstNode; node != null; node = node.next) {
            clone.add(node.item);
        }

        return clone;
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        return this.getNode(index).item;
    }

    /**
     *
     * @return
     */
    @Override
    public Iterator<E> descendingIterator() {
        // TODO
        return null;
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public E remove(int index) {
        return this.removeNode(this.getNode(index));
    }

    /**
     *
     * @param index
     * @param item
     * @return
     */
    @Override
    public E set(int index, E item) {
        Node<E> node = this.getNode(index);
        final E oldItem = node.item;

        node.item = item;

        return oldItem;
    }

    /**
     *
     * @return
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    @Override
    public IList<E> subList(int fromIndex, int toIndex) {
        this.checkRange(fromIndex);
        this.checkRange(toIndex);

        if (fromIndex > toIndex) {
            String msg = "fromIndex can not be bigger than toIndex!";
            throw new IndexOutOfBoundsException(msg);
        }

        IList<E> list = new LinkedList<>();

        Node<E> startNode = this.getNode(fromIndex);
        Node<E> endNode = this.getNode(toIndex);

        for (Node<E> node = startNode; node != endNode; node = node.next) {
            list.add(node.item);
        }

        return list;
    }


    //***************** Implementation of Deque interface *****************//

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean offerFirst(E item) {
        this.addFirst(item);
        return true;
    }

    @Override
    public boolean offerLast(E item) {
        this.addLast(item);
        return true;
    }

    @Override
    public boolean offer(E item) {
        this.addLast(item);
        return true;
    }

    @Override
    public E remove() {
        return this.removeNode(this.firstNode);
    }

    @Override
    public E poll() {
        return this.removeNode(this.firstNode);
    }

    @Override
    public E element() {
        return this.firstNode.item;
    }

    @Override
    public E peek() {
        return this.firstNode.item;
    }

    @Override
    public void push(E item) {
        this.addFirst(item);
    }

    @Override
    public E pop() {
        return this.removeNode(this.firstNode);
    }

    @Override
    public E pollFirst() {
        return this.removeNode(this.firstNode);
    }

    @Override
    public E pollLast() {
        return this.removeNode(this.lastNode);
    }

    @Override
    public E getFirst() {
        return this.firstNode.item;
    }

    @Override
    public E getLast() {
        return this.lastNode.item;
    }

    @Override
    public E peekFirst() {
        return this.firstNode.item;
    }

    @Override
    public E peekLast() {
        return this.lastNode.item;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return this.remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        for (Node<E> node = this.lastNode; node != null; node = node.prev) {
            if (o.equals(node.item)) {
                this.removeNode(node);
                return true;
            }
        }

        return false;
    }


    public void addFirst(E item) {
        Node<E> newNode = new Node<>(item);
        Node<E> oldFirstNode = this.firstNode;

        if (oldFirstNode == null) {
            this.lastNode = newNode;
        }
        else {
            newNode.next = oldFirstNode;
            oldFirstNode.prev = newNode;
        }

        this.firstNode = newNode;

        ++this.size;
    }

    public void addLast(E item) {
        Node<E> newNode = new Node<>(item);
        Node<E> oldLastNode = this.lastNode;

        if (oldLastNode == null) {
            this.firstNode = newNode;
        }
        else {
            newNode.prev = oldLastNode;
            oldLastNode.next = newNode;
        }

        this.lastNode = newNode;

        ++this.size;
    }

    public E removeFirst() {
        return this.removeNode(this.firstNode);
    }

    public E removeLast() {
        return this.removeNode(this.lastNode);
    }


    //*************************************************************************
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ===============================
    //*************************************************************************

    /**
     *
     * @param index
     * @return
     */
    private boolean isCloserFromLeft(int index) {
        return (index < (this.size >> 1));
    }

    /**
     *
     * @param index
     * @return
     */
    private Node<E> getNode(int index) {
        this.checkRange(index);

        Node<E> currentNode = null;

        if (this.isCloserFromLeft(index)) {
            currentNode = this.firstNode;

            for (int i = 0; i < index; ++i) {
                currentNode = currentNode.next;
            }
        }
        else {
            currentNode = this.lastNode;

            for (int i = this.size - 1; i > index; --i) {
                currentNode = currentNode.prev;
            }
        }

        return currentNode;
    }

    /**
     *
     * @param nodeToRemove
     * @return
     */
    private E removeNode(Node<E> nodeToRemove) {
        Node<E> prevNode = nodeToRemove.prev;
        Node<E> nextNode = nodeToRemove.next;
        final E item = nodeToRemove.item;

        // first node to remove
        if (prevNode == null) {
            this.firstNode = nextNode;
            nextNode.prev = null;       // ???
        }
        else {
            prevNode.next = nextNode;
        }

        // last node to remove
        if (nextNode == null) {
            this.lastNode = prevNode;
            prevNode.next = null;       // ???
        }
        else {
            nextNode.prev = prevNode;
        }

        nodeToRemove.prev = null;
        nodeToRemove.next = null;
        nodeToRemove.item = null;

        --this.size;
        return item;
    }


    //*************************************************************************
    //== NESTED DATA TYPES ====================================================
    //*************************************************************************

    /**
     *
     *
     * @param <E>
     */
    private static class Node<E> {

        /**
         *
         */
        E item;

        /**
         *
         */
        Node<E> prev;

        /**
         *
         */
        Node<E> next;


        /**
         *
         * @param item
         */
        Node(E item) {
            this(item, null, null);
        }

        /**
         *
         * @param item
         * @param prevNode
         * @param nextNode
         */
        Node(E item, Node<E> prevNode, Node<E> nextNode) {
            this.item = item;
            this.prev = prevNode;
            this.next = nextNode;
        }
    }
}
