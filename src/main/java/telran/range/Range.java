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
        private Integer next_element = null;

        @Override
        public boolean hasNext() {
            boolean res = false;
            if (predicate == null) {
                this.next_element = this.current;
                res = this.next_element <= max;
            } else {
                int temp_сurrent = current;
                while(temp_сurrent <= max) {
                    if (predicate.test(temp_сurrent)) {
                        next_element = temp_сurrent;
                        res = true;
                        break;
                    }
                    temp_сurrent++;
                }
            }

            return res;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.current = next_element;
            next_element = null;
            return this.current++;
        }
    }
}
