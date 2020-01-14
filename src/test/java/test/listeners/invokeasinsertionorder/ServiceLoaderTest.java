package test.listeners.invokeasinsertionorder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.TestNG;
import org.testng.annotations.Test;

import test.SimpleBaseTest;

public class ServiceLoaderTest extends SimpleBaseTest {

    URL r = ClassLoader.getSystemClassLoader().getResource("META-INF/services/org.testng.ITestNGListener");
    String file = r.getPath();

    @Test
    public void test3() throws IOException {

        //lock file
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.tryLock();
        if (fileLock.isValid()) {
            System.out.println("You have got the file lock.");
        }

        // add listeners to META-INF/services/org.testng.ITestNGListener
        readFile();
        addLinesToFile();

        TestNG tng = create(SimpleTestClassWithConfigMethod.class);
        URL url = getClass().getClassLoader().getResource("serviceloader.jar");
        System.out.println("resouces: " + url + "\n");
        URLClassLoader ucl = URLClassLoader.newInstance(new URL[] { url });
        tng.setServiceLoaderClassLoader(ucl);
        tng.run();

        // remove listeners after run
        removeLines();
        fileLock.release();

        List<String> s = GetMessages.getMessages();
        System.out.println(s);
        Assertions.assertThat(GetMessages.getMessages())
                .containsExactlyElementsOf(ListenerInvocationAsInsertOrderTest.expected());
        s.clear();
    }

    public void readFile() throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(
                file));) {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        }

    }

    public void addLinesToFile() throws IOException {

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file, true));) {
            output.newLine();
            output.append("test.listeners.invokeasinsertionorder.Listeners1");
            output.newLine();
            output.append("test.listeners.invokeasinsertionorder.Listeners2");
            output.newLine();
            output.append("test.listeners.invokeasinsertionorder.Listeners3");
        }
    }

    public void removeLines() throws IOException {
        File inputFile = new File(file);
        URL tempurl = ClassLoader.getSystemClassLoader().getResource("META-INF/services");
        String tempfile = tempurl.getPath();
        File tempFile = new File(tempfile + "temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));) {

            String lineToRemove1 = "test.listeners.invokeasinsertionorder.Listeners1";
            String lineToRemove2 = "test.listeners.invokeasinsertionorder.Listeners2";
            String lineToRemove3 = "test.listeners.invokeasinsertionorder.Listeners3";
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(lineToRemove1) || trimmedLine.equals(lineToRemove2)
                        || trimmedLine.equals(lineToRemove3)) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        boolean successful = tempFile.renameTo(inputFile);
        System.out.println("Has renamed successful? " + successful);

    }

}
