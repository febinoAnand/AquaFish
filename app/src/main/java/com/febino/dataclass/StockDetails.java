package com.febino.dataclass;

public class StockDetails {
    private int id;
    private String currentDateTime;
    private String entryDateTime;
    private String name;
    private int breedDetailsID;
    private float box;
    private float kg;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(String entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public int getBreedDetailsID() {
        return breedDetailsID;
    }

    public void setBreedDetailsID(int breedDetailsID) {
        this.breedDetailsID = breedDetailsID;
    }

    public float getBox() {
        return box;
    }

    public void setBox(float box) {
        this.box = box;
    }

    public float getKg() {
        return kg;
    }

    public void setKg(float kg) {
        this.kg = kg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
