package com.adel.ehcache.dto;

import java.io.Serializable;

/**
 * @author Adel.Albediwy
 */
public class Foo implements Serializable {

    private String id;
    private String value;

    public Foo() {
    }

    public Foo(final String id, final String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Foo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
