package ir.vegitto.roomDB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import ir.vegitto.model.Food;

@Database(entities = {Food.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FoodDataBase extends RoomDatabase {

    private static FoodDataBase instance;

    public abstract FoodDao foodDao();

    public static synchronized FoodDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FoodDataBase.class,
                    "Food_DB")
                    //use utf8
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            db.execSQL("PRAGMA encoding='UTF-8';");
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
