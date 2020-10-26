package list;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/******************************************************************************
 * List interface.
 *
 * @author  Peter SKUHRA
 * @version 1.0 — 2020-10-05
 */
public interface IList<E> extends Collection<E> {

    boolean add(E e);

    void add(int index, E element);

    boolean addAll(Collection<? extends E> c);

    boolean addAll(int index, Collection<? extends E> c);

    void clear();

    boolean contains(Object o);

    boolean containsAll(Collection<?> c);

    boolean equals(Object o);

    E get(int index);

    int hashCode();

    int indexOf(Object o);

    boolean isEmpty();

    int lastIndexOf(Object o);

    boolean remove(Object o);

    E remove(int index);

    boolean removeAll(Collection<?> c);

    boolean retainAll(Collection<?> c);

    E set(int index, E element);

    int size();

    IList<E> subList(int fromIndex, int toIndex);

    Object[] toArray();

    <T> T[] toArray(T[] a);


    Iterator<E> iterator();

    ListIterator<E> listIterator();

    ListIterator<E> listIterator(int index);

}
