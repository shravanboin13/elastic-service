package com.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
@Document(indexName="attributes",type="attributes")
public class Attributes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    private String name;
    private String values;

    public Attributes(String name, String values) {
        this.name = name;
        this.values = values;
    }

    public Attributes() {
    }
}
