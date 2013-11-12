package com.github.bednar.persistence.api;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

import com.github.bednar.base.api.ApiResource;
import com.github.bednar.base.event.Dispatcher;
import com.github.bednar.persistence.contract.Resource;
import com.github.bednar.persistence.event.DeleteEvent;
import com.github.bednar.persistence.event.ListEvent;
import com.github.bednar.persistence.event.ReadEvent;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.Suspend;
import org.jboss.resteasy.spi.AsynchronousResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Bednář (10/11/2013 10:08)
 */
public abstract class AbstractPersistenceAPI<R extends Resource> implements ApiResource
{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractPersistenceAPI.class);

    @Inject
    private Dispatcher dispatcher;

    private final Class dtoType;

    protected AbstractPersistenceAPI()
    {
        try
        {
            this.dtoType = Class.forName(getType().getCanonicalName() + "DTO");
        }
        catch (ClassNotFoundException e)
        {
            throw new AbstractPersistenceAPIException(e);
        }

        LOG.info("[persistence-api-initialized][{}][{}]", getType().getCanonicalName(), dtoType.getCanonicalName());
    }

    @Nonnull
    protected abstract Class<R> getType();

    protected void asynchRead(@Nonnull final Long id, @Nonnull @Suspend final AsynchronousResponse response)
    {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(response);

        dispatcher.publish(new ReadEvent<R>(id, getType())
        {
            @Override
            public void success(@Nonnull final R value)
            {
                Object dto = transform(value, dtoType);

                response.setResponse(Response.ok(dto).build());
            }
        });
    }

    protected void asynchList(@Nonnull @Suspend final AsynchronousResponse response)
    {
        Preconditions.checkNotNull(response);

        dispatcher.publish(new ListEvent<R>(Restrictions.conjunction(), getType())
        {
            @Override
            public void success(@Nonnull final List<R> values)
            {
                ImmutableList<Object> dtos = FluentIterable.from(values).transform(new Function<R, Object>()
                {
                    @Nonnull
                    @Override
                    public Object apply(@Nonnull @SuppressWarnings("NullableProblems") final R value)
                    {
                        return transform(value, dtoType);
                    }
                }).toList();

                GenericEntity entity = new GenericEntity(dtos, List.class);

                response.setResponse(Response.ok(entity).build());
            }
        });
    }

    protected void asynchDelete(@Nonnull final Long id, @Nonnull @Suspend final AsynchronousResponse response)
    {
        dispatcher.publish(new DeleteEvent(id, getType())
        {
            @Override
            public void success(@Nonnull final Long value)
            {
                response.setResponse(Response.ok().build());
            }
        });
    }

    @Nonnull
    private Object transform(@Nonnull final Object source, @Nonnull final Class destType)
    {
        Object dest;
        try
        {
            dest = destType.newInstance();

            BeanUtilsBean2.getInstance().copyProperties(dest, source);
        }
        catch (Exception e)
        {
            throw new AbstractPersistenceAPIException(e);
        }

        return dest;
    }

    private static class AbstractPersistenceAPIException extends RuntimeException
    {
        private AbstractPersistenceAPIException(@Nonnull final Throwable cause)
        {
            super(cause);
        }
    }
}
