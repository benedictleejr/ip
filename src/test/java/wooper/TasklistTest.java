package wooper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TasklistTest {
    @Test
    public void getTask_invalidIndex_indexOutOfBoundsException() {
        Tasklist tasklist = new Tasklist();
        assertThrows(IndexOutOfBoundsException.class, () -> tasklist.getTask(0));
    }

    @Test
    public void findTasks_noTasksFound_wooperException() {
        Tasklist tasklist = new Tasklist();
        assert tasklist.getAllTasks().size() == 0 : "Tasklist should be size 0 but it's not!";
        assertThrows(WooperException.class, () -> tasklist.findTasks("party"));
    }
}
