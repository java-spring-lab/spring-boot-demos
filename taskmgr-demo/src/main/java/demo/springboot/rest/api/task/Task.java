package demo.springboot.rest.api.task;

import java.util.Date;

import org.springframework.core.style.ToStringCreator;

public class Task {

    Integer id;
    String title;
    String description;
    Date dueDate;

    public Task(Integer id, String title, String description, Date dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", getId())
                .append("title", getTitle())
                .append("desc", getDescription())
                .append("dueDate", getDueDate())
                .toString();

    }
}
