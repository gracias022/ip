package miffy.storage;

import miffy.exception.MiffyException;
import miffy.task.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles persistent task storage and management for the Miffy application.
 * <p>
 * This class is responsible for loading tasks from a data file on the hard disk
 * at application startup, and saving tasks to the file whenever the task list changes.
 */
public class Storage {
    private final Path path;

    public Storage(String filePath) {
        this.path = Paths.get(filePath);
    }

    /**
     * Loads tasks from a data file on disk.
     * Checks if a datafile matching the specified file path exists.
     * If it does not, a new file is created and an empty list is returned.
     * Otherwise, each valid line in the file is parsed into a {@link Task} object.
     * Corrupted lines are skipped.
     *
     * @return List of loaded {@link Task} objects.
     * @throws MiffyException If an I/O exception occurs while accessing the data file.
     *
     */
    public ArrayList<Task> load() throws MiffyException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return tasks; // return empty task list
            }

            try (Scanner s = new Scanner(path)) {
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    try {
                        tasks.add(parseTaskFromLine(line));
                    } catch (IllegalArgumentException | DateTimeParseException e) {
                        System.out.println("  Oops, corrupted line detected! Skipping:");
                        System.out.println("  " + line);
                    }
                }
            }

            return tasks;

        } catch (IOException e) {
            throw new MiffyException("Failed to load tasks: " + e.getMessage());
        }
    }

    public static Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Corrupted line: " + line);
        }

        String taskType = parts[0];
        Task task;

        switch (taskType) {
        case "T":
            task = new Todo(parts[2]);
            break;
        case "D":
            LocalDateTime byDate = LocalDateTime.parse(parts[3]);
            task = new Deadline(parts[2], byDate);
            break;
        case "E":
            LocalDateTime fromDate = LocalDateTime.parse(parts[3]);
            LocalDateTime toDate = LocalDateTime.parse(parts[4]);
            task = new Event(parts[2], fromDate, toDate);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }

        if (parts[1].equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Overwrites the data file on disk with the current list of tasks.
     * This method is invoked whenever the {@link TaskList} changes,
     * such as when a task is added, deleted, marked or unmarked.
     *
     * @param tasks Updated list of {@link Task} objects
     * @throws MiffyException If an I/O exception occurs while accessing or writing to the data file.
     */
    public void save(ArrayList<Task> tasks) throws MiffyException {
        try (FileWriter fw = new FileWriter(path.toString())) {
            for (Task t : tasks) {
                fw.write(t.formatTaskForStorage());
            }
        } catch (IOException e) {
            throw new MiffyException("Failed to save tasks: " + e.getMessage());
        }
    }
}
