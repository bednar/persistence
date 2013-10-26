package com.github.bednar.persistence;

import javax.annotation.Nonnull;

import com.github.bednar.persistence.resource.Pub;

/**
 * @author Jakub Bednář (19/08/2013 2:59 PM)
 */
public final class DummyData
{
    private DummyData()
    {
    }

    @Nonnull
    public static Pub getPub()
    {
        return getPub("Chata");
    }

    @Nonnull
    public static Pub getPub(final @Nonnull String name)
    {
        Pub pub = new Pub();
        pub.setName(name);

        return pub;
    }
}
