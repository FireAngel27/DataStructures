package list;

import java.util.Collection;

public interface IQueue<E> extends Collection<E> {

    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}
