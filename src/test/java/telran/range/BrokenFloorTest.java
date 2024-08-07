package telran.range;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BrokenFloorTest
{

    private int getMinimalBrokenFloor(BallBrokenFloor bbf)
    {
        int low_floor = 1;
        int high_floor = Integer.MAX_VALUE;
        int result = -1;
        while (low_floor <= high_floor) {
            int middle = (low_floor + high_floor) / 2;
            try {
                bbf.checkFloor(middle);
                low_floor = middle + 1;
            } catch (Exception e) {
                result = middle;
                high_floor = middle - 1;
            }
        }
        return result;
    }

    @Test
    void minimalBrokenFloorTest()
    {
        int [] floors = {200, 17, 1001, 2000};
        for(int i = 0; i < floors.length; i++) {
            BallBrokenFloor bbf = new BallBrokenFloor(floors[i]);
            assertEquals(bbf.getMinBrokenFloor(), getMinimalBrokenFloor(bbf));
        }
    }
}