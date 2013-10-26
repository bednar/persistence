package com.github.bednar.persistence;


import com.github.bednar.base.event.Dispatcher;
import com.github.bednar.base.http.AppBootstrap;
import com.github.bednar.base.inject.Injector;
import com.github.bednar.test.EmbeddedJetty;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Jakub Bednář (27/07/2013 2:44 PM)
 */
public abstract class AbstractPersistenceTest
{
    /**
     * Class scope
     */
    protected static EmbeddedJetty embeddedJetty;
    protected static Injector injector;

    /**
     * Test scopre
     */
    protected Dispatcher dispatcher;

    @BeforeClass
    public static void before() throws Exception
    {
        embeddedJetty = new EmbeddedJetty().start();

        injector = (Injector) embeddedJetty.getServletContext().getAttribute(AppBootstrap.INJECTOR_KEY);
    }

    @AfterClass
    public static void after() throws Exception
    {
        embeddedJetty.stop();
    }

    @Before
    public void beforeTest()
    {
        dispatcher = injector.getInstance(Dispatcher.class);
    }
}
