package list;

/******************************************************************************
 * Class of ArrayList (resizable-array).
 * Implements IList interface.
 *
 * @author  Peter SKUHRA
 * @version 1.0 — 2020-10-05
 */
public class ArrayList<E> extends AList<E> implements IList<E> {


    //*************************************************************************
    //== CONSTANT CLASS ATTRIBUTES ============================================
    //*************************************************************************

    /**
     * Default capacity of ArrayList after initialization.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Expand coefficient for capacity changes.
     */
    private static final int EXPAND_COEFFICIENT = 2;


    //*************************************************************************
    //== VARIABLE INSTANCE ATTRIBUTES =========================================
    //*************************************************************************

    /**
     * Size of ArrayList.
     */
    private int size;

    /**
     * Array buffer with items.
     */
    private Object[] array;


    //*************************************************************************
    //== CONSTRUCTORS AND FACTORY METHODS =====================================
    //*************************************************************************

    /**
     * Default constructor.
     * Initializes ArrayList with default capacity - 10.
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor.
     * Initializes ArrayList with defined capacity.
     *
     * @param capacity  initial capacity of ArrayList
     */
    public ArrayList(int capacity) {
        this.size = 0;
        this.array = (E[]) new Object[capacity];
    }


    //*************************************************************************
    //== PUBLIC INSTANCE METHODS ==============================================
    //*************************************************************************

    /**
     * Inserts an item into the ArrayList.
     *
     * @param item  item to inserted into the ArrayList
     * @return      true after insertion
     */
    @Override
    public boolean add(E item) {
        this.ensureCapacity(this.size + 1);
        this.array[++this.size] = item;

        return true;
    }

    /**
     * Inserts an item into the ArrayList at the specified position.
     *
     * @param index     specified position
     * @param item      item to inserted into the ArrayList
     */
    @Override
    public void add(int index, E item) {
        this.checkRangeForAdd(index);
        this.ensureCapacity(this.size + 1);

        System.arraycopy(
                this.array,
                index,
                this.array,
                index + 1,
                this.size - index);

        this.array[index] = item;
        ++this.size;
    }

    /**
     * Returns an item at the specified position.
     *
     * @param index specified position of item
     * @return      item at the specified position
     */
    @Override
    public E get(int index) {
        this.checkRange(index);
        return (E) this.array[index];
    }

    /**
     * Sets an item at the specified position in ArrayList with the specified item.
     *
     * @param index specified position with item to be replace
     * @param item  specified item
     * @return      a replaced item
     */
    @Override
    public E set(int index, E item) {
        this.checkRange(index);

        E oldItem = (E) this.array[index];

        this.array[index] = item;
        return oldItem;
    }

    /**
     * Removes an item at the specified position in ArrayList.
     *
     * @param index specified position of the item to be remove
     * @return      removed item
     */
    @Override
    public E remove(int index) {
        this.checkRange(index);
        E oldItem = (E) this.array[index];

        System.arraycopy(
                this.array,
                index + 1,
                this.array,
                index,
                this.size - index - 1);

        --this.size;
        return oldItem;
    }

    /**
     * Returns the number of elements in ArrayList.
     *
     * @return the number of elements in ArrayList
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * // TODO
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    @Override
    public IList<E> subList(int fromIndex, int toIndex) {
        // TODO
        return null;
    }

    /**
     * Ensures (increase) capacity of ArrayList.
     *
     * @param capacity  the desired minimum capacity
     */
    public void ensureCapacity(int capacity) {
        if (capacity <= this.array.length) {
            return;
        }

        this.changeCapacity(
                Math.max(this.array.length * EXPAND_COEFFICIENT, capacity)
        );
    }

    /**
     * Trims the capacity of the ArrayList to the current size.
     */
    public void trimToSize() {
        if (this.size == this.array.length) {
            return;
        }

        this.changeCapacity(this.size);
    }


    //*************************************************************************
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ===============================
    //*************************************************************************

    /**
     * Changes capacity of ArrayList to the defined capacity.
     *
     * @param capacity  the desired capacity
     */
    private void changeCapacity(int capacity) {
        Object[] newArray = new Object[capacity];

        System.arraycopy(this.array, 0, newArray, 0, this.size);
        this.array = newArray;
    }
}
