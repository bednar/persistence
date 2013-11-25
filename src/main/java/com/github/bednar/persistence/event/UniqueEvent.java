package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;

import com.github.bednar.base.event.AbstractEvent;
import com.github.bednar.persistence.contract.Resource;
import com.google.common.base.Preconditions;
import org.hibernate.criterion.Criterion;

/**
 * @author Jakub Bednář (25/11/2013 20:13)
 */
public class UniqueEvent<R extends Resource> extends AbstractEvent<R>
{
    private final Criterion criterion;
    private final Class<R> type;

    public UniqueEvent(@Nonnull final Criterion criterion, @Nonnull final Class<R> type)
    {
        Preconditions.checkNotNull(criterion);
        Preconditions.checkNotNull(type);

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
