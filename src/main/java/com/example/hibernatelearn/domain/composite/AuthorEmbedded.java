package com.example.hibernatelearn.domain.composite;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "author_composite")
public class AuthorEmbedded {
    /*
    *  @EmbeddedId  Applied to a persistent field or property of an entity class or mapped superclass to denote
    *  a composite primary key that is an embeddable class.
    * The embeddable class must be annotated as Embeddable.
    * https://docs.oracle.com/javaee/6/api/javax/persistence/EmbeddedId.html
     * */
    @EmbeddedId
    private NameId nameId;
    private String country;

    public AuthorEmbedded() {
    }

    public AuthorEmbedded(NameId nameId) {
        this.nameId = nameId;
    }


    public NameId getNameId() {
        return nameId;
    }

    public void setNameId(NameId nameId) {
        this.nameId = nameId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() { return country; }

}
