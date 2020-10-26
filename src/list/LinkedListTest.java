package list;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTest {

    final int ITEM_COUNT = 10000;

    final int COEF = 1;

    Integer[] array;

    LinkedList<Integer> linkedList;

    @Before
    public void setUp() throws Exception {
        linkedList = new LinkedList<>();

        array = new Integer[ITEM_COUNT];

        for (int i = 0; i < ITEM_COUNT; ++i) {
            array[i] = i * COEF;
        }
    }

    @Test
    public void add() {
        assertTrue("Failed - isEmpty()", linkedList.isEmpty());

        for (int i = 0; i < ITEM_COUNT; ++i) {
            linkedList.add(array[i]);
            assertEquals("Failed - get()!", array[i], linkedList.get(i));
            assertEquals("Failed - size()", i+1, linkedList.size());
            assertEquals("Failed - indexOf()", i, linkedList.indexOf(i));
            assertTrue("List must contain item!", linkedList.contains(array[i]));
        }

        assertFalse("List can not be empty!", linkedList.isEmpty());
        assertEquals("Size is not equal!", ITEM_COUNT, linkedList.size());

        linkedList.clear();
        assertTrue("List must be empty after clear method!",
                linkedList.isEmpty());

        assertEquals("Size is not 0 after clear method!",
                0,
                linkedList.size());
    }

    @Test
    public void addAll() {

    }

    @Test
    public void remove() {
    }

    @Test
    public void set() {
    }
}