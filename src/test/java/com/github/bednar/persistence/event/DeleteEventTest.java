package com.github.bednar.persistence.event;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.DummyData;
import com.github.bednar.persistence.resource.Pub;
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

    @Test
    public void deleteNotPersisted()
    {
        dispatcher.publish(new DeleteEvent(new Pub()));
    }
}
