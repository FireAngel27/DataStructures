package list;

import java.util.Collection;

public interface IStack<E> extends Collection<E> {

    boolean empty();

    E peek();

    E pop();

    E push(E item);

    int search(Object o);
}
