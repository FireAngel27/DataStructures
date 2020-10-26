package collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/******************************************************************************
 * Comment class...
 *
 *
 * @author  Peter SKUHRA
 * @version 1.0 — 2020-10-09
 */
public abstract class ACollection<E> implements Collection<E> {


    //*************************************************************************
    //== CONSTRUCTORS AND FACTORY METHODS =====================================
    //*************************************************************************

    /**
     * Default constructor.
     */
    protected ACollection() {
    }


    //*************************************************************************
    //== ABSTRACT METHODS =====================================================
    //*************************************************************************

    /**
     *
     *
     * @param e
     * @return
     */
    @Override
    public abstract boolean add(E e);

    /**
     *
     *
     * @return
     */
    @Override
    public abstract Iterator<E> iterator();

    /**
     *
     *
     * @return
     */
    @Override
    public abstract int size();


    //*************************************************************************
    //== PUBLIC INSTANCE METHODS ==============================================
    //*************************************************************************

    /**
     *
     *
     * @param collection
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
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
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    /**
     *
     *
     * @param o
     * @return
     */
    @Override
    public boolean contains(Object o) {
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            if (o.equals(iterator.next())) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     *
     * @param collection
     * @return
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }



    /**
     *
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            if (o.equals(iterator.next())) {
                iterator.remove();
                return true;
            }
        }

        return false;
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
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            if (collection.contains(iterator.next())) {
                iterator.remove();
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
        boolean modified = false;
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            if (!collection.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }

        return modified;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        Iterator<E> iterator = iterator();

        for (int i = 0; i < array.length; ++i) {
            if (iterator.hasNext()) {
                array[i] = iterator.next();
            }
            else {
                return Arrays.copyOf(array, i);
            }
        }

        return array;
    }

    /**
     *
     *
     * @param ts
     * @param <T>
     * @return
     */
    @Override
    public <T> T[] toArray(T[] ts) {
        T[] array = null;

        if (ts.length < size()) {
            array = Arrays.copyOf(ts, size());
        }
        else {
            array = ts;
        }

        Iterator<E> iterator = iterator();

        for (int i = 0; i < array.length; ++i) {
            if (iterator.hasNext()) {
                array[i] = (T)iterator.next();
            }
        }

        return array;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String toString() {
        Iterator<E> iterator = iterator();

        if (!iterator.hasNext()) {
            return "[]";
        }

        String str = "[";

        while (iterator.hasNext()) {
            str += iterator.next().toString();

            if (iterator.hasNext()) {
                str += ", ";
            }
        }

        str += "]";

        return str;
    }
}
