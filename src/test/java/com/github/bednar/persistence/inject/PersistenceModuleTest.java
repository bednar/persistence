package com.github.bednar.persistence.inject;

import com.github.bednar.persistence.AbstractPersistenceTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jakub Bednář (27/07/2013 4:08 PM)
 */
public class PersistenceModuleTest extends AbstractPersistenceTest
{
    @Test
    public void injectorNotNull()
    {
        Assert.assertNotNull(injector);
    }

    @Test
    public void dispatcherNotNull()
    {
        Assert.assertNotNull(dispatcher);
    }
}
