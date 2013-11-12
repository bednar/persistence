package com.github.bednar.persistence.inject.service;

import javax.annotation.Nonnull;
import java.util.List;

import com.github.bednar.persistence.contract.Resource;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

/**
 * Connect to the Database storage.
 *
 * @author Jakub Bednář (27/07/2013 1:28 PM)
 */
public interface Database
{
    /**
     * Create new Transaction.
     *
     * @return new database transaction
     */
    @Nonnull
    Transaction transaction();

    /**
     * Database transaction
     */
    public interface Transaction extends AutoCloseable
    {
        /**
         * Save
         *
         * @param resource persistable resource
         *
         * @return this
         */
        @Nonnull
        Transaction save(@Nonnull Resource resource);

        /**
         * Read
         *
         * @param key  of resource
         * @param type of resource
         *
         * @return resource with {@code key}
         */
        @Nonnull
        <R extends Resource> R read(@Nonnull Long key, @Nonnull Class<R> type);

        /**
         * Delete
         *
         * @param key  of resource
         * @param type of resource
         *
         * @return this
         */
        @Nonnull
        Transaction delete(final @Nonnull Long key, final @Nonnull Class type);

        /**
         * List
         *
         * @param criterion for select resources
         *
         * @return selected resources by {@code criterion}
         */
        @Nonnull
        <R extends Resource> List<R> list(@Nonnull Criterion criterion, @Nonnull Class<R> type);

        /**
         * @return actual session
         */
        @Nonnull
        Session session();

        /**
         * Execute {@code sql} statement.
         *
         * @param sql sql statement
         *
         * @return this
         */
        Transaction doSQL(@Nonnull String sql);

        /**
         * Commit actual transaction to Database.
         *
         * @return this
         */
        @Nonnull
        Transaction commit();

        /**
         * Rollback actual transaction.
         *
         * @return this
         */
        @Nonnull
        Transaction rollback();

        /**
         * Finish wokr with actual Transaction (close database connection).
         */
        void finish();
    }
}
