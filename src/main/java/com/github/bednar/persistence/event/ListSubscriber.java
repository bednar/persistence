package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

import com.github.bednar.base.event.AbstractSubscriber;
import com.github.bednar.persistence.contract.Resource;
import com.github.bednar.persistence.inject.service.Database;
import com.mycila.event.Event;

/**
 * @author Jakub Bednář (19/08/2013 4:23 PM)
 */
public class ListSubscriber extends AbstractSubscriber<ListEvent>
{
    @Inject
    private Database database;

    @Nonnull
    @Override
    public Class<ListEvent> eventType()
    {
        return ListEvent.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(final Event<ListEvent> event) throws Exception
    {
        try (Database.Transaction transaction = database.transaction())
        {
            List<? extends Resource> list = transaction.list(
                    event.getSource().getCriterion(),
                    event.getSource().getType());

            event.getSource().success(list);
        }
        catch (Exception e)
        {
            event.getSource().fail(e);
        }
    }
}
