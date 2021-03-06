package com.github.bednar.persistence.resource;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.github.bednar.persistence.contract.Resource;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Jakub Bednář (27/07/2013 7:03 PM)
 */

@Entity
@ApiModel("Pub for my Beers")
public class Pub extends Resource
{
    @Column(nullable = false, length = 500)
    @ApiModelProperty(value = "Name of Pub", position = 1)
    private String name;

    @Nonnull
    public String getName()
    {
        return name;
    }

    public void setName(final @Nonnull String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Pub))
        {
            return false;
        }

        Pub pub = (Pub) o;

        //noinspection RedundantIfStatement
        if (getId() != null ? !getId().equals(pub.getId()) : pub.getId() != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return getId() != null ? getId().hashCode() : 0;
    }
}
