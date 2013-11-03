package com.github.bednar.persistence.inject.service;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import com.github.bednar.base.http.AppBootstrap;
import com.github.bednar.base.utils.xml.FluentXml;
import com.github.bednar.persistence.contract.Resource;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Bednář (27/07/2013 1:47 PM)
 */
public class DatabaseImpl implements Database
{
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseImpl.class);

    private final SessionFactory factory;

    public DatabaseImpl()
    {
        if (FluentXml.byResource("/hibernate.cfg.xml").notExists())
        {
            LOG.info("[not-exist-configuration-file][/hibernate.cfg.xml]");

            this.factory = null;

            return;
        }

        Configuration configuration = new Configuration();
        for (Class entity : findEntities())
        {
            configuration.addAnnotatedClass(entity);
        }
        configuration.configure();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();

        this.factory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Nonnull
    @Override
    public Transaction transaction()
    {
        return new TransactionImpl();
    }

    @Nonnull
    private Set<Class<?>> findEntities()
    {
        Reflections reflections = new Reflections(AppBootstrap.SYMBOL_BASE_PACKAGE);

        return reflections.getTypesAnnotatedWith(Entity.class);
    }

    private class TransactionImpl implements Transaction, AutoCloseable
    {
        private final Session session;

        public org.hibernate.Transaction transaction;

        {
            session = factory.openSession();

            beginTransaction();
        }

        @Nonnull
        @Override
        public Transaction save(final @Nonnull Resource resource)
        {
            session.save(resource);

            return this;
        }

        @Nonnull
        @Override
        @SuppressWarnings("unchecked")
        public <R extends Resource> R read(final @Nonnull Long key, final @Nonnull Class<R> type)
        {
            R resource = (R) session.byId(type).load(key);

            if (resource == null)
            {
                throw new ObjectNotFoundException(key, type.getName());
            }

            return resource;
        }

        @Nonnull
        @Override
        public Transaction delete(final @Nonnull Resource resource)
        {
            session.delete(resource);

            return this;
        }

        @Nonnull
        @Override
        public <R extends Resource> List<R> list(final @Nonnull Criterion criterion, final @Nonnull Class<R> type)
        {
            //noinspection unchecked
            return session.createCriteria(type).add(criterion).list();
        }

        @Nonnull
        @Override
        public Session session()
        {
            return session;
        }

        @Nonnull
        @Override
        public Transaction doSQL(@Nonnull final String sql)
        {
            session.doWork(new Work()
            {
                @Override
                public void execute(final Connection connection) throws SQLException
                {
                    Statement statement = connection.createStatement();
                    statement.execute(sql);
                    statement.close();
                }
            });

            return this;
        }

        @Nonnull
        @Override
        public Transaction commit()
        {
            transaction.commit();

            beginTransaction();

            return this;
        }

        @Nonnull
        @Override
        public Transaction rollback()
        {
            transaction.rollback();

            beginTransaction();

            return this;
        }

        @Override
        public void finish()
        {
            transaction.rollback();

            session.close();
        }

        @Override
        public void close()
        {
            finish();
        }

        private void beginTransaction()
        {
            transaction = session.beginTransaction();
        }
    }
}
