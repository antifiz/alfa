package com.company;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "item")
public class Item {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlAttribute(name = "id")
    @Id
    private int id;
    @XmlAttribute(name = "color")
    private String color;

    public void Item() {
    }

    public void Item(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public Integer getBox_id() {
        return box_id;
    }

    public void setBox_id(Integer box_id) {
        this.box_id = box_id;
    }

    @Column(name = "contained_in")
    private Integer box_id;

}
