package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;
import java.util.List;

import com.github.bednar.base.event.AbstractEvent;
import com.github.bednar.persistence.contract.Resource;
import org.hibernate.criterion.Criterion;

/**
 * @author Jakub Bednář (19/08/2013 4:22 PM)
 */
public class ListEvent<R extends Resource> extends AbstractEvent<List<R>>
{
    private final Criterion criterion;
    private final Class<R> type;

    public ListEvent(final @Nonnull Criterion criterion, final @Nonnull Class<R> type)
    {
        this.criterion = criterion;
        this.type = type;
    }

    @Nonnull
    public Criterion getCriterion()
    {
        return criterion;
    }

    @Nonnull
    public Class<? extends Resource> getType()
    {
        return type;
    }
}
