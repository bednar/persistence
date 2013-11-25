package com.github.bednar.persistence.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import com.github.bednar.persistence.event.SaveEvent;
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
public abstract class AbstractPersistenceAPI<R extends Resource, D> implements ApiResource
{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractPersistenceAPI.class);

    @Inject
    protected Dispatcher dispatcher;

    protected AbstractPersistenceAPI()
    {
        LOG.info("[persistence-api-initialized][{}][{}]", getResourceType(), getDTOType());
    }

    @Nonnull
    protected abstract Class<R> getResourceType();

    @Nonnull
    protected abstract Class<D> getDTOType();

    protected void asynchRead(@Nonnull final Long id, @Nonnull @Suspend final AsynchronousResponse response)
    {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(response);

        dispatcher.publish(new ReadEvent<R>(id, getResourceType())
        {
            @Override
            public void success(@Nonnull final R value)
            {
                D dto = transform(value, getDTOType());

                response.setResponse(Response.ok(dto).build());
            }
        });
    }

    protected void asynchList(@Nonnull @Suspend final AsynchronousResponse response)
    {
        Preconditions.checkNotNull(response);

        dispatcher.publish(new ListEvent<R>(Restrictions.conjunction(), getResourceType())
        {
            @Override
            public void success(@Nonnull final List<R> values)
            {
                ImmutableList<D> dtos = FluentIterable.from(values).transform(new Function<R, D>()
                {
                    @Nonnull
                    @Override
                    public D apply(@Nonnull @SuppressWarnings("NullableProblems") final R value)
                    {
                        return transform(value, getDTOType());
                    }
                }).toList();

                GenericEntity entity = new GenericEntity<List>(dtos, List.class);

                response.setResponse(Response.ok(entity).build());
            }
        });
    }

    protected void asynchDelete(@Nonnull final Long id, @Nonnull @Suspend final AsynchronousResponse response)
    {
        dispatcher.publish(new DeleteEvent(id, getResourceType())
        {
            @Override
            public void success(@Nonnull final Long value)
            {
                response.setResponse(Response.ok().build());
            }
        });
    }

    protected void asynchPut(@Nullable final Long key, @Nonnull final D dto, @Nonnull @Suspend final AsynchronousResponse response)
    {
        R resource = transform(dto, getResourceType());

        /**
         * key == null => create new
         */
        //noinspection ConstantConditions
        resource.setId(key);

        dispatcher.publish(new SaveEvent(resource)
        {
            @Override
            public void success(@Nonnull final Long key)
            {
                response.setResponse(Response.ok(key).build());
            }
        });
    }

    @Nonnull
    private <T> T transform(@Nonnull final Object source, @Nonnull final Class<T> destType)
    {
        T dest;
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
