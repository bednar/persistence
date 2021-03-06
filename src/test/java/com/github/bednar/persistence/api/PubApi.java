package com.github.bednar.persistence.api;

import javax.annotation.Nonnull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.github.bednar.persistence.resource.Pub;
import com.github.bednar.persistence.resource.PubDTO;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.Suspend;
import org.jboss.resteasy.spi.AsynchronousResponse;

/**
 * @author Jakub Bednář (10/11/2013 11:54)
 */
@Path("/pub")
public class PubApi extends AbstractPersistenceAPI<Pub, PubDTO>
{
    @Nonnull
    @Override
    protected Class<Pub> getResourceType()
    {
        return Pub.class;
    }

    @Nonnull
    @Override
    protected Class<PubDTO> getDTOType()
    {
        return PubDTO.class;
    }

    @GET
    @Path("{id}")
    public void get(@Nonnull @PathParam("id") final Long id, @Nonnull @Suspend final AsynchronousResponse response)
    {
        asynchRead(id, response);
    }

    @GET
    public void get(@Nonnull @Suspend final AsynchronousResponse response)
    {
        asynchList(response);
    }

    @DELETE
    @Path("{id}")
    public void delete(@Nonnull @PathParam("id") final Long id, @Nonnull @Suspend final AsynchronousResponse response)
    {
        asynchDelete(id, response);
    }

    @PUT
    public void put(@Nonnull PubDTO pubDTO, @Nonnull @Suspend final AsynchronousResponse response)
    {
        asynchPut(null, pubDTO, response);
    }

    @PUT
    @Path("{id}")
    public void put(@Nonnull @PathParam("id") final Long id, @Nonnull PubDTO pubDTO, @Nonnull @Suspend final AsynchronousResponse response)
    {
        asynchPut(id, pubDTO, response);
    }

    @GET
    @Path("unique")
    public void unique(@Nonnull @QueryParam("like") String like, @Nonnull @Suspend final AsynchronousResponse response)
    {
        asynchUnique(Restrictions.like("name", "%" + like + "%"), response);
    }
}
