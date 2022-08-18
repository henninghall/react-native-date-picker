
import com.henninghall.date_picker.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShortestScrollOption {

    @Test
    public void decreaseOne() {
        assertEquals( -1, Utils.getShortestScrollOption(1, 0, 10, false));
    }

    @Test
    public void increaseOne() {
        assertEquals(1, Utils.getShortestScrollOption(0, 1, 10, false));
    }

    @Test
    public void increaseFive() {
        assertEquals( 5, Utils.getShortestScrollOption(0, 5, 10, false));
    }

    @Test
    public void noChange() {
        assertEquals( 0, Utils.getShortestScrollOption(0, 0, 10, false));
    }

    @Test
    public void noWrapping() {
        assertEquals( 10, Utils.getShortestScrollOption(0, 10, 10, false));
    }

    @Test
    public void wrapping() {
        assertEquals( -1, Utils.getShortestScrollOption(0, 10, 10, true));
    }

    @Test
    public void findingClosestByIncreaseNoWrap() {
        assertEquals( 4, Utils.getShortestScrollOption(0, 4, 9, true));
    }

    @Test
    public void findingClosestByIncreaseWrap() {
        assertEquals( 4, Utils.getShortestScrollOption(6, 0, 9, true));
    }

    @Test
    public void findingClosestByDecreaseNoWrap() {
        assertEquals( -4, Utils.getShortestScrollOption(5, 1, 9, true));
    }

    @Test
    public void findingClosestByDecreaseWrap() {
        assertEquals( -4, Utils.getShortestScrollOption(0, 6, 9, true));
    }

}