package com.example.kilt.data;

public abstract class Item {
    private final Integer id;
    private final String name;

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}


