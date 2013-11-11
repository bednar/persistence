package com.github.bednar.persistence.api;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.github.bednar.persistence.resource.Pub;
import org.jboss.resteasy.annotations.Suspend;
import org.jboss.resteasy.spi.AsynchronousResponse;

/**
 * @author Jakub Bednář (10/11/2013 11:54)
 */
@Path("/pub")
public class PubApi extends AbstractPersistenceAPI<Pub>
{
    @Nonnull
    @Override
    protected Class<Pub> getType()
    {
        return Pub.class;
    }

    @GET
    @Path("{id}")
    public void get(@Nonnull @PathParam("id") final Long id, @Nonnull @Suspend final AsynchronousResponse response)
    {
        asynchRead(id, response);
    }
}
