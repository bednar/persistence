package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;

import com.github.bednar.base.event.AbstractEvent;
import com.github.bednar.persistence.contract.Resource;

/**
 * @author Jakub Bednář (19/08/2013 3:33 PM)
 */
public class DeleteEvent extends AbstractEvent<Long>
{
    private final Resource resource;

    public DeleteEvent(final @Nonnull Resource resource)
    {
        this.resource = resource;
    }

    @Nonnull
    public Resource getResource()
    {
        return resource;
    }
}
