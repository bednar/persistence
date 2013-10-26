package com.github.bednar.persistence.inject;

import javax.inject.Provider;

import com.github.bednar.persistence.inject.service.Database;
import com.github.bednar.persistence.inject.service.DatabaseImpl;
import org.grouplens.grapht.Context;
import org.grouplens.grapht.Module;

/**
 * Persistence dependency injection configuration.
 *
 * @author Jakub Bednář (27/07/2013 4:02 PM)
 */
public final class PersistenceModule implements Module
{
    @Override
    public void configure(final Context context)
    {
        //'eager load'
        final DatabaseImpl database = new DatabaseImpl();

        context
                .bind(Database.class)
                .toProvider(
                        new Provider<Database>()
                        {
                            @Override
                            public Database get()
                            {
                                return database;
                            }
                        });
    }
}
