package demo.springboot.taskmgr.task.note;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Note {

    private final Integer id;
    private final String content;
}
