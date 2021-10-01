package ir.vegitto.roomDB;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ir.vegitto.model.Food;

@Dao
public interface FoodDao {

    @Insert
    void Insert(Food food);

    @Query("DELETE FROM Food_Table")
    void DeleteAll();

    @Query("SELECT * FROM Food_Table ORDER BY id DESC")
    LiveData<List<Food>> getAllFoods();
}
