import java.io.PrintWriter;
import java.util.ArrayList;

public class Tasklist {
    protected ArrayList<Task> tasklist;

    public Tasklist() {
        this.tasklist = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasklist.add(task);
    }

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
            pw.printf("%d[%s] %s\n", i+1, t.getStatusIcon(), t.getDescription());
        }
        pw.println("____________________________________________________________");
    }

    public Task getTask(int index) {
        return this.tasklist.get(index);
    }
}
