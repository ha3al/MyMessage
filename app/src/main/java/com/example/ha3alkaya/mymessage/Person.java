package com.example.ha3alkaya.mymessage;

/**
 * Created by ha3alkaya on 27.04.2016.
 */
public class Person {

    String name = null;
    String tel = null;
    boolean selected = false;

    public Person(String name,String tel,boolean selected){

        super();
        this.tel = tel;
        this.name = name;
        this.selected = selected;

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
