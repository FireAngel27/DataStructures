package trees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BinaryHeapTest {

    private final static int ITEM_COUNT = 100;

    private BinaryHeap<Integer> heap;

    private List<Integer> list;


    @Before
    public void setUp() throws Exception {
        this.heap = new BinaryHeap<>(ITEM_COUNT);
        this.list = new ArrayList<>(ITEM_COUNT);

        for (int i = 0; i < ITEM_COUNT; ++i) {
            list.add(i);
            assertTrue(heap.add(i));
        }
    }

    @After
    public void tearDown() throws Exception {
        list.clear();
        heap.clear();
    }



    @Test
    public void add() {
        assertTrue(heap.add(ITEM_COUNT + 1));
    }

    @Test (expected = NullPointerException.class)
    public void addException() {
        heap.add(null);
    }

    @Test
    public void isEmpty() {
        heap.add(ITEM_COUNT);
        assertFalse(heap.isEmpty());

        heap.clear();
        assertTrue(heap.isEmpty());
    }

    @Test
    public void size() {
        assertEquals(this.list.size(), this.heap.size());

        heap.clear();
        assertNotEquals(this.list.size(), this.heap.size());

        list.clear();
        assertEquals(this.list.size(), this.heap.size());
    }

    @Test
    public void toArray() {
        Object[] array = heap.toArray();

        for (Object o : array) {
            System.out.print(o + ", ");
        }
    }

    @Test
    public void remove() {
        for (int i = list.size() - 1; i >= 0; --i) {
            Integer removed = heap.remove();
            assertEquals(removed, list.get(i));
            System.out.print(removed + ", ");
        }

        assertTrue(heap.isEmpty());
    }

    @Test
    public void removeEmptyHeap() {
        heap.add(1);
        assertNotNull(heap.remove());

        heap.clear();
        assertEquals(null, heap.remove());
    }

    @Test
    public void removeSpecificItem() {
        assertFalse(heap.remove(200));
        assertFalse(heap.remove(null));

        assertTrue(heap.add(150));
        assertTrue(heap.remove(150));
    }

    @Test
    public void clear() {
        heap.clear();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
    }

    @Test
    public void contains() {
        heap.add(102);

        assertTrue(heap.contains(102));
    }

    @Test
    public void offer() {
        assertTrue(heap.offer(ITEM_COUNT));
    }

    @Test
    public void poll() {
        for (int i = list.size() - 1; i >= 0; --i) {
            Integer removed = heap.poll();
            assertEquals(removed, list.get(i));
            System.out.print(removed + ", ");
        }

        assertNull(heap.poll());
    }

    @Test
    public void element() {
        final Integer num = Integer.MAX_VALUE - 256;
        heap.add(num);

        assertEquals(num, heap.element());
    }

    @Test
    public void peek() {
        final Integer num = Integer.MAX_VALUE - 512;
        heap.add(num);

        assertEquals(num, heap.peek());
    }
}