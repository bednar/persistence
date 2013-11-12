package com.github.bednar.persistence.api;

import javax.annotation.Nonnull;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.event.ReadEvent;
import com.github.bednar.persistence.resource.Pub;
import com.github.bednar.persistence.resource.PubDTO;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (12/11/2013 18:32)
 */
public class PersistenceAPIPutTest extends AbstractPersistenceTest
{
    @Test
    public void putNew() throws ExecutionException, InterruptedException
    {
        PubDTO pubDTO = new PubDTO();
        pubDTO.setName("London Pub");

        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/")
                .request("application/json")
                .buildPut(Entity.json(pubDTO))
                .submit()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("3", response.readEntity(String.class));

        dispatcher.publish(new ReadEvent<Pub>(3L, Pub.class)
        {
            @Override
            public void success(@Nonnull final Pub pub)
            {
                Assert.assertEquals((Object) 3L, pub.getId());
                Assert.assertEquals("London Pub", pub.getName());
            }
        });
    }

    @Test
    public void putExisted() throws ExecutionException, InterruptedException
    {
        PubDTO pubDTO = new PubDTO();
        pubDTO.setName("Irish Pub in London");

        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/1")
                .request("application/json")
                .buildPut(Entity.json(pubDTO))
                .submit()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("1", response.readEntity(String.class));

        dispatcher.publish(new ReadEvent<Pub>(1L, Pub.class)
        {
            @Override
            public void success(@Nonnull final Pub pub)
            {
                Assert.assertEquals((Object) 1L, pub.getId());
                Assert.assertEquals("Irish Pub in London", pub.getName());
            }
        });
    }
}
