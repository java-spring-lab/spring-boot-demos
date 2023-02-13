package demo.springboot.rest.api.exception;

public class TaskNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TaskNotFoundException(Integer id) {

        super(String.format("Task Id %d not found", id));
    }
}