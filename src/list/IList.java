package list;

public interface IList<T> extends Iterable<T> {

    boolean add(T item);

    boolean add(int index, T item);

    void clear();

    T get(int index);

    T set(int index, T item);

    T remove(int index);

    boolean remove(T item);

    boolean contains(T item);

    boolean isEmpty();

    int size();

    int indexOf(T item);
}
