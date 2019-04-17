package index;

public interface Index<E> {
    boolean isEmpty();

    int size();

    void insert(E e);

    boolean contains(E e);

    E nearest(E e);
}
