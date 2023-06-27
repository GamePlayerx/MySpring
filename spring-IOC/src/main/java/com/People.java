package com;

public class People {
    private String name;
    private dog dog;
    private cat cat;

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", dog=" + dog +
                ", cat=" + cat +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.dog getDog() {
        return dog;
    }

    public void setDog(com.dog dog) {
        this.dog = dog;
    }

    public com.cat getCat() {
        return cat;
    }

    public void setCat(com.cat cat) {
        this.cat = cat;
    }
}
