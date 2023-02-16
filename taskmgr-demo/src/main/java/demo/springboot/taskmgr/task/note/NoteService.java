package demo.springboot.taskmgr.task.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import demo.springboot.taskmgr.exception.NoteNotFoundException;
import demo.springboot.taskmgr.task.Task;
import demo.springboot.taskmgr.task.TaskService;

@Service
@Slf4j
public class NoteService {

    private final Map<Integer, List<Note>> taskNotesMap;
    private final AtomicInteger noteId = new AtomicInteger(0);
    private final TaskService taskService;

    public NoteService(TaskService taskService) {

        this.taskService = taskService;

        Note note1 = new Note(noteId.incrementAndGet(), "Note 1");
        Note note2 = new Note(noteId.incrementAndGet(), "Note 2");
        Note note3 = new Note(noteId.incrementAndGet(), "Note 3");

        taskNotesMap = new HashMap<>();
        taskNotesMap.put(1, new ArrayList<>());
        taskNotesMap.get(1).add(note1);
        taskNotesMap.get(1).add(note2);
        taskNotesMap.get(1).add(note3);

        taskNotesMap.put(2, new ArrayList<>());
        taskNotesMap.get(2).add(note1);
        taskNotesMap.get(2).add(note2);
        taskNotesMap.get(2).add(note3);

        taskNotesMap.put(3, new ArrayList<>());
        taskNotesMap.get(3).add(note1);
        taskNotesMap.get(3).add(note2);
        taskNotesMap.get(3).add(note3);
    }

    /**
     * Show all existing notes for given task
     *
     * @return List of notes
     */
    public List<Note> getAllNotesForTask(Integer taskId) {

        log.debug("taskId={}", taskId);
        List<Note> noteList = taskNotesMap.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(taskId))
                .flatMap(e -> e.getValue().stream())
                .toList();

        log.debug("noteList={}", noteList);
        return noteList;
    }

    /**
     * Create a new note for given task
     *
     * @param taskId Task id of the new note
     * @param content Note content
     * @return Note object created
     */
    public Note createNoteForTask(Integer taskId, String content) {

        log.debug("taskId={}|content={}", taskId, content);
        var currTask = taskService.getTaskById(taskId);

        var newNote = new Note(noteId.incrementAndGet(), content);
        taskNotesMap.computeIfAbsent(currTask.getId(), k -> new ArrayList<>()).add(newNote);

        log.debug("newNote={}", newNote);
        return newNote;
    }

    public Note getNoteByIdForTask(Integer taskId, Integer noteId) {

        List<Note> noteList = getAllNotesForTask(taskId);

        var fetchNote = noteList.stream()
                .filter(n -> n.getId().equals(noteId))
                .findFirst()
                .orElseThrow(() -> new NoteNotFoundException(noteId));

        log.debug("fetchNote={}", fetchNote);
        return fetchNote;
    }

    /**
     * Delete a note by given id for given task
     *
     * @param taskId Task id of the note
     * @param noteId Note id to delete
     * @return the deleted task
     */
    public Note deleteNoteForTask(Integer taskId, Integer noteId) {

        log.debug("delete_taskId={}|noteId={}", taskId, noteId);

        var currTask = taskService.getTaskById(taskId);
        Note removeNote = getNoteByIdForTask(currTask.getId(), noteId);
        taskNotesMap.get(taskId).remove(removeNote);

        log.debug("removeNote={}", removeNote);
        return removeNote;
    }
}
