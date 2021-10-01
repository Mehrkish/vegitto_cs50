package ir.vegitto.model;

import java.util.List;

public class Ingredient {
    private String name;
    private List<String> allergy;
    private List<Integer> food;

    public Ingredient(String name, List<String> allergy, List<Integer> food) {
        this.name = name;
        this.allergy = allergy;
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public List<String> getAllergy() {
        return allergy;
    }

    public List<Integer> getFood() {
        return food;
    }
}
