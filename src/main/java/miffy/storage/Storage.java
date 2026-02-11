package miffy.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import miffy.exception.MiffyException;
import miffy.task.Deadline;
import miffy.task.Event;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.task.Todo;

/**
 * Handles persistent task storage and management for the Miffy application.
 * <p>
 * This class is responsible for loading tasks from a data file on the hard disk
 * at application startup, and saving tasks to the file whenever the task list changes.
 */
public class Storage {
    private final Path path;

    /**
     * Constructs a {@code Storage} object for a specified file path.
     *
     * @param filePath Path to the data file used to store tasks.
     */
    public Storage(String filePath) {
        this.path = Paths.get(filePath);
    }

    /**
     * Loads tasks from a data file on disk.
     * <p>
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
            ensureFileExists();

            try (Scanner scanner = new Scanner(path)) {
                while (scanner.hasNextLine()) {
                    processLine(scanner.nextLine(), tasks);
                }
            }

            return tasks;

        } catch (IOException e) {
            throw new MiffyException("Failed to load tasks: " + e.getMessage());
        }
    }

    private void ensureFileExists() throws IOException {
        if (Files.exists(path)) {
            return;
        }

        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }

    /**
     * Parses a single line from the storage file into a {@link Task} and adds it to the given task list.
     *
     * @param line Raw line read from the file.
     * @param tasks {@link TaskList} to add the parsed task to.
     */
    private void processLine(String line, ArrayList<Task> tasks) {
        try {
            tasks.add(parseTaskFromLine(line));
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("  Oops, corrupted line detected! Skipping:");
            System.out.println("  " + line);
        }
    }


    /**
     * Parses a single line from the data file into a {@link Task} object.
     *
     * @param line Line from the file representing a task.
     * @return Task object corresponding to the line.
     * @throws IllegalArgumentException If the line is corrupted or has an unknown task type.
     */
    public static Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        validateParts(parts, line);

        Task task = createTask(parts);
        markIfDone(task, parts[1]);

        return task;
    }

    private static void validateParts(String[] parts, String line) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Corrupted line: " + line);
        }
    }

    private static Task createTask(String[] parts) {
        String taskType = parts[0];

        switch (taskType) {
        case "T":
            return new Todo(parts[2]);
        case "D":
            LocalDateTime byDate = LocalDateTime.parse(parts[3]);
            return new Deadline(parts[2], byDate);
        case "E":
            LocalDateTime fromDate = LocalDateTime.parse(parts[3]);
            LocalDateTime toDate = LocalDateTime.parse(parts[4]);
            return new Event(parts[2], fromDate, toDate);
        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    private static void markIfDone(Task task, String completionFlag) {
        if ("1".equals(completionFlag)) {
            task.markAsDone();
        }
    }

    /**
     * Overwrites the data file on disk with the current list of tasks.
     * <p>
     * This method should be invoked whenever the task list is modified,
     * such as when a task is added, deleted, marked or unmarked.
     *
     * @param tasks List of {@link Task} objects to save.
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
