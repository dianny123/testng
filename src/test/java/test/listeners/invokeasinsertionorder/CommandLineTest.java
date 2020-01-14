package test.listeners.invokeasinsertionorder;

import org.assertj.core.api.Assertions;
import org.testng.TestNG;
import org.testng.annotations.Test;

import test.SimpleBaseTest;

public class CommandLineTest extends SimpleBaseTest {

    // command line -listener
    @Test
    public void test2() {
        String[] argv = { "-listener",
                "test.listeners.invokeasinsertionorder.Listeners1, test.listeners.invokeasinsertionorder.Listeners2, test.listeners.invokeasinsertionorder.Listeners3",
                "-testclass",
                "test.listeners.invokeasinsertionorder.SimpleTestClassWithConfigMethod" };
        TestNG.privateMain(argv, null);
        System.out.println(GetMessages.getMessages());
        Assertions.assertThat(GetMessages.getMessages())
                .containsExactlyElementsOf(ListenerInvocationAsInsertOrderTest.expected());
    }
}
