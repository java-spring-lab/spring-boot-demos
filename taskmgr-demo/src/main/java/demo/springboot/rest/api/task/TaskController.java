package demo.springboot.rest.api.task;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Show all existing tasks GET /tasks
     *
     * @return List of tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {

        return ResponseEntity.ok(taskService.getTasks());
    }

    /**
     * Create a new task POST /tasks Body:
     *
     * <pre>
     *      {
     *          "title": "Task 4",
     *          "description": "Description 4",
     *          "dueDate": "2021-01-01"
     *      }
     * </pre>
     *
     * @param task Task object sent by client
     * @return Task object created
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {

        var newTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.created(URI.create("/tasks/" + newTask.getId())).body(newTask);
    }

    /**
     * Get a task by id
     *
     * @param id
     * @return Task object
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    /**
     * Delete a task by given id
     *
     * @param id Task id to delete
     * @return the deleted task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id) {

        return ResponseEntity.accepted().body(taskService.deleteTask(id));
    }

    /**
     * Update a task by given id
     *
     * @param id   Task id to update
     * @param task Task object sent by client
     * @return the updated task
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Integer id, @RequestBody Task partialTask) {

        var updatedTask = taskService.updateTask(id,
                partialTask.getTitle(),
                partialTask.getDescription(),
                partialTask.getDueDate());
        return ResponseEntity.accepted().body(updatedTask);
    }
}
