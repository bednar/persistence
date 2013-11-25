package com.github.bednar.persistence.inject.service;

import com.github.bednar.persistence.AbstractPersistenceTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (27/07/2013 2:41 PM)
 */
public class DatabaseTest extends AbstractPersistenceTest
{
    @Test
    public void databaseNotNull()
    {
        Database database = injector.getInstance(Database.class);

        Assert.assertNotNull(database);
    }

    @Test
    public void createTransaction()
    {
        Database database = injector.getInstance(Database.class);

        Database.Transaction transaction = database.transaction();

        Assert.assertNotNull(transaction);

        transaction.finish();
    }

    @Test
    public void doSQLStatement()
    {
        Database database = injector.getInstance(Database.class);

        database
                .transaction()
                .doSQL("select * from Pub")
                .finish();
    }

    @Test
    public void sessionNotNull()
    {
        Database.Transaction transaction = injector
                .getInstance(Database.class)
                .transaction();

        Assert.assertNotNull(transaction.session());

        transaction.finish();
    }
}
