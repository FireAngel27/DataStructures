package trees;

import list.IQueue;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/******************************************************************************
 *  Generic class of binary heap.
 *
 *  Implements IQueue interface.
 *
 * @param <E>   E must implement Comparable interface
 * @author      Peter Skuhra
 * @version     1.0 - 2020-10-05
 */
public class BinaryHeap<E extends Comparable<E>> implements IQueue<E> {


    //*************************************************************************
    //== CONSTANT CLASS ATTRIBUTES ============================================
    //*************************************************************************

    /** Default capacity of array for the heap */
    private static final int DEFAULT_CAPACITY = 10;

    /** Maximum capacity of array for the heap */
    private static final int MAX_HEAP_SIZE = Integer.MAX_VALUE - 8;


    //*************************************************************************
    //== VARIABLE INSTANCE ATTRIBUTES =========================================
    //*************************************************************************

    /** Size of the heap */
    private int size;

    /** Array capacity for the heap. */
    private int capacity;

    /** Array for the heap */
    private Object[] heap;


    //*************************************************************************
    //== CONSTRUCTORS AND FACTORY METHODS ======================================
    //*************************************************************************

    /**
     * Default constructor.
     * Initializes the heap with default capacity 10.
     */
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor.
     * Initializes the heap with specified capacity.
     *
     * @param capacity  initial capacity for heap
     */
    public BinaryHeap(int capacity) {
        this.size = 0;
        this.capacity = capacity;

        this.heap = new Object[this.capacity];
    }


    //*************************************************************************
    //== INSTANCE GETTERS AND SETTERS =========================================
    //*************************************************************************

