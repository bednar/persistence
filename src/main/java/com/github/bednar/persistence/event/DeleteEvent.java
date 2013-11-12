package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;

import com.github.bednar.base.event.AbstractEvent;
import com.github.bednar.persistence.contract.Resource;
import com.google.common.base.Preconditions;

/**
 * @author Jakub Bednář (19/08/2013 3:33 PM)
 */
public class DeleteEvent extends AbstractEvent<Long>
{
    private final Long id;
    private final Class type;

    public DeleteEvent(@Nonnull final Long id, @Nonnull final Class type)
    {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(type);

        this.id = id;
        this.type = type;
    }

    public DeleteEvent(@Nonnull final Resource resource)
    {
        this(resource.getId(), resource.getClass());
    }

    @Nonnull
    public Long getId()
    {
        return id;
    }

    @Nonnull
    public Class getType()
    {
        return type;
    }
}
