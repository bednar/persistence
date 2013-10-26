package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import com.github.bednar.base.event.AbstractSubscriber;
import com.github.bednar.persistence.contract.Resource;
import com.github.bednar.persistence.inject.service.Database;
import com.mycila.event.Event;

/**
 * @author Jakub Bednář (19/08/2013 11:59 AM)
 */
public class SaveSubscriber extends AbstractSubscriber<SaveEvent>
{
    @Inject
    private Database database;

    @Nonnull
    @Override
    public Class<SaveEvent> eventType()
    {
        return SaveEvent.class;
    }

    @Override
    public void onEvent(final @Nonnull Event<SaveEvent> event) throws Exception
    {
        Database.Transaction transaction = database.transaction();

        try
        {
            Resource resource = event.getSource().getResource();

            transaction
                    .save(resource)
                    .commit();

            event.getSource().success(resource.getId());
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
