package com;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Person {
    private School school;
    private String name;
    private String[] books;
    private List<String> hobbys;
    private Map<String, String> card;
    private Set<String> games;
    private String wife;
    private Properties info;

    public void setSchool(School school) {
        this.school = school;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
    }

    public void setCard(Map<String, String> card) {
        this.card = card;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public void setWife(String wife) {
        this.wife = wife;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    public void show() {
        System.out.println("name="+name+",school="+school.getSchoolName()+",books=");
        for (String book: books) {
            System.out.println("<<"+book+">>");
        }
        System.out.println("\n爱好："+hobbys);
        System.out.println("card："+card);
        System.out.println("games："+games);
        System.out.println("wife："+wife);
        System.out.println("info："+info);
    }
}
