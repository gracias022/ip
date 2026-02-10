package miffy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import miffy.task.Deadline;
import miffy.task.Event;
import miffy.task.Task;
import miffy.task.Todo;


public class StorageTest {

    @SuppressWarnings("checkstyle:MethodName")
    @Test
    public void parseTaskFromLine_todo_createsTodoTask() {
        Task t = Storage.parseTaskFromLine("T | 0 | read");

        assertInstanceOf(Todo.class, t);
        assertFalse(t.isDone());
        assertEquals("[T][ ] read", t.toString());
    }

    @Test
    public void parseTaskFromLine_doneTodo_createsDoneTodoTask() {
        Task t = Storage.parseTaskFromLine("T | 1 | read");

        assertInstanceOf(Todo.class, t);
        assertTrue(t.isDone());
        assertEquals("[T][X] read", t.toString());
    }

    @Test
    public void parseTaskFromLine_invalidTaskType_throwsException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            Storage.parseTaskFromLine("X | 0 | blah");
        });

        assertEquals("Unknown task type: X", e.getMessage());
    }

    @SuppressWarnings("checkstyle:MethodName")
    @Test
    public void parseTaskFromLine_deadline_createsDeadlineTask() {
        Task t = Storage.parseTaskFromLine("D | 0 | return book | 2026-01-17T18:00");

        assertInstanceOf(Deadline.class, t);
        assertFalse(t.isDone());
        assertEquals("[D][ ] return book (by: Jan 17 2026 6:00 PM)", t.toString());
    }

    @Test
    public void parseTaskFromLine_doneDeadline_createsDoneDeadlineTask() {
        Task t = Storage.parseTaskFromLine("D | 1 | return book | 2026-01-17T18:00");

        assertInstanceOf(Deadline.class, t);
        assertTrue(t.isDone());
        assertEquals("[D][X] return book (by: Jan 17 2026 6:00 PM)", t.toString());
    }

    @SuppressWarnings("checkstyle:MethodName")
    @Test
    public void parseTaskFromLine_event_createsEventTask() {
        Task t = Storage.parseTaskFromLine("E | 0 | meeting | 2026-01-17T16:00 | 2026-01-17T18:00");

        assertInstanceOf(Event.class, t);
        assertFalse(t.isDone());
        assertEquals("[E][ ] meeting (from: Jan 17 2026 4:00 PM to: Jan 17 2026 6:00 PM)", t.toString());
    }

    @Test
    public void parseTaskFromLine_doneEvent_createsDoneEventTask() {
        Task t = Storage.parseTaskFromLine("E | 1 | meeting | 2026-01-17T16:00 | 2026-01-17T18:00");

        assertInstanceOf(Event.class, t);
        assertTrue(t.isDone());
        assertEquals("[E][X] meeting (from: Jan 17 2026 4:00 PM to: Jan 17 2026 6:00 PM)", t.toString());
    }

    @Test
    public void parseTaskFromLine_corruptedLine_throwsException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            Storage.parseTaskFromLine("T | 0 sth");
        });

        assertEquals("Corrupted line: T | 0 sth", e.getMessage());
    }

    @Test
    public void saveThenLoad_validTasks_tasksPreserved() throws Exception {
        Path tempFile = Files.createTempFile("storage-test", ".txt");
        Storage storage = new Storage(tempFile.toString());

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read"));
        tasks.add(new Deadline(
                "work",
                LocalDateTime.of(2026, 1, 17, 18, 0)));

        storage.save(tasks);
        ArrayList<Task> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertEquals("[T][ ] read", loaded.get(0).toString());
        assertEquals("[D][ ] work (by: Jan 17 2026 6:00 PM)", loaded.get(1).toString());

        Files.deleteIfExists(tempFile); // clean up
    }

    @Test
    void load_fileDoesNotExist_createsFileAndReturnsEmptyList() throws Exception {
        Path tempFile = Files.createTempFile("storage-test", ".txt");
        Files.delete(tempFile); // ensure file is missing

        Storage storage = new Storage(tempFile.toString());

        ArrayList<Task> loaded = storage.load();

        assertTrue(Files.exists(tempFile));
        assertTrue(loaded.isEmpty());

        Files.deleteIfExists(tempFile); // clean up
    }



    @Test
    public void load_fileWithCorruptedLine_lineSkipped() throws Exception {
        Path tempFile = Files.createTempFile("storage-test", ".txt");

        try (FileWriter fw = new FileWriter(tempFile.toString())) {
            fw.write("T | 0 | read\n");
            fw.write("bad line\n");
        }

        Storage storage = new Storage(tempFile.toString());

        ArrayList<Task> loaded = storage.load();

        assertEquals(1, loaded.size());
        assertEquals("[T][ ] read", loaded.get(0).toString());

        Files.deleteIfExists(tempFile); // clean up
    }

}
