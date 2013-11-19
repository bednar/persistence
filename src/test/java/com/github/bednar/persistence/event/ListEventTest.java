package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;
import java.util.List;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.DummyData;
import com.github.bednar.persistence.resource.Pub;
import com.github.bednar.test.AssertUtil;
import org.hibernate.QueryException;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (19/08/2013 4:27 PM)
 */
public class ListEventTest extends AbstractPersistenceTest
{
    @Test
    public void list()
    {
        Pub pub1 = DummyData.getPub("Kovárna");
        dispatcher.publish(new SaveEvent(pub1));

        Pub pub2 = DummyData.getPub("Kotelna");
        dispatcher.publish(new SaveEvent(pub2));

        Pub pub3 = DummyData.getPub("U Bukáčků");
        dispatcher.publish(new SaveEvent(pub3));

        dispatcher.publish(new ListEvent<Pub>(Restrictions.like("name", "Ko%"), Pub.class)
        {
            @Override
            public void success(final @Nonnull List<Pub> resources)
            {
                Assert.assertEquals(2, resources.size());
                Assert.assertTrue(resources.get(0).getName().startsWith("Ko"));
                Assert.assertTrue(resources.get(1).getName().startsWith("Ko"));
            }

            @Override
            public void fail(final @Nonnull Throwable error)
            {
                Assert.fail();
            }
        });
    }

    @Test
    public void illegalRestrictions()
    {
        try
        {
            dispatcher.publish(new ListEvent<>(Restrictions.like("notExistProperty", "error"), Pub.class));
        }
        catch (Exception e)
        {
            AssertUtil.assertException(QueryException.class, e);
        }
    }
}
