package wooper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WooperTest {
    @Test
    public void parsingTaskNumber_invalidValue_negativeOneReturned() {
        String[] l = {"mark", "a"};
        assertEquals(-1, new Wooper().getTaskNumber(l));
    }
}
