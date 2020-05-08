package com.company;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

@XmlRootElement(name = "Storage")
@XmlAccessorType(XmlAccessType.FIELD)
public class Storage implements Serializable {
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    @XmlElement(name = "Item")

    private ArrayList<Item> items;
    @XmlElement(name = "Box")

    private ArrayList<Box> boxes;

    public void Storage() {
    }

    public void jaxbXmlFileToObject(String fileName) {

        File xmlFile = new File(fileName);

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Storage.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Storage storage = (Storage) jaxbUnmarshaller.unmarshal(xmlFile);
            if (storage != null) {
                this.setBoxes(storage.getBoxes());
                this.setItems(storage.getItems());
            } else {
                throw new Exception("Can not convert to java object");
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void jaxbObjectToXML(Storage storage) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Storage.class);


            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();


            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


            StringWriter sw = new StringWriter();


            jaxbMarshaller.marshal(storage, sw);


            String xmlContent = sw.toString();
            System.out.println(xmlContent);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        if (this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                if (this.items.get(i) != null) {
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

    public ArrayList<Box> getAllBoxes() {
        ArrayList<Box> items = new ArrayList<Box>();
        if (this.boxes != null) {
            for (int i = 0; i < this.boxes.size(); i++) {
                if (this.boxes.get(i) != null) {
                    items.add(this.boxes.get(i));

                }
            }
        }

        return items;
    }

}
