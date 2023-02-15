package demo.springboot.taskmgr.task;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import demo.springboot.taskmgr.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskService {

    private final List<Task> taskList;
    private final AtomicInteger taskId = new AtomicInteger(0);

    public TaskService() {

        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", new Date()));
    }

    /**
     * Show all existing tasks
     *
     * @return List of tasks
     */
    public List<Task> getAllTasks() {

        log.debug("tasks_count={}", taskList.size());
        return taskList;
    }

    /**
     * Create a new task
     *
     * @param title       Title of new task
     * @param description Descripiton of new task
     * @param dueDate     Due Date of new task
     * @return Task object created
     */
    public Task createTask(String title, String description, Date dueDate) {

        // TODO: ensure date is not past
        log.debug("title={}|desc={}|dueDate={}", title, description, dueDate);

        var newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
        taskList.add(newTask);

        log.debug("newTask={}", newTask);
        return newTask;
    }

    public Task getTaskById(Integer id) {

        log.debug("fetch_id={}", id);

        var fetchTask = taskList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));

        log.debug("fetchTask={}", fetchTask);
        return fetchTask;
    }

    /**
     * Delete a task by given id
     *
     * @param id Task id to delete
     * @return the deleted task
     */
    public Task deleteTask(Integer id) {

        log.debug("delete_id={}", id);

        var removeTask = getTaskById(id);
        taskList.remove(removeTask);

        log.debug("removeTask={}", removeTask);
        return removeTask;
    }

    /**
     * Update a task by given id
     *
     * @param id   Task id to update
     * @param task Task object sent by client
     * @return the updated task
     */
    public Task updateTask(Integer id, String title, String description, Date dueDate) {

        log.debug("update_id={}|title={}|desc={}|dueDate={}", id, title, description, dueDate);

        var changeTask = getTaskById(id);
        var partialTask = new Task(title, description, dueDate);
        BeanUtils.copyProperties(partialTask, changeTask, getNullPropNames(partialTask));

        log.debug("changeTask={}", changeTask);
        return changeTask;
    }

    private String[] getNullPropNames(Object obj) {

        Set<String> nullPropNames = new HashSet<>();
        nullPropNames.add("id"); // Skip Identity Prop

        BeanWrapper srcObj = new BeanWrapperImpl(obj);
        PropertyDescriptor[] props = srcObj.getPropertyDescriptors();

        for (PropertyDescriptor prop : props) {
            Object srcFieldVal = srcObj.getPropertyValue(prop.getName());

            if (Objects.isNull(srcFieldVal)) {
                nullPropNames.add(prop.getName());
            }
        }

        log.debug("nullPropNames={}", nullPropNames);
        return nullPropNames.toArray(String[]::new);
    }
}
