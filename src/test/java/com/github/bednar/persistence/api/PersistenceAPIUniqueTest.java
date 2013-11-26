package com.github.bednar.persistence.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import com.github.bednar.persistence.AbstractPersistenceTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (26/11/2013 17:09)
 */
public class PersistenceAPIUniqueTest extends AbstractPersistenceTest
{
    @Test
    public void unique() throws ExecutionException, InterruptedException
    {
        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/unique?like=Irish")
                .request("application/json")
                .buildGet()
                .submit()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("{\"name\":\"Irish Pub\"}", response.readEntity(String.class));
    }

    @Test
    public void nullResult() throws ExecutionException, InterruptedException
    {
        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/unique?like=German")
                .request("application/json")
                .buildGet()
                .submit()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("", response.readEntity(String.class));
    }

    @Test
    public void notUnique() throws ExecutionException, InterruptedException
    {
        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/unique?like=Pub")
                .request("application/json")
                .buildGet()
                .submit()
                .get();

        Assert.assertEquals(500, response.getStatus());
    }
}
