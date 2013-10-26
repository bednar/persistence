package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;

import com.github.bednar.base.event.AbstractEvent;
import com.github.bednar.persistence.contract.Resource;

/**
 * @author Jakub Bednář (19/08/2013 11:13 AM)
 */
public class SaveEvent extends AbstractEvent<Long>
{
    private final Resource resource;

    public SaveEvent(final @Nonnull Resource resource)
    {
        this.resource = resource;
    }

    @Nonnull
    public Resource getResource()
    {
        return resource;
    }
}
