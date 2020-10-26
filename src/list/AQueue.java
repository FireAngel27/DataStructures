package list;

import collection.ACollection;

import java.util.Collection;
import java.util.NoSuchElementException;

/******************************************************************************
 * Abstract class of Queue.
 *
 * @param <E>
 * @author Peter Skuhra
 * @version 0.9 - 2020-10-26
 */
public abstract class AQueue<E> extends ACollection<E> implements IQueue<E> {

    /**
     *
     */
    protected AQueue() {

    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        if (offer(e)) {
            return true;
        }
        else {
            throw new IllegalStateException("Full queue");
        }
    }

    /**
     *
     * @param collection
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }

        if (collection == this) {
            throw new IllegalArgumentException();
        }

        boolean modified = false;

        for (E e : collection) {
            if (add(e)) {
                modified = true;
            }
        }

        return modified;
    }

    /**
     *
     */
    @Override
    public void clear() {
        while (poll() != null);
    }

    /**
     *
     * @return
     */
    @Override
    public E remove() {
        E item = poll();

        if (item != null) {
            return item;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public E element() {
        E item = peek();

        if (item != null) {
            return item;
        }
        else {
            throw new NoSuchElementException();
        }
    }
}
