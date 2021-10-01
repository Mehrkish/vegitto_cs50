package ir.vegitto.model;

import java.util.List;

public class FoodResponse {
    private int count;
    private String next;
    private String previous;
    private List<Food> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Food> getResults() {
        return results;
    }

}
