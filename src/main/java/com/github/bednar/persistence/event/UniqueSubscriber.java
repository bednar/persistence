package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import com.github.bednar.base.event.AbstractSubscriber;
import com.github.bednar.persistence.contract.Resource;
import com.github.bednar.persistence.inject.service.Database;
import com.mycila.event.Event;

/**
 * @author Jakub Bednář (25/11/2013 20:14)
 */
public class UniqueSubscriber extends AbstractSubscriber<UniqueEvent>
{
    @Inject
    private Database database;

    @Nonnull
    @Override
    public Class<UniqueEvent> eventType()
    {
        return UniqueEvent.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onEvent(final Event<UniqueEvent> event) throws Exception
    {
        try (Database.Transaction transaction = database.transaction())
        {
            Resource resource = transaction.unique(
                    event.getSource().getCriterion(),
                    event.getSource().getType());

            event.getSource().success(resource);
        }
        catch (Exception e)
        {
            event.getSource().fail(e);
        }
    }
}
