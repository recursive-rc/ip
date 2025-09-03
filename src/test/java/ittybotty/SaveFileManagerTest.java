package ittybotty;

import ittybotty.data.TaskList;
import ittybotty.data.tasks.Event;
import ittybotty.data.tasks.Task;
import ittybotty.data.tasks.TaskWithDeadline;
import ittybotty.data.tasks.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveFileManagerTest {
    private SaveFileManager manager;

    @BeforeEach
    public void setUp() {
        this.manager = new SaveFileManager();
    }

    @Test
    public void loadFromFile_emptyFile_loadEmptyList(@TempDir File tempDir) throws IOException {
        File inputFile = this.createTempFile(tempDir, "");
        this.manager.setSaveFilePath(inputFile.getPath());

        TaskList testList = new TaskList();
        TaskList expectedList = new TaskList();
        // expectedList intentionally kept empty
        this.manager.loadFromFile(testList);
        assertEquals(expectedList, testList);
    }

    @Test
    public void loadFromFile_oneToDo_loadCorrectly(@TempDir File tempDir) throws IOException {
        File inputFile = this.createTempFile(tempDir, "T,false,\"borrow book\"");
        this.manager.setSaveFilePath(inputFile.getPath());

        TaskList testList = new TaskList();
        TaskList expectedList = new TaskList();
        expectedList.addTask(new ToDo("borrow book"));
        this.manager.loadFromFile(testList);
        assertEquals(expectedList, testList);
    }

    @Test
    public void loadFromFile_multipleTasks_loadCorrectly(@TempDir File tempDir) throws IOException {
        File inputFile = this.createTempFile(tempDir, """
                E,false,"research conference","2026-03-01","2026-03-04"
                T,false,"read The Mythical Month-Man"
                D,false,"finish literature review","2025-11-10"
                """);
        this.manager.setSaveFilePath(inputFile.getPath());

        TaskList testList = new TaskList();
        TaskList expectedList = new TaskList();
        expectedList.addTask(new Event("research conference",
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 4)));
        expectedList.addTask(new ToDo("read The Mythical Month-Man"));
        expectedList.addTask(new TaskWithDeadline("finish literature review",
                LocalDate.of(2025, 11, 10)));
        this.manager.loadFromFile(testList);
        assertEquals(expectedList, testList);
    }

    @Test
    public void loadFromFile_markedTasks_loadCorrectly(@TempDir File tempDir) throws IOException {
        File inputFile = this.createTempFile(tempDir, """
                D,true,"decide research question","2024-11-10"
                T,true,"borrow Clean Code from NUS Libraries"
                E,true,"camping trip","2023-03-01","2023-03-04"
                """);
        this.manager.setSaveFilePath(inputFile.getPath());

        TaskList testList = new TaskList();
        TaskList expectedList = new TaskList();
        expectedList.addTask(new TaskWithDeadline("decide research question",
                LocalDate.of(2024, 11, 10)));
        expectedList.addTask(new ToDo("borrow Clean Code from NUS Libraries"));
        expectedList.addTask(new Event("camping trip",
                LocalDate.of(2023, 3, 1),
                LocalDate.of(2023, 3, 4)));
        for (int i = 0; i < expectedList.size(); i++) {
            expectedList.markTask(i + 1);
            // `+ 1` because TaskList::markTask parameter is 1-indexed
        }
        this.manager.loadFromFile(testList);
        assertEquals(expectedList, testList);
    }

    @Test
    public void loadFromFile_missingInfo_exceptionThrown(@TempDir File tempDir) throws IOException {
        File inputFile = this.createTempFile(tempDir, """
                E,true,"This never ends","2025-01-01"
                """);
        this.manager.setSaveFilePath(inputFile.getPath());

        TaskList list = new TaskList();
        assertThrows(IOException.class, () -> this.manager.loadFromFile(list));
    }

    /**
     * Creates a file with given text as a test input file.
     *
     * @param tempDir Temporary direction to create file in. Should be
     *                the test method parameter annotated with
     *                {@link org.junit.jupiter.api.io.TempDir @TempDir}.
     * @param textInFile Text to put in the test input file.
     * @return The test input file, which will be a plain text file.
     * @throws IOException If the test input file cannot be created
     *                     or cannot be opened for any other reason.
     */
    private File createTempFile(File tempDir, String textInFile) throws IOException {
        File tempFile = new File(tempDir, "test.txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            assertTrue(tempFile.exists());
            writer.write(textInFile);
        }
        return tempFile;
    }
}
