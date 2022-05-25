package core;

import model.File;
import model.SampleFile;
import org.junit.Test;
import shared.FileManager;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileExplorerTest {

    @Test
    public void testConstructorShouldSetFileNumberToOne() {
        FileManager fileManager = new FileExplorer();
        File root = fileManager.getRoot();
        assertNotNull(root);
        assertEquals(1, root.getNumber());
    }

    @Test
    public void testAddMultipleFilesAsString() {
        FileManager fileManager = new FileExplorer();
        for (int i = 1; i <= 23; i++) {
            File root = fileManager.getRoot();
            assertNotNull(root);
            fileManager.addInDirectory(root.getNumber(), new SampleFile(i, "test_name"));
        }

        String expected =
                "1\n" +
                        "├── 1\n" +
                        "├── 2\n" +
                        "├── 3\n" +
                        "├── 4\n" +
                        "├── 5\n" +
                        "├── 6\n" +
                        "├── 7\n" +
                        "├── 8\n" +
                        "├── 9\n" +
                        "├── 10\n" +
                        "├── 11\n" +
                        "├── 12\n" +
                        "├── 13\n" +
                        "├── 14\n" +
                        "├── 15\n" +
                        "├── 16\n" +
                        "├── 17\n" +
                        "├── 18\n" +
                        "├── 19\n" +
                        "├── 20\n" +
                        "├── 21\n" +
                        "├── 22\n" +
                        "└── 23";

        assertEquals(expected.replaceAll("\n", System.lineSeparator()), fileManager.getAsString());
    }

    @Test
    public void testAddMultipleFiles() {
        FileManager fileManager = new FileExplorer();
        for (int i = 2; i <= 24; i++) {
            fileManager.addInDirectory(i - 1, new SampleFile(i, "test_name"));
        }
        String newLine = System.lineSeparator();
        assertEquals("1" + newLine +
                        "└── 2" + newLine +
                        "    └── 3" + newLine +
                        "        └── 4" + newLine +
                        "            └── 5" + newLine +
                        "                └── 6" + newLine +
                        "                    └── 7" + newLine +
                        "                        └── 8" + newLine +
                        "                            └── 9" + newLine +
                        "                                └── 10" + newLine +
                        "                                    └── 11" + newLine +
                        "                                        └── 12" + newLine +
                        "                                            └── 13" + newLine +
                        "                                                └── 14" + newLine +
                        "                                                    └── 15" + newLine +
                        "                                                        └── 16" + newLine +
                        "                                                            └── 17" + newLine +
                        "                                                                └── 18" + newLine +
                        "                                                                    └── 19" + newLine +
                        "                                                                        └── 20" + newLine +
                        "                                                                            └── 21" + newLine +
                        "                                                                                └── 22" + newLine +
                        "                                                                                    └── 23" + newLine +
                        "                                                                                        └── 24",

                fileManager.getAsString());
    }

    @Test
    public void testGetRoot() {
        FileManager fileManager = new FileExplorer();
        for (int i = 2; i <= 24; i++) {
            fileManager.addInDirectory(i - 1, new SampleFile(i, "test_name"));
        }
        // This method should always return 1
        // however if this only returns it without setting the correct
        // rule inside the constructor the rest tests will fail
        File root = fileManager.getRoot();

        assertNotNull(root);

        assertEquals(1, root.getNumber());
    }

    @Test
    public void testGetFileByKey() {
        FileManager fileManager = new FileExplorer();
        for (int i = 2; i <= 24; i++) {
            fileManager.addInDirectory(i - 1, new SampleFile(i, "test_name"));
        }
        File file = fileManager.get(13);
        assertNotNull(file);
        File prev = file;
        for (int i = 13; i <= 23; i++) {
            assertEquals(i, prev.getNumber());
            List<File> children = prev.getChildren();
            assertNotNull(children);
            assertEquals(1, children.size());
            prev = children.get(0);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testGetFileByKeyShouldThrowWithInvalidKey() {
        FileManager fileManager = new FileExplorer();
        for (int i = 2; i <= 24; i++) {
            fileManager.addInDirectory(i - 1, new SampleFile(i, "test_name"));
        }
        fileManager.get(123);
    }

    @Test
    public void testGetAsString() {
        FileManager fileManager = new FileExplorer();
        fileManager.addInDirectory(1, new SampleFile(45, "test_name"));
        fileManager.addInDirectory(1, new SampleFile(84, "test_name"));
        fileManager.addInDirectory(1, new SampleFile(38, "test_name"));
        fileManager.addInDirectory(45, new SampleFile(10, "test_name"));
        fileManager.addInDirectory(45, new SampleFile(62, "test_name"));
        fileManager.addInDirectory(84, new SampleFile(59, "test_name"));
        fileManager.addInDirectory(59, new SampleFile(24, "test_name"));
        fileManager.addInDirectory(10, new SampleFile(49, "test_name"));
        fileManager.addInDirectory(49, new SampleFile(44, "test_name"));
        fileManager.addInDirectory(62, new SampleFile(80, "test_name"));
        fileManager.addInDirectory(62, new SampleFile(6, "test_name"));
        fileManager.addInDirectory(62, new SampleFile(91, "test_name"));
        fileManager.addInDirectory(80, new SampleFile(5, "test_name"));
        fileManager.addInDirectory(5, new SampleFile(6, "test_name"));
        fileManager.addInDirectory(80, new SampleFile(0, "test_name"));
        fileManager.addInDirectory(91, new SampleFile(65, "test_name"));

        String expected =
                "1\n" +
                        "├── 45\n" +
                        "│   ├── 10\n" +
                        "│   │   └── 49\n" +
                        "│   │       └── 44\n" +
                        "│   └── 62\n" +
                        "│       ├── 80\n" +
                        "│       │   ├── 5\n" +
                        "│       │   │   └── 6\n" +
                        "│       │   └── 0\n" +
                        "│       ├── 6\n" +
                        "│       └── 91\n" +
                        "│           └── 65\n" +
                        "├── 84\n" +
                        "│   └── 59\n" +
                        "│       └── 24\n" +
                        "└── 38";

        assertEquals(expected.replaceAll("\n", System.lineSeparator()), fileManager.getAsString());
    }
}