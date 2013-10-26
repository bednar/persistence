package com.github.bednar.persistence.event;

import javax.annotation.Nonnull;

import com.github.bednar.base.event.AbstractEvent;
import com.github.bednar.persistence.contract.Resource;

/**
 * @author Jakub Bednář (19/08/2013 3:42 PM)
 */
public class ReadEvent<R extends Resource> extends AbstractEvent<R>
{
    private final Long key;
    private final Class<R> type;

    public ReadEvent(final @Nonnull Long key, final @Nonnull Class<R> type)
    {
        this.key = key;
        this.type = type;
    }

    @Nonnull
    public Long getKey()
    {
        return key;
    }

    @Nonnull
    public Class<? extends Resource> getType()
    {
        return type;
    }
}
