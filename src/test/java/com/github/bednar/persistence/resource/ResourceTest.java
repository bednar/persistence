package com.github.bednar.persistence.resource;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (03/11/2013 16:55)
 */
public class ResourceTest
{
    @Test
    public void newResource()
    {
        Pub pub = new Pub();

        Assert.assertTrue(pub.isNew());
    }

    @Test
    public void oldResource()
    {
        Pub pub = new Pub();
        pub.setId(1L);

        Assert.assertFalse(pub.isNew());
    }
}
