package com.Blood.types.Model;

public class ModelTransport {
    private String name,number,title,line;

    public ModelTransport(String name, String number, String title, String line) {
        this.name = name;
        this.number = number;
        this.title = title;
        this.line = line;
    }

    public ModelTransport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
