package com.github.bednar.persistence.api;

import javax.annotation.Nonnull;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.github.bednar.persistence.AbstractPersistenceTest;
import com.github.bednar.persistence.event.ListEvent;
import com.github.bednar.persistence.resource.Pub;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (12/11/2013 18:02)
 */
public class PersistenceAPIDeleteTest extends AbstractPersistenceTest
{
    @Test
    public void delete() throws ExecutionException, InterruptedException
    {
        Response response = ClientBuilder.newClient()
                .target(embeddedJetty.getURL() + "api/pub/2")
                .request("application/json")
                .buildDelete()
                .submit()
                .get();

        Assert.assertEquals(200, response.getStatus());

        dispatcher.publish(new ListEvent<Pub>(Restrictions.conjunction(), Pub.class)
        {
            @Override
            public void success(@Nonnull final List<Pub> values)
            {
                Assert.assertEquals(1, values.size());
                Assert.assertEquals((Object) 1L, values.get(0).getId());
                Assert.assertEquals("Irish Pub", values.get(0).getName());
            }
        });
    }
}
