import java.io.PrintWriter;
import java.util.ArrayList;

public class Tasklist {
    protected ArrayList<Task> tasklist;

    public Tasklist() {
        this.tasklist = new ArrayList<>();
    }

    /**
     * Adds a task to the tasklist and prints a message to the user.
     * @param pw printwriter to print the message to the user
     * @param task task to be added to the tasklist
     */
    public void addTask(PrintWriter pw, Task task) {
        this.tasklist.add(task);
        String output = String.format("""
                ____________________________________________________________
                
                Got it. I've added this task:
                    [%s][ ] %s
                Now you have %d tasks in the list.
                ____________________________________________________________

                """, task.getTaskType(), task.getDescription(), tasklist.size());
        pw.println(output);
    }

    /**
     * Prints all tasks in the lists with beautiful formatting
     * @param pw printwriter to print the message to the user
     */
    public void printTasklist(PrintWriter pw) {
        if (this.tasklist.size() == 0) {
            String output = """
                    ____________________________________________________________

                    Task List:
                    No tasks in the list.
                    ____________________________________________________________
                    """;
            pw.println(output);
            return;
        }

        pw.println("____________________________________________________________\n");
        pw.println("Task List:");
        for (int i = 0; i < tasklist.size(); i++) {
            Task t = this.tasklist.get(i);
            pw.printf("%d.[%s][%s] %s\n", i+1, t.getTaskType(), t.getStatusIcon(), t.getDescription());
        }
        pw.println("____________________________________________________________");
    }

    public Task getTask(int index) {
        return this.tasklist.get(index);
    }

}
