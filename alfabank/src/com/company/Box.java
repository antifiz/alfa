package com.company;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Box")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "box")
public class Box {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    @XmlAttribute(name = "id")
    @Id

    private int id;
    @XmlElement(name = "Item")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contained_in")

    private List<Item> items;
    @XmlElement(name = "Box")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contained_in")
    private List<Box> boxes;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public Integer getBox_id() {
        return box_id;
    }

    public void setBox_id(Integer box_id) {
        this.box_id = box_id;
    }

    @Column(name = "contained_in")
    public Integer box_id;

    public Box() {
    }

    public Box(int id, ArrayList<Item> items, ArrayList<Box> boxes) {
        this.id = id;
        this.items = items;
        this.boxes = boxes;
    }

    public ArrayList<Item> getAllItems(ArrayList<Item> items) {
        if (items == null) {
            items = new ArrayList<Item>();
        }
        if (this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                if (this.items.get(i) != null) {
                    // this.items.get(i).setBox_id(this);
                    items.add(this.items.get(i));
                }
            }
        }
        if (this.boxes != null) {
            for (int i = 0; i < this.boxes.size(); i++) {
                if (this.boxes.get(i) != null) {
                    this.boxes.get(i).getAllItems(items);
                }
            }
        }
        return items;
    }

    public ArrayList<Box> getAllBoxes(ArrayList<Box> items) {
        if (items == null) {
            items = new ArrayList<Box>();
        }
        if (this.boxes != null) {
            for (int i = 0; i < this.boxes.size(); i++) {
                if (this.boxes.get(i) != null) {
                    // this.items.get(i).setBox_id(this);
                    items.add(this.boxes.get(i));
                    this.boxes.get(i).getAllBoxes(items);
                }
            }
        }

        return items;
    }
}
