package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.resource.Pub;
import com.github.bednar.test.AssertUtil;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (25/11/2013 20:20)
 */
public class UniqueEventTest extends AbstractPersistenceTest
{
    @Test
    public void uniqueResultSuccess()
    {
        dispatcher.publish(new UniqueEvent<Pub>(Restrictions.eq("name", "Irish Pub"), Pub.class)
        {
            @Override
            public void success(@Nonnull final Pub pub)
            {
                Assert.assertNotNull(pub);
                Assert.assertEquals("Irish Pub", pub.getName());
                Assert.assertEquals((Object) 1L, pub.getId());
            }

            @Override
            public void fail(@Nonnull final Throwable error)
            {
                Assert.fail();
            }
        });
    }

    @Test
    public void uniqueResultFailure()
    {
        dispatcher.publish(new UniqueEvent<Pub>(Restrictions.like("name", "%Pub"), Pub.class)
        {
            @Override
            public void success(@Nonnull final Pub pub)
            {
                Assert.fail();
            }

            @Override
            public void fail(@Nonnull final Throwable error)
            {
                AssertUtil.assertException(NonUniqueResultException.class, error);
            }
        });
    }
}
