package marc.dashboard.todo;

import java.util.Date;

public class TodoItem implements Comparable<TodoItem> {
    private Date date;
    private String title;

    public TodoItem(Date date, String title) {
        this.date = date;
        this.title = title;
    }

    @Override
    public int compareTo(TodoItem o) {
        return date.compareTo(o.date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
