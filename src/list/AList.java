package list;

import collection.ACollection;

import java.util.*;

/******************************************************************************
 * Abstract class of List.
 *
 * @author Peter SKUHRA
 * @version 1.0 — 2020-10-10
 */
public abstract class AList<E> extends ACollection<E> implements IList<E> {

    //*************************************************************************
    //== CONSTRUCTORS AND FACTORY METHODS =====================================
    //*************************************************************************

    /**
     *
     */
    protected AList() {
    }


    //*************************************************************************
    //== ABSTRACT METHODS =====================================================
    //*************************************************************************

    /**
     * @param index
     * @param element
     */
    abstract public void add(int index, E element);

    /**
     * @param index
     * @return
     */
    abstract public E get(int index);

    /**
     * @param index
     * @param element
     * @return
     */
    abstract public E set(int index, E element);

    /**
     * @param index
     * @return
     */
    abstract public E remove(int index);


    //*************************************************************************
    //== PUBLIC INSTANCE METHODS ==============================================
    //*************************************************************************

    /**
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    /**
     * @param collection
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return addAll(size(), collection);
    }

    /**
     * @param index
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkRangeForAdd(index);

        boolean modified = false;

        for (E e : c) {
            add(index++, e);
            modified = true;
        }

        return modified;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public int indexOf(Object o) {
        ListIterator<E> iterator = listIterator();

        while (iterator.hasNext()) {
            if (o.equals(iterator.next())) {
                return iterator.previousIndex();
            }
        }

        return -1;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        ListIterator<E> listIterator = listIterator(size());

        while (listIterator.hasPrevious()) {
            if (o.equals(listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }

        return -1;
    }

    /**
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * @return
     */
    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    /**
     * @param index
     * @return
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        checkRange(index);
        return new ListItr(index);
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof IList)) {
            return false;
        }

        if (size() != ((IList<?>) o).size()) {
            return false;
        }

        Iterator<E> iterator1 = iterator();
        Iterator<?> iterator2 = ((IList<?>) o).iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            if (!iterator1.next().equals(iterator2.next())) {
                return false;
            }
        }

        return !(iterator1.hasNext() || iterator2.hasNext());   // NOR
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash *= size();

        for (E e : this) {
            hash *= 23 + e.hashCode();
        }

        return hash;
    }


    //*************************************************************************
    //== PROTECTED AND AUXILIARY INSTANCE METHODS =============================
    //*************************************************************************

    /**
     * @param index
     */
    protected void checkRange(int index) {
        if ((index < 0) || (index >= size())) {
            throw new IndexOutOfBoundsException(outOfRangeMsg(index));
        }
    }

    /**
     * @param index
     */
    protected void checkRangeForAdd(int index) {
        if ((index < 0) || (index > size())) {
            throw new IndexOutOfBoundsException(outOfRangeMsg(index));
        }
    }

    /**
     * @param index
     * @return
     */
    protected String outOfRangeMsg(int index) {
        String msg = "Index out of range!";
        msg += " (Index: " + index + ", Size: " + size() + ")";

        return msg;
    }


    //*************************************************************************
    //== NESTED DATA TYPES ====================================================
    //*************************************************************************

    /**
     *
     */
    private class Itr implements Iterator<E> {

        /**
         *
         */
        protected final int NO_RETURNS = -1;

        /**
         *
         */
        protected int cursor;

        /**
         *
         */
        protected int lastReturned;

        /**
         *
         */
        protected boolean isReturned;


        /**
         *
         */
        public Itr() {
            cursor = 0;
            lastReturned = NO_RETURNS;
            isReturned = false;
        }

        /**
         * @return
         */
        @Override
        public boolean hasNext() {
            return (cursor != size());
        }

        /**
         * @return
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            try {
                E next = get(cursor);

                lastReturned = cursor;
                isReturned = true;
                ++cursor;

                return next;
            }
            catch (IndexOutOfBoundsException exception) {
                throw new NoSuchElementException();
            }
        }

        /**
         *
         */
        @Override
        public void remove() {
            if (!isReturned) {
                throw new IllegalStateException();
            }

            try {
                AList.this.remove(lastReturned);

                // if the last move was "next()"
                if (lastReturned < cursor) {
                    --cursor;
                }

                lastReturned = NO_RETURNS;
                isReturned = false;
            }
            catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }
    }


    /**
     *
     */
    private class ListItr extends Itr implements ListIterator<E> {

        /**
         * @param index
         */
        public ListItr(int index) {
            cursor = index;
            lastReturned = NO_RETURNS;
            isReturned = false;
        }

        /**
         * @return
         */
        @Override
        public boolean hasPrevious() {
            return (cursor > 0);
        }

        /**
         * @return
         */
        @Override
        public E previous() {
            try {
                E previous = get(cursor - 1);

                --cursor;
                lastReturned = cursor;
                isReturned = true;

                return previous;
            }
            catch (IndexOutOfBoundsException exception) {
                throw new NoSuchElementException();
            }
        }

        /**
         * @return
         */
        @Override
        public int nextIndex() {
            return cursor;
        }

        /**
         * @return
         */
        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        /**
         * @param e
         */
        @Override
        public void set(E e) {
            if (!isReturned) {
                throw new IllegalStateException();
            }

            try {
                AList.this.set(lastReturned, e);
            }
            catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * @param e
         */
        @Override
        public void add(E e) {
            try {
                AList.this.add(cursor, e);

                ++cursor;
                lastReturned = -1;
                isReturned = false;
            }
            catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
