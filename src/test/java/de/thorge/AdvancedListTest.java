package de.thorge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdvancedListTest {
    private AdvancedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new AdvancedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    void map() {
        AdvancedList<String> mappedList = list.map(Object::toString);
        assertEquals("1", mappedList.get(0));
        assertEquals("2", mappedList.get(1));
        assertEquals("3", mappedList.get(2));
    }

    @Test
    void filter() {
        AdvancedList<Integer> filteredList = list.filter(i -> i % 2 == 0);
        assertEquals(1, filteredList.size());
        assertEquals(2, filteredList.get(0));
    }

    @Test
    void first() {
        AdvancedList<Integer> firstList = list.first(2);
        assertEquals(2, firstList.size());
        assertEquals(1, firstList.get(0));
        assertEquals(2, firstList.get(1));
    }

    @Test
    void last() {
        AdvancedList<Integer> lastList = list.last(2);
        assertEquals(2, lastList.size());
        assertEquals(2, lastList.get(0));
        assertEquals(3, lastList.get(1));
    }

    @Test
    void intSum() {
        assertEquals(6, list.intSum());
    }

    @Test
    void doubleSum() {
        assertEquals(6.0, list.doubleSum());
    }

    @Test
    void longSum() {
        assertEquals(6L, list.longSum());
    }
}