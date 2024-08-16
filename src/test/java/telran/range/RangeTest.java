package telran.range;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import telran.range.exceptions.OutOfRangeMaxValueException;
import telran.range.exceptions.OutOfRangeMinValueException;

public class RangeTest
{
    private static final int MIN = 50;
    private static final int MAX = 100;
    Range range = Range.getRange(MIN, MAX);

    @Test
    void wrongRangeCreatingTest()
    {
        assertThrowsExactly(IllegalArgumentException.class, () -> Range.getRange(MAX, MIN) );
    }

    @Test
    void rightNumberTest() throws Exception
    {
        range.checkNumber(55);
    }

    @Test
    void wrongNumberTest() throws Exception
    {
        assertThrowsExactly(OutOfRangeMaxValueException.class,
                () -> range.checkNumber(MAX + 1));
        assertThrowsExactly(OutOfRangeMinValueException.class, () -> range.checkNumber(MIN - 1));
    }

    @Test
    void iteratorTest()
    {
        // Test case 1: No predicate (should iterate over all numbers)
        Range range = Range.getRange(1, 10);
        Iterator<Integer> iterator = range.iterator();
        int expected = 1;
        while (iterator.hasNext()) {
            assertEquals(expected++, iterator.next());
        }
        assertEquals(11, expected);

        // Test case 2: Predicate for even numbers
        Predicate<Integer> evenPredicate = x -> x % 2 == 0;
        range.setPredicate(evenPredicate);
        iterator = range.iterator();
        expected = 2;
        while (iterator.hasNext()) {
            assertEquals(expected, iterator.next());
            expected += 2;
        }
        assertEquals(12, expected);

        // Test case 3: Predicate for numbers divisible by 3
        Predicate<Integer> divisibleByThreePredicate = x -> x % 3 == 0;
        range.setPredicate(divisibleByThreePredicate);
        iterator = range.iterator();
        expected = 3;
        while (iterator.hasNext()) {
            assertEquals(expected, iterator.next());
            expected += 3;
        }
        assertEquals(12, expected);

        // Test case 4: No elements matching predicate
        Predicate<Integer> noMatchPredicate = x -> x > 10;
        range.setPredicate(noMatchPredicate);
        iterator = range.iterator();
        assertFalse(iterator.hasNext());

        // Test case 5: Null predicate (should iterate over all numbers)
        range.setPredicate(null);
        iterator = range.iterator();
        expected = 1;
        while (iterator.hasNext()) {
            assertEquals(expected++, iterator.next());
        }
        assertEquals(11, expected);
    }
}
