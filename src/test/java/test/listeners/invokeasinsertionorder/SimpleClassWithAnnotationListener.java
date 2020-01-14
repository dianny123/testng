package test.listeners.invokeasinsertionorder;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ test.listeners.invokeasinsertionorder.Listeners1.class,
        test.listeners.invokeasinsertionorder.Listeners2.class,
        test.listeners.invokeasinsertionorder.Listeners3.class })
public class SimpleClassWithAnnotationListener {

    @BeforeMethod
    public void beforeMethod() {}

    @Test(priority = 0)
    public void testWillPass() {}

    @Test(priority = 1)
    public void testWillFail() {
        Assert.fail();
    }

    @AfterClass
    public void afterClass() {
        Assert.fail();
    }

    @AfterTest
    public void afterTest() {}

}
