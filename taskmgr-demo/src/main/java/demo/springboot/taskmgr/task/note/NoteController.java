package demo.springboot.taskmgr.task.note;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService taskService) {
        this.noteService = taskService;
    }

    /**
     * Show all existing tasks GET /tasks
     *
     * @return List of tasks
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotesForTask(@PathVariable("taskId") Integer taskId) {

        return ResponseEntity.ok(noteService.getAllNotesForTask(taskId));
    }

    /**
     * Create a new note POST /tasks/{id}/comments Body:
     *
     * <pre>
     *      {
     *          "content": "Note Content"
     *      }
     * </pre>
     *
     * @param taskId Task id of the note to create
     * @param note Note object sent by client
     * @return Note object created
     */
    @PostMapping
    public ResponseEntity<Note> createNoteForTask(@PathVariable("taskId") Integer taskId, @RequestBody Note note) {

        var newNote = noteService.createNoteForTask(taskId, note.getContent());
        return ResponseEntity.created(URI.create(String.format("/tasks/%s/comments", taskId))).body(newNote);
    }

    /**
     * Delete a task by given id
     *
     * @param taskId Task id of the note to delete
     * @param noteId Note id to delete
     * @return the deleted note
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNoteForTask(@PathVariable("taskId") Integer taskId, @PathVariable("id") Integer noteId) {

        return ResponseEntity.accepted().body(noteService.deleteNoteForTask(taskId, noteId));
    }

}
