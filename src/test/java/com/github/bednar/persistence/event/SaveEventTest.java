package com.github.bednar.persistence.event;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.DummyData;
import com.github.bednar.persistence.resource.Pub;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (19/08/2013 12:01 PM)
 */
public class SaveEventTest extends AbstractPersistenceTest
{
    @Test
    public void canSave()
    {
        dispatcher.publish(new SaveEvent(DummyData.getPub()));
    }

    @Test
    public void resourceHasId()
    {
        Pub pub = DummyData.getPub();

        Assert.assertNull(pub.getId());

        dispatcher.publish(new SaveEvent(pub));

        Assert.assertNotNull(pub.getId());
    }
}
