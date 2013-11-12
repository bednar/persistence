package com.github.bednar.persistence.event;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.DummyData;
import com.github.bednar.persistence.resource.Pub;
import com.github.bednar.test.AssertUtil;
import org.junit.Test;

/**
 * @author Jakub Bednář (19/08/2013 3:34 PM)
 */
public class DeleteEventTest extends AbstractPersistenceTest
{
    @Test
    public void delete()
    {
        Pub pub = DummyData.getPub();

        dispatcher.publish(new SaveEvent(pub));
        dispatcher.publish(new DeleteEvent(pub));
    }

    @Test(expected = NullPointerException.class)
    public void deleteNotPersisted()
    {
        dispatcher.publish(new DeleteEvent(new Pub()));
    }

    @Test
    public void deleteNotExist()
    {
        Pub pub = new Pub();
        pub.setId(999L);

        try
        {
            dispatcher.publish(new DeleteEvent(pub));
        }
        catch (Exception e)
        {
            AssertUtil.assertException(IllegalArgumentException.class, e);
        }
    }
}
