package org.testng.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import org.testng.IClass;
import org.testng.IClassListener;
import org.testng.IDataProviderListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SuiteRunState;
import org.testng.collections.Lists;
import org.testng.internal.ConfigMethodArguments.Builder;
import org.testng.internal.ITestInvoker.FailureContext;
import org.testng.internal.invokers.InvokedMethodListenerInvoker;
import org.testng.internal.invokers.InvokedMethodListenerMethod;
import org.testng.xml.XmlSuite;

/**
 * This class is responsible for invoking methods: - test methods - configuration methods - possibly
 * in a separate thread and then for notifying the result listeners.
 *
 * @author <a href="mailto:cedric@beust.com">Cedric Beust</a>
 * @author <a href='mailto:the_mindstorm@evolva.ro'>Alexandru Popescu</a>
 */
public class Invoker implements IInvoker {

    /** Predicate to filter methods */
    static final BiPredicate<ITestNGMethod, IClass> CAN_RUN_FROM_CLASS = ITestNGMethod::canRunFromClass;
    /** Predicate to filter methods */
    static final BiPredicate<ITestNGMethod, IClass> SAME_CLASS = (m, c) -> c == null
            || m.getTestClass().getName().equals(c.getName());

    private final TestInvoker m_testInvoker;
    private final ConfigInvoker m_configInvoker;
    private final ITestResultNotifier m_notifier;
    private final ITestContext m_testContext;
    private final Collection<IInvokedMethodListener> m_invokedMethodListeners;

    public Invoker(
            IConfiguration configuration,
            ITestContext testContext,
            ITestResultNotifier notifier,
            SuiteRunState state,
            boolean skipFailedInvocationCounts,
            Collection<IInvokedMethodListener> invokedMethodListeners,
            List<IClassListener> classListeners,
            Collection<IDataProviderListener> dataProviderListeners) {
        m_configInvoker = new ConfigInvoker(notifier, invokedMethodListeners, testContext, state, configuration);
        m_testInvoker = new TestInvoker(notifier, testContext, state, configuration,
                invokedMethodListeners,
                dataProviderListeners, classListeners, skipFailedInvocationCounts, m_configInvoker);
        m_notifier = notifier;
        m_testContext = testContext;
        m_invokedMethodListeners = invokedMethodListeners;
    }

    @Override
    public ConfigInvoker getConfigInvoker() {
        return m_configInvoker;
    }

    @Override
    public TestInvoker getTestInvoker() {
        return m_testInvoker;
    }

    /**
     * Invoke configuration methods if they belong to the same TestClass passed in parameter..
     * <p>
     * TODO: Calculate ahead of time which methods should be invoked for each class. Might speed
     * things up for users who invoke the same test class with different parameters in the same
     * suite
     * run.
     * <p>
     * If instance is non-null, the configuration will be run on it. If it is null, the
     * configuration methods will be run on all the instances retrieved from the ITestClass.
     */
    @Override
    public void invokeConfigurations(
            IClass testClass,
            ITestNGMethod[] allMethods,
            XmlSuite suite,
            Map<String, String> params,
            Object[] parameterValues,
            Object instance) {
        ConfigMethodArguments attributes = new Builder()
                .forTestClass(testClass)
                .usingConfigMethodsAs(allMethods)
                .forSuite(suite).usingParameters(params)
                .usingParameterValues(parameterValues)
                .usingInstance(instance)
                .build();
        m_configInvoker.invokeConfigurations(attributes);
    }

    /**
     * Invoke all the test methods. Note the plural: the method passed in parameter might be invoked
     * several times if the test class it belongs to has more than one instance (i.e., if
     * an @Factory
     * method has been declared somewhere that returns several instances of this TestClass). If
     * no @Factory method was specified, testMethod will only be invoked once.
     * <p>
     * Note that this method also takes care of invoking the beforeTestMethod and afterTestMethod,
     * if any.
     * <p>
     * Note (alex): this method can be refactored to use a SingleTestMethodWorker that directly
     * invokes {@link ITestInvoker#invokeTestMethod(TestMethodArguments, XmlSuite, FailureContext)}
     * and
     * this would simplify the implementation (see how DataTestMethodWorker is used)
     */
    @Override
    public List<ITestResult> invokeTestMethods(
            ITestNGMethod testMethod,
            Map<String, String> testParameters,
            ConfigurationGroupMethods groupMethods,
            Object instance,
            ITestContext testContext) {
        return m_testInvoker
                .invokeTestMethods(testMethod, groupMethods, instance, testContext);
    }

    static void log(int level, String s) {
        Utils.log("Invoker " + Thread.currentThread().hashCode(), level, s);
    }

    void runTestListeners(ITestResult tr) {

        TestListenerHelper.runTestListeners(tr, m_notifier.getTestListeners());

        if (tr.getStatus() == ITestResult.STARTED) {

            TestListenerHelper.runTestListeners(tr, m_notifier.getTestListeners());

        } else {

            List<ITestListener> test_Listeners_reverted = Lists.newArrayList(m_notifier.getTestListeners());

            Collections.reverse(test_Listeners_reverted);

            TestListenerHelper.runTestListeners(tr, test_Listeners_reverted);

        }

    }

    private void runInvokedMethodListeners(InvokedMethodListenerMethod listenerMethod,
            IInvokedMethod invokedMethod,
            ITestResult testResult) {
        if (noListenersPresent()) {
            return;
        }
        InvokedMethodListenerInvoker invoker = new InvokedMethodListenerInvoker(listenerMethod, testResult,
                m_testContext);
        if (InvokedMethodListenerMethod.BEFORE_INVOCATION.equals(listenerMethod)) {

            for (IInvokedMethodListener currentListener : m_invokedMethodListeners) {

                invoker.invokeListener(currentListener, invokedMethod);

            }

        } else {

            List<IInvokedMethodListener> m_invokedMethodListenerList = new ArrayList<>(m_invokedMethodListeners);

            Collections.reverse(m_invokedMethodListenerList);

            for (IInvokedMethodListener currentListener : m_invokedMethodListenerList) {

                invoker.invokeListener(currentListener, invokedMethod);

            }

        }
    }

    private boolean noListenersPresent() {

        return m_invokedMethodListeners == null || m_invokedMethodListeners.isEmpty();

    }
}
