package com.github.bednar.persistence.inject.service;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.resource.Pub;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.Restrictions;
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

    @Test
    public void uniqueResultSuccess()
    {
        Database.Transaction transaction = injector
                .getInstance(Database.class)
                .transaction();

        Pub pub = transaction.unique(Restrictions.eq("name", "Irish Pub"), Pub.class);

        Assert.assertNotNull(pub);
        Assert.assertEquals("Irish Pub", pub.getName());
        Assert.assertEquals((Object) 1L, pub.getId());

        transaction.finish();
    }

    @Test(expected = NonUniqueResultException.class)
    public void uniqueResultFailure()
    {
        Database.Transaction transaction = injector
                .getInstance(Database.class)
                .transaction();

        try
        {
            transaction.unique(Restrictions.like("name", "%Pub"), Pub.class);
        }
        finally
        {
            transaction.finish();
        }
    }
}
