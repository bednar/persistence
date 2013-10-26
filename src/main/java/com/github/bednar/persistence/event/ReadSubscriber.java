package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import com.github.bednar.base.event.AbstractSubscriber;
import com.github.bednar.persistence.contract.Resource;
import com.github.bednar.persistence.inject.service.Database;
import com.mycila.event.Event;

/**
 * @author Jakub Bednář (19/08/2013 3:46 PM)
 */
public class ReadSubscriber extends AbstractSubscriber<ReadEvent>
{
    @Inject
    private Database database;

    @Nonnull
    @Override
    public Class<ReadEvent> eventType()
    {
        return ReadEvent.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(final Event<ReadEvent> event) throws Exception
    {
        Database.Transaction transaction = database.transaction();

        try
        {
            Resource resource = transaction.read(
                    event.getSource().getKey(),
                    event.getSource().getType());

            event.getSource().success(resource);
        }
        catch (Exception e)
        {
            event.getSource().fail(e);
        }
        finally
        {
            transaction.finish();
        }
    }
}
