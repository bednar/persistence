package com.github.bednar.persistence.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import com.github.bednar.persistence.AbstractPersistenceTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (10/11/2013 12:13)
 */
public class PubApiTest extends AbstractPersistenceTest
{
    @Test
    public void get() throws ExecutionException, InterruptedException
    {
        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/1")
                .request("application/json")
                .buildGet()
                .submit()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("{\"name\":\"Irish Pub\"}", response.readEntity(String.class));
    }

    @Test
    public void list() throws ExecutionException, InterruptedException
    {
        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/")
                .request("application/json")
                .buildGet()
                .submit()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("[{\"name\":\"Irish Pub\"},{\"name\":\"Czech Pub\"}]", response.readEntity(String.class));
    }
}
