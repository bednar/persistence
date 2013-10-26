package com.github.bednar.persistence.contract;


import javax.annotation.Nonnull;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Basic enity for store in Database Storage.
 *
 * @author Jakub Bednář (27/07/2013 4:34 PM)
 */
@MappedSuperclass
public class Resource
{
    @Id
    @GeneratedValue
    private Long id;

    @Nonnull
    public Long getId()
    {
        return id;
    }

    public void setId(final @Nonnull Long id)
    {
        this.id = id;
    }
}
