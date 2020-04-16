package com.hj.todoproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase ==null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db_task")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public abstract TaskDao getTaskDao();

}
