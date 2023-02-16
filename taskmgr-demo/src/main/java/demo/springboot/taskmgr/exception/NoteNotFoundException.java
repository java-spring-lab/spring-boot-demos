package demo.springboot.taskmgr.exception;

public class NoteNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoteNotFoundException(Integer id) {

        super(String.format("Note Id %d not found", id));
    }
}