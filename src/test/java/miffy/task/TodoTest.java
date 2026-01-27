package miffy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void formatTaskForStorage_notDone_formattedAsNotDone() {
        Todo t = new Todo("read book");
        assertEquals("T | 0 | read book\n", t.formatTaskForStorage());
    }

    @Test
    public void formatTaskForStorage_done_formattedAsDone() {
        Todo t = new Todo("read book");
        t.markAsDone();
        assertEquals("T | 1 | read book\n", t.formatTaskForStorage());
    }

    @Test
    public void formatTaskForStorage_markedThenUnmarked_formattedAsNotDone() {
        Todo t = new Todo("read book");
        t.markAsDone();
        t.unmark();
        assertEquals("T | 0 | read book\n", t.formatTaskForStorage());
    }

    @Test
    public void toString_notDone_showsEmptyStatus() {
        Todo t = new Todo("read book");
        assertEquals("[T][ ] read book", t.toString());
    }

    @Test
    public void toString_done_showsCompletedStatus() {
        Todo t = new Todo("read book");
        t.markAsDone();
        assertEquals("[T][X] read book", t.toString());
    }

    @Test
    public void toString_markedThenUnmarked_showsEmptyStatus() {
        Todo t = new Todo("read book");
        t.markAsDone();
        t.unmark();
        assertEquals("[T][ ] read book", t.toString());
    }

}
