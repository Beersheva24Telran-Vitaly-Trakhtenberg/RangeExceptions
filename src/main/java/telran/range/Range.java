package telran.range;

import telran.range.exceptions.OutOfRangeMaxValueException;
import telran.range.exceptions.OutOfRangeMinValueException;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class Range implements Iterable<Integer>
{
    private static final String ERROR_MESSAGE = "max less or equal min";
    private final int min;
    private final int max;
    private Predicate<Integer> predicate;

    private Range(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    public static Range getRange(int min, int max)
    {
        if (max <= min) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return new Range(min, max);
    }

    public void checkNumber(int number) throws OutOfRangeMaxValueException, OutOfRangeMinValueException
    {
        if(number > max) {
            throw new OutOfRangeMaxValueException(max, number);
        }
        if (number < min) {
            throw new OutOfRangeMinValueException(min, number);
        }
    }

    public void setPredicate(Predicate<Integer> predicate)
    {
        this.predicate = predicate != null ? predicate : x -> true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new RangeIterator();
    }

    private class RangeIterator implements Iterator<Integer>
    {
        private int current = min;

        private RangeIterator()
        {
            this.current = findNext(min);
        }

        @Override
        public boolean hasNext() {
            return this.current <= max;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int res = this.current;
            this.current = findNext(this.current+1);
            return res;
        }

        private int findNext(int val)
        {
            boolean flag_found = false;
            while (val <= max && !flag_found) {
                if (predicate == null || predicate.test(val)) {
                    break;
                }
                val++;
            }
            return val;
        }
    }
}
