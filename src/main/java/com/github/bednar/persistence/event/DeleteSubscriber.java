package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import com.github.bednar.base.event.AbstractSubscriber;
import com.github.bednar.persistence.inject.service.Database;
import com.mycila.event.Event;

/**
 * @author Jakub Bednář (19/08/2013 15:33 PM)
 */
public class DeleteSubscriber extends AbstractSubscriber<DeleteEvent>
{
    @Inject
    private Database database;

    @Nonnull
    @Override
    public Class<DeleteEvent> eventType()
    {
        return DeleteEvent.class;
    }

    @Override
    public void onEvent(final @Nonnull Event<DeleteEvent> event) throws Exception
    {
        Database.Transaction transaction = database.transaction();

        try
        {
            Long key    = event.getSource().getId();
            Class type  = event.getSource().getType();

            transaction
                    .delete(key, type)
                    .commit();

            event.getSource().success(key);
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
