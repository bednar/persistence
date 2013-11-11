package com.github.bednar.persistence;


import com.github.bednar.base.event.Dispatcher;
import com.github.bednar.base.http.AppContext;
import com.github.bednar.base.inject.Injector;
import com.github.bednar.persistence.inject.service.Database;
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

    /**
     * Test scope
     */
    protected Dispatcher dispatcher;
    protected Injector injector;

    @BeforeClass
    public static void beforeClass() throws Exception
    {
        embeddedJetty = new EmbeddedJetty().start();

        AppContext.initInjector(embeddedJetty.getServletContext());
    }

    @Before
    public void before()
    {
        injector = AppContext.getInjector();

        dispatcher = injector.getInstance(Dispatcher.class);
    }

    @AfterClass
    public static void afterClass() throws Exception
    {
        //Delete H2 database
        AppContext.getInjector()
                .getInstance(Database.class)
                .transaction()
                .doSQL("DROP ALL OBJECTS")
                .finish();

        AppContext
                .clear();

        embeddedJetty
                .stop();
    }
}