    /**
     * Returns size of heap.
     *
     * @return  size of heap
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns true if heap is empty.
     *
     * @return true if heap is empty
     */
    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }


    //*************************************************************************
    //== PUBLIC INSTANCE METHODS ===================================
    //*************************************************************************

    /**
     * Returns true if heap contains specific element.
     *
     * @param o     specific element
     * @return      true if heap contains specific element
     */
    @Override
    public boolean contains(Object o) {
        return (this.indexOf(o) != -1);
    }

    /**
     * Returns iterator for heap.
     *
     * @return  iterator for heap
     */
    @Override
    public Iterator<E> iterator() {
        // TODO
        return null;
    }

    /**
     * Returns array of heap.
     *
     * @return array of heap
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.heap, this.size);
    }

    /**
     * //TODO
     *
     * @param ts
     * @param <T>
     * @return
     */
    @Override
    public <T> T[] toArray(T[] ts) {
        if (ts.length < this.size) {
            return (T[]) Arrays.copyOf(this.heap, this.size, ts.getClass());
        }

        System.arraycopy(this.heap, 0, ts, 0, this.size);

        if (ts.length > this.size) {
            ts[this.size] = null;
        }

        return ts;
    }

    /**
     * Inserts the specific element into this heap. After success returns true.
     *
     * @param e     specific element
     * @return      true after insert
     * @throws NullPointerException     if the specified element is null
     */
    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }

        this.ensureCapacity(this.size + 1);
        this.heap[this.size++] = e;

        this.updateHeap(this.size - 1);

        return true;
    }

    /**
     * Removes the specific element from this heap.
     * Returns true if element was successfully removed,
     * or false if the element is not in this heap.
     *
     * @param o     specific element to be removed from heap
     * @return      true if this heap changed (element removed)
     */
    @Override
    public boolean remove(Object o) {
        int index = this.indexOf(o);

        if (index < 0) {
            return false;
        }

        if (index == 0) {
            return (this.remove() != null) ? true : false;
        }

        this.heap[index] = this.heap[--this.size];
        this.heap[this.size] = null;

        this.updateHeap(index);

        return true;
    }

    /**
     * Returns true if the heap contains all elements
     * from the specified collection.
     *
     * @param collection    collection to be checked
     * @return              true if the heap contains all elements
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!this.contains(o)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds all of the elements in the specified collection to the heap.
     *
     * @param collection    collection with elements to be added to the heap
     * @return              true if the heap changed
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;

        for (E e : collection) {
            if (this.add(e)) {
                modified = true;
            }
        }

        return modified;
    }

    /**
     *
     *
     * @param collection
     * @return
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;

        if (collection.size() == 0) {
            return false;
        }

        for (Object o : collection) {
            if (remove(o)) {
                modified = true;
            }
        }

        return modified;
    }

    /**
     *
     *
     * @param collection
     * @return
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        // TODO
        return false;
    }

    /**
     * Removes all of the elements from this heap.
     * The heap will be empty after call this method.
     */
    @Override
    public void clear() {
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = null;
        }

        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Inserts the specific element into this heap. After success returns true.
     *
     * @param e     specific element
     * @return      true after insert
     * @throws NullPointerException     if the specified element is null
     */
    @Override
    public boolean offer(E e) {
        return this.add(e);
    }

    /**
     * Removes and returns the root of this heap.
     *
     * @return  root of this heap
     */
    @Override
    public E remove() {
        if (this.isEmpty()) {
            return null;
        }

        E max = this.root();
        this.heap[0] = this.heap[--this.size];
        this.heap[this.size] = null;

        this.updateHeap(0);

        return max;
    }

    /**
     * Removes and returns the root of this heap.
     *
     * @return  root of this heap
     */
    @Override
    public E poll() {
        return this.remove();
    }

    /**
     * Returns, but does not remove, the root of this heap.
     *
     * @return  root of this heap
     */
    @Override
    public E element() {
        return this.root();
    }

    /**
     * Returns, but does not remove, the root of this heap.
     *
     * @return  root of this heap
     */
    @Override
    public E peek() {
        return this.root();
    }


    //*************************************************************************
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ===============================
    //*************************************************************************

    /**
     * Returns the index of the specific element if it is in this heap.
     *
     * @param o     specific element
     * @return      index of specific element in this heap
     */
    private int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < this.size; ++i) {
                if (o.equals(this.heap[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * Ensures minimal capacity of array for heap.
     *
     * @param minCapacity       minimal capacity to be ensure
     * @throws OutOfMemoryError if not enough memory, or minCapacity is negative
     */
    private void ensureCapacity(int minCapacity) {
        if ((minCapacity > MAX_HEAP_SIZE) || (minCapacity < 0)) {
            throw new OutOfMemoryError();
        }

        if (minCapacity < this.capacity) {
            return;
        }

        int newCapacity = Math.max(
                this.capacity + (this.capacity >> 1),
                minCapacity);

        if (newCapacity > MAX_HEAP_SIZE) {
            newCapacity = minCapacity;
        }

        this.increaseCapacity(newCapacity);
    }

    /**
     * Increase capacity of array for this heap.
     *
     * @param capacity  desired capacity (must be greater than size of heap)
     * @throws InvalidParameterException if capacity is smaller/equal than size
     */
    private void increaseCapacity(int capacity) {
        if (capacity <= this.size) {
            throw new InvalidParameterException(
                    "Capacity must be greater than size");
        }

        Object[] newArray = new Object[capacity];
        this.capacity = capacity;

        System.arraycopy(this.heap, 0, newArray, 0, this.size);
        this.heap = newArray;
    }

    /**
     * Checks if the specific index is in allowed range.
     *
     * @param index     specific index to be check
     * @throws IndexOutOfBoundsException if index is out of range <0, size)
     */
    private void checkRange(int index) {
        if ((index < 0) || (index >= this.size)) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
    }

    /**
     * Updates the properties of this heap from the specified index.
     *
     * @param index     specified index of array this heap
     */
    private void updateHeap(int index) {
        if (this.isEmpty()) {
            return;
        }

        if (this.isParentSmaller(index)) {
            this.updateParent(index);
        }
        else {
            this.updateChildren(index);
        }
    }

    /**
     * Updates the heap towards the parent.
     *
     * @param index     specified index of array this heap
     */
    private void updateParent(int index) {
        while (!this.isRoot(index) && this.isParentSmaller(index)) {
            final int parentIndex = this.parentIndex(index);
            this.swap(index, parentIndex);
            index = parentIndex;
        }
    }

    /**
     * Updates the heap towards the children.
     *
     * @param parentIndex   specified index of array this heap
     */
    private void updateChildren(int parentIndex) {
        while (!this.isLeaf(parentIndex) && this.isChildBigger(parentIndex)) {
            final int maxChildIndex = this.maxChildIndex(parentIndex);
            this.swap(parentIndex, maxChildIndex);
            parentIndex = maxChildIndex;
        }
    }

    /**
     * Returns value of root in heap.
     *
     * @return  value of root in heap
     */
    private E root() {
        if (isEmpty()) {
            return null;
        }

        return (E) this.heap[0];
    }

    /**
     * Returns last element of heap (last leaf).
     *
     * @return  last element of heap
     */
    private E lastLeaf() {
        return (E) this.heap[this.size - 1];
    }

    /**
     * Returns index of parent.
     *
     * @param index     index of child
     * @return          index of parent
     */
    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    /**
     * Returns index of left child.
     *
     * @param index     index of some parent
     * @return          index of its left child
     */
    private int leftChildIndex(int index) {
        return (2 * index) + 1;
    }

    /**
     * Returns index of right child.
     *
     * @param index     index of some parent
     * @return          index of its right child
     */
    private int rightChildIndex(int index) {
        return (2 * index) + 2;
    }

    /**
     * Returns index of greater child.
     *
     * @param parentIndex   index of some parent
     * @return              index of its greater child
     *                      (returns the same index if node has no children)
     */
    private int maxChildIndex(int parentIndex) {

        switch (this.childCount(parentIndex)) {
            case 1:
                return this.leftChildIndex(parentIndex);

            case 2:
                E leftChild = (E) this.heap[this.leftChildIndex(parentIndex)];
                E rightChild = (E) this.heap[this.rightChildIndex(parentIndex)];

                if (rightChild.compareTo(leftChild) > 0) {
                    return this.rightChildIndex(parentIndex);
                }
                else {
                    return this.leftChildIndex(parentIndex);
                }

            default:
                return parentIndex;
        }
    }

    /**
     * Returns the number of the parent's children.
     *
     * @param parentIndex   index of some parent
     * @return              0, 1, or 2
     */
    private int childCount(int parentIndex) {
        int childCount = 0;

        if (this.leftChildIndex(parentIndex) < this.size) {
            ++childCount;
        }

        if (this.rightChildIndex(parentIndex) < this.size) {
            ++childCount;
        }

        return childCount;
    }

    /**
     * Swap value in this heap between specified index a and b.
     *
     * @param a     index a
     * @param b     index b
     * @throws IndexOutOfBoundsException if any index is out of allowed range
     */
    private void swap(int a, int b) {
        if (a == b) {
            return;
        }

        this.checkRange(a);
        this.checkRange(b);

        Object tmp = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = tmp;
    }

    /**
     * Returns true if specified index is index of root (if index == 0).
     *
     * @param index     node index
     * @return          true if index == 0
     */
    private boolean isRoot(int index) {
        return (index == 0);
    }

    /**
     * Returns true if specified index is index of leaf (has no children).
     *
     * @param index     node index
     * @return          true if specified index is index of leaf
     */
    private boolean isLeaf(int index) {
        return (this.childCount(index) == 0);
    }

    /**
     * Returns true if specified index is child of some parent (not root).
     *
     * @param index     index of any child
     * @return          true if index != 0
     */
    private boolean hasParent(int index) {
        return !isRoot(index);
    }

    /**
     * Returns true if the specified index belongs to a parent
     * who has at least one child.
     *
     * @param index     node index
     * @return          true if child count of node is greater than 0
     */
    private boolean hasChild(int index) {
        return (this.childCount(index) > 0);
    }

    /**
     * Returns true if the specified node index has smaller parent.
     *
     * @param index     node index
     * @return          true if parent is smaller
     * @throws IndexOutOfBoundsException if index is out of allowed range
     */
    private boolean isParentSmaller(int index) {
        this.checkRange(index);

        if (this.isRoot(index)) {
            return false;
        }

        final E currentItem = (E) this.heap[index];
        final E parentItem = (E) this.heap[this.parentIndex(index)];

        return (currentItem.compareTo(parentItem) > 0);
    }

    /**
     * Returns true if the specified node index has bigger child.
     *
     * @param parentIndex   index of some parent
     * @return              true if parent has bigger child,
     *                      or false if has no child
     * @throws IndexOutOfBoundsException if index is out of allowed range
     */
    private boolean isChildBigger(int parentIndex) {
        this.checkRange(parentIndex);

        int maxChildIndex = this.maxChildIndex(parentIndex);
        if (maxChildIndex >= this.size) {
            return false;
        }

        final E currentItem = (E) this.heap[parentIndex];
        final E childItem = (E) this.heap[maxChildIndex];

        return (currentItem.compareTo(childItem) < 0);
    }

    //*************************************************************************
    //== NESTED DATA TYPES ====================================================
    //*************************************************************************

    // TODO Iterator
}
