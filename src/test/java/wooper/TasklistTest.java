package wooper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TasklistTest {
    @Test
    public void getTask_invalidIndex_indexOutOfBoundsException() {
        Tasklist tasklist = new Tasklist();
        assertThrows(IndexOutOfBoundsException.class, () -> tasklist.getTask(0));
    }
}
