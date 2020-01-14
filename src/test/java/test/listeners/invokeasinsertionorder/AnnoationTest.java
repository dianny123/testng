package test.listeners.invokeasinsertionorder;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.TestNG;
import org.testng.annotations.Test;

import test.SimpleBaseTest;

public class AnnoationTest extends SimpleBaseTest {

    @Test
    public void test1() {

        //@Listener

        TestNG testng = create(SimpleClassWithAnnotationListener.class);
        testng.run();
        List<String> s = GetMessages.getMessages();
        System.out.println(s);
        Assertions.assertThat(GetMessages.getMessages())
                .containsExactlyElementsOf(ListenerInvocationAsInsertOrderTest.expected());
        s.clear();

    }

}
