package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.DummyData;
import com.github.bednar.persistence.resource.Pub;
import org.hibernate.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (19/08/2013 3:50 PM)
 */
public class ReadEventTest extends AbstractPersistenceTest
{
    @Test
    public void read() throws InterruptedException
    {
        final Pub pub = DummyData.getPub();
        dispatcher.publish(new SaveEvent(pub));

        dispatcher.publish(new ReadEvent<Pub>(pub.getId(), Pub.class)
        {
            @Override
            public void success(final @Nonnull Pub resource)
            {
                Assert.assertEquals(pub, resource);
            }

            @Override
            public void fail(final @Nonnull Throwable error)
            {
                Assert.fail();
            }
        });
    }

    @Test
    public void readNotExist() throws Throwable
    {
        dispatcher.publish(new ReadEvent<Pub>(-1L, Pub.class)
        {
            @Override
            public void success(final @Nonnull Pub value)
            {
                Assert.fail();
            }

            @Override
            public void fail(final @Nonnull Throwable error)
            {
                Assert.assertEquals(ObjectNotFoundException.class, error.getClass());
            }
        });
    }
}
