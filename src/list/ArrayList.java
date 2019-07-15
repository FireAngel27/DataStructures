package list;

import java.util.Iterator;
import java.util.function.Consumer;

public class ArrayList<T> implements IList<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private final int EXPAND_COEFFICIENT = 2;

    private int size_;

    private T[] array_;


    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        size_ = 0;
        array_ = (T[]) new Object[capacity];
    }

    @Override
    public boolean add(T item) {
        ensureCapacity(size_ + 1);
        array_[++size_] = item;

        return true;
    }

    @Override
    public boolean add(int index, T item) {
        ensureCapacity(size_ + 1);
        checkRange(index);

        System.arraycopy(
                array_,
                index,
                array_,
                index + 1,
                size_ - index);
        array_[index] = item;
        ++size_;

        return true;
    }

    @Override
    public void clear() {
        size_ = 0;
    }

    @Override
    public T get(int index) {
        checkRange(index);
        return array_[index];
    }

    @Override
    public T set(int index, T item) {
        checkRange(index);

        T oldItem = array_[index];

        array_[index] = item;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        T oldItem = array_[index];

        System.arraycopy(
                array_,
                index + 1,
                array_,
                index,
                size_ - index - 1);
        --size_;

        return oldItem;
    }

    @Override
    public boolean remove(T item) {
        for (int i = 0; i < size_; ++i) {
            if (item.equals(array_[i])) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean contains(T item) {
        return indexOf(item) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return size_ == 0;
    }

    @Override
    public int size() {
        return size_;
    }

    @Override
    public int indexOf(T item) {
        for (int i = 0; i < size_; ++i) {
            if (item.equals(array_[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
        // TODO
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        // TODO
    }

    public void ensureCapacity(int capacity) {
        if (capacity <= array_.length) {
            return;
        }

        changeCapacity(Math.max(array_.length * EXPAND_COEFFICIENT, capacity));
    }

    public void shrinkToFit() {
        if (size_ == array_.length) {
            return;
        }

        changeCapacity(size_);
    }

    private void checkRange(int index) {
        if ((index < 0) || (index >= size_)) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
    }

    private void changeCapacity(int capacity) {
        T[] newArray = (T[]) new Object[capacity];

        System.arraycopy(array_, 0, newArray, 0, size_);
        array_ = newArray;
    }
}
