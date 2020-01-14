package test.listeners.invokeasinsertionorder;

import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_1;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_2;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_3;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_FAILURE_1;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_FAILURE_2;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_FAILURE_3;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_SKIP_1;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_SKIP_2;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_SKIP_3;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_1;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_2;
import static test.listeners.invokeasinsertionorder.Constants.ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_3;
import static test.listeners.invokeasinsertionorder.Constants.IEXECUTIONLISTENER_ON_EXECUTION_FINISH_1;
import static test.listeners.invokeasinsertionorder.Constants.IEXECUTIONLISTENER_ON_EXECUTION_FINISH_2;
import static test.listeners.invokeasinsertionorder.Constants.IEXECUTIONLISTENER_ON_EXECUTION_FINISH_3;
import static test.listeners.invokeasinsertionorder.Constants.IEXECUTIONLISTENER_ON_EXECUTION_START_1;
import static test.listeners.invokeasinsertionorder.Constants.IEXECUTIONLISTENER_ON_EXECUTION_START_2;
import static test.listeners.invokeasinsertionorder.Constants.IEXECUTIONLISTENER_ON_EXECUTION_START_3;
import static test.listeners.invokeasinsertionorder.Constants.IINVOKEDMETHODLISTENER_AFTER_INVOCATION_1;
import static test.listeners.invokeasinsertionorder.Constants.IINVOKEDMETHODLISTENER_AFTER_INVOCATION_2;
import static test.listeners.invokeasinsertionorder.Constants.IINVOKEDMETHODLISTENER_AFTER_INVOCATION_3;
import static test.listeners.invokeasinsertionorder.Constants.IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_1;
import static test.listeners.invokeasinsertionorder.Constants.IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_2;
import static test.listeners.invokeasinsertionorder.Constants.IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_3;
import static test.listeners.invokeasinsertionorder.Constants.ISUITELISTENER_ON_FINISH_1;
import static test.listeners.invokeasinsertionorder.Constants.ISUITELISTENER_ON_FINISH_2;
import static test.listeners.invokeasinsertionorder.Constants.ISUITELISTENER_ON_FINISH_3;
import static test.listeners.invokeasinsertionorder.Constants.ISUITELISTENER_ON_START_1;
import static test.listeners.invokeasinsertionorder.Constants.ISUITELISTENER_ON_START_2;
import static test.listeners.invokeasinsertionorder.Constants.ISUITELISTENER_ON_START_3;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_FINISH_TEST_TAG_1;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_FINISH_TEST_TAG_2;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_FINISH_TEST_TAG_3;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_START_TEST_METHOD_1;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_START_TEST_METHOD_2;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_START_TEST_METHOD_3;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_START_TEST_TAG_1;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_START_TEST_TAG_2;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_START_TEST_TAG_3;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_TEST_FAILURE_TEST_METHOD_1;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_TEST_FAILURE_TEST_METHOD_2;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_TEST_FAILURE_TEST_METHOD_3;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_TEST_SUCCESS_TEST_METHOD_1;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_TEST_SUCCESS_TEST_METHOD_2;
import static test.listeners.invokeasinsertionorder.Constants.ITESTLISTENER_ON_TEST_SUCCESS_TEST_METHOD_3;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.annotations.Test;

import test.SimpleBaseTest;

public class ListenerInvocationAsInsertOrderTest extends SimpleBaseTest {

    @Test
    public void test() {

        runTest(expected(), SimpleTestClassWithConfigMethod.class,
                new Listeners1(), new Listeners2(), new Listeners3());
    }

    private static void runTest(List<String> expected, Class<?> clazz, ITestNGListener... listeners) {
        TestNG testng = create(clazz);
        for (ITestNGListener listener : listeners) {
            testng.addListener(listener);
        }
        testng.alwaysRunListeners(true);
        testng.run();
        System.out.println(GetMessages.getMessages());
        Assertions.assertThat(GetMessages.getMessages()).containsExactlyElementsOf(expected);
    }

    public static List<String> expected() {
        List<String> expected = Arrays.asList(
                IEXECUTIONLISTENER_ON_EXECUTION_START_1,
                IEXECUTIONLISTENER_ON_EXECUTION_START_2,
                IEXECUTIONLISTENER_ON_EXECUTION_START_3,

                ISUITELISTENER_ON_START_1,
                ISUITELISTENER_ON_START_2,
                ISUITELISTENER_ON_START_3,

                ITESTLISTENER_ON_START_TEST_TAG_1,
                ITESTLISTENER_ON_START_TEST_TAG_2,
                ITESTLISTENER_ON_START_TEST_TAG_3,

                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_1,
                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_2,
                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_3,

                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_1,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_2,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_3,

                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_3,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_2,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_1,

                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_3,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_2,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_1,

                ITESTLISTENER_ON_START_TEST_METHOD_1,
                ITESTLISTENER_ON_START_TEST_METHOD_2,
                ITESTLISTENER_ON_START_TEST_METHOD_3,

                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_1,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_2,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_3,

                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_3,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_2,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_1,

                ITESTLISTENER_ON_TEST_SUCCESS_TEST_METHOD_3,
                ITESTLISTENER_ON_TEST_SUCCESS_TEST_METHOD_2,
                ITESTLISTENER_ON_TEST_SUCCESS_TEST_METHOD_1,

                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_1,
                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_2,
                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_3,

                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_1,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_2,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_3,

                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_3,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_2,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_1,

                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_3,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_2,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SUCCESS_1,

                ITESTLISTENER_ON_START_TEST_METHOD_1,
                ITESTLISTENER_ON_START_TEST_METHOD_2,
                ITESTLISTENER_ON_START_TEST_METHOD_3,

                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_1,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_2,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_3,

                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_3,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_2,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_1,

                ITESTLISTENER_ON_TEST_FAILURE_TEST_METHOD_3,
                ITESTLISTENER_ON_TEST_FAILURE_TEST_METHOD_2,
                ITESTLISTENER_ON_TEST_FAILURE_TEST_METHOD_1,

                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_1,
                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_2,
                ICONFIGURATIONLISTENER_BEFORE_CONFIGURATION_3,

                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_1,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_2,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_3,

                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_3,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_2,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_1,

                ICONFIGURATIONLISTENER_ON_CONFIGURATION_FAILURE_3,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_FAILURE_2,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_FAILURE_1,

                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_1,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_2,
                IINVOKEDMETHODLISTENER_BEFORE_INVOCATION_3,

                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_3,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_2,
                IINVOKEDMETHODLISTENER_AFTER_INVOCATION_1,

                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SKIP_3,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SKIP_2,
                ICONFIGURATIONLISTENER_ON_CONFIGURATION_SKIP_1,

                ITESTLISTENER_ON_FINISH_TEST_TAG_3,
                ITESTLISTENER_ON_FINISH_TEST_TAG_2,
                ITESTLISTENER_ON_FINISH_TEST_TAG_1,

                ISUITELISTENER_ON_FINISH_3,
                ISUITELISTENER_ON_FINISH_2,
                ISUITELISTENER_ON_FINISH_1,

                IEXECUTIONLISTENER_ON_EXECUTION_FINISH_3,
                IEXECUTIONLISTENER_ON_EXECUTION_FINISH_2,
                IEXECUTIONLISTENER_ON_EXECUTION_FINISH_1);

        return expected;
    }
}
